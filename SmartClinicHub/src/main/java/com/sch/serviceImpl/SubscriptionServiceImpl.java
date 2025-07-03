package com.sch.serviceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sch.config.CustomizedUserDetailsService;
import com.sch.dto.Response;
import com.sch.dto.SubscriptionPlanDto;
import com.sch.entity.Clinic;
import com.sch.entity.Subscription;
import com.sch.entity.SubscriptionPlan;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.repository.ClinicRepository;
import com.sch.repository.SubscriptionPlanRepository;
import com.sch.repository.SubscriptionRepository;
import com.sch.service.EmailService;
import com.sch.service.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private CustomizedUserDetailsService customizedUserDetailsService;
	@Autowired
	private SubscriptionPlanRepository planRepository;
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ClinicRepository clinicRepository;

	@Override
	public Response<?> createplan(SubscriptionPlanDto planDto) {
		try {
			Optional<User> loggedInUser = customizedUserDetailsService.getUserDetails();
			if (loggedInUser.isEmpty() || !loggedInUser.get().getRole().equals(Role.SUPER_ADMIN)) {
				return new Response<>(HttpStatus.OK.value(), "Only SUPER_ADMIN can create or update plans.", null);
			}

			if (planDto.getId() != null) {
				Optional<SubscriptionPlan> existingPlanOpt = planRepository.findById(planDto.getId());
				if (existingPlanOpt.isEmpty()) {
					return new Response<>(HttpStatus.NOT_FOUND.value(), "Plan not found for update.", null);
				}

				SubscriptionPlan plan = existingPlanOpt.get();

				if (planDto.getName() != null)
					plan.setName(planDto.getName());
				if (planDto.getPrice() != null)
					plan.setPrice(planDto.getPrice());
				if (planDto.getDurationDays() != null)
					plan.setDurationDays(planDto.getDurationDays());
				if (planDto.getDiscountPercent() != null)
					plan.setDiscountPercent(planDto.getDiscountPercent());

				planRepository.save(plan);

				return new Response<>(HttpStatus.OK.value(), "Plan updated successfully.", null);

			} else {
				Optional<SubscriptionPlan> existingPlan = planRepository.findByName(planDto.getName());
				if (existingPlan.isPresent()) {
					return new Response<>(HttpStatus.OK.value(), "Plan already exists.", null);
				}

				SubscriptionPlan plan = new SubscriptionPlan();
				plan.setName(planDto.getName());
				plan.setPrice(planDto.getPrice());
				plan.setDurationDays(planDto.getDurationDays());
				plan.setDiscountPercent(planDto.getDiscountPercent() != null ? planDto.getDiscountPercent() : 0);
				plan.setCreatedAt(new Date());

				planRepository.save(plan);

				return new Response<>(HttpStatus.OK.value(), "Plan created successfully.", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong.", null);
		}
	}

	@Override
	public boolean isClinicSubscriptionActive(Long clinicId) {
		return subscriptionRepository.findByClinicIdAndIsActiveTrue(clinicId).isPresent();
	}

	@Scheduled(cron = "0 30 9 * * *") // Every day at 9:30 AM
	public void sendExpiryReminders() {
		LocalDate target = LocalDate.now().plusDays(5);
		Date reminderDate = Date.from(target.atStartOfDay(ZoneId.systemDefault()).toInstant());

		List<Subscription> subscriptions = subscriptionRepository
				.findByEndDateAndIsActiveTrueAndReminderSentFalse(reminderDate);

		for (Subscription s : subscriptions) {
			try {
				String email = s.getClinic().getEmail(); // Clinic email
				emailService.sendReminder(email, s.getEndDate());

				s.setReminderSent(true);
				subscriptionRepository.save(s);

				System.out.println("✅ Reminder sent to clinic: " + email);
			} catch (Exception ex) {
				System.err.println("❌ Failed to send reminder to: " + s.getClinic().getEmail());
				ex.printStackTrace();
			}
		}
	}

	@Override
	public Response<?> upgradePlan(SubscriptionPlanDto planDto) {
		try {
	    Optional<User> loggedInuser = customizedUserDetailsService.getUserDetails();
	    if (loggedInuser.isEmpty()) {
	        return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not logged in", null);
	    }

	    Role role = loggedInuser.get().getRole();
	    if (!(Role.ADMIN.equals(role) || Role.SUPER_ADMIN.equals(role))) {
	        return new Response<>(HttpStatus.FORBIDDEN.value(), "You can't upgrade the plan", null);
	    }

	    Long clinicId = loggedInuser.get().getClinic().getId();
	    Optional<Clinic> clinicOpt = clinicRepository.findById(clinicId);
	    if (clinicOpt.isEmpty()) {
	        return new Response<>(HttpStatus.NOT_FOUND.value(), "Clinic not found", null);
	    }

	    Optional<SubscriptionPlan> planOpt = planRepository.findByName(planDto.getName());
	    if (planOpt.isEmpty()) {
	        return new Response<>(HttpStatus.NOT_FOUND.value(), "Plan not found", null);
	    }

	    SubscriptionPlan newPlan = planOpt.get();

	    List<Subscription> subs= subscriptionRepository.findTopByClinicIdOrderByEndDateDesc(clinicId);
	    if (subs.isEmpty()) {
	        return new Response<>(HttpStatus.NOT_FOUND.value(), "Active subscription not found", null);
	    }

	    Subscription subscription = subs.get(0);
	    subscription.setPlan(newPlan);
	    subscription.setStartDate(new Date());
	    subscription.setEndDate(Date.from(LocalDate.now()
	        .plusDays(newPlan.getDurationDays())
	        .atStartOfDay(ZoneId.systemDefault()).toInstant()));
	    subscription.setPaymentStatus("PAID");
	    subscription.setIsActive(true);
	    subscription.setReminderSent(false); 
	    subscriptionRepository.save(subscription);

	    return new Response<>(HttpStatus.OK.value(), "Plan upgraded successfully.", null);

	} catch (Exception e) {
	    e.printStackTrace();
	    return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
	}
				
}
	
	@Scheduled(cron = "*/1 * * * * *")
    public void deactivateExpiredSubscriptions() {
        Date today = new Date();
        List<Subscription> expiredList = subscriptionRepository.findByIsActiveTrueAndEndDateBefore(today);

        for (Subscription sub : expiredList) {
            sub.setIsActive(false);
            subscriptionRepository.save(sub);
            System.out.println("⛔ Deactivated expired subscription for clinic: " + sub.getClinic().getName());
        }
    }

}
