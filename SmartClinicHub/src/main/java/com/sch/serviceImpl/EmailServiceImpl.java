package com.sch.serviceImpl;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sch.entity.User;
import com.sch.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailService;

	@Value("${app.reset-password-url}")
	private String resetPasswordUrl;

	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	public void sendPasswordResetEmail(User user) {
		if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
			log.error("Invalid user details. Cannot send password reset email.");
			return;
		}

		String passwordResetLink = resetPasswordUrl + user.getId();

		Thread emailThread = new Thread(() -> {
			try {
				MimeMessage mimeMessage = javaMailService.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

				// Email Content
				String subject = "Account Created - Reset Your Password";
				String content = String.format("Dear %s,<br><br>"
						+ "Your account has been successfully created. To activate your account, please reset your password.<br><br>"
						+ "<b>Click the link below to reset your password:</b><br>"
						+ "<a href='%s'>Reset Password</a><br><br>"
						+ "If you did not request this, please ignore this email.<br><br>"
						+ "Best regards,<br>Support Team", user.getName(), passwordResetLink);

				helper.setTo(user.getEmail());
				helper.setSubject(subject);
				helper.setText(content, true);

				javaMailService.send(mimeMessage);
				log.info("Password reset email sent successfully to user: {}", user.getEmail());
			} catch (Exception e) {
				log.error("Failed to send password reset email to user: {}", user.getEmail(), e);
			}
		});

		emailThread.setPriority(Thread.NORM_PRIORITY); 
		emailThread.start(); 
	}
	
	
	
	
	
	
	
	
	
	
	 
	
	
	
	
	
	
	
	
	

    public void sendReminder(String toEmail, Date expiryDate) {
        String subject = "⚠️ Clinic Subscription Expiry Reminder";

        String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(expiryDate);

        String message = """
                Dear Clinic Admin,

                This is a reminder that your clinic's subscription is expiring on %s.

                Please renew it before expiry to avoid service interruption.

                Regards,
                Clinic SaaS Team
                """.formatted(formattedDate);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailService.send(mailMessage);
    }
}
