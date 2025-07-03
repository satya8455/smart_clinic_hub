package com.sch.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sch.dto.ClinicDto;
import com.sch.dto.Response;
import com.sch.entity.Clinic;
import com.sch.repository.ClinicRepository;
import com.sch.service.ClinicService;

@Service
public class ClinicServiceImpl implements ClinicService {
	@Autowired
	ClinicRepository clinicRepository;

	@Override
	public Response<?> getClinicById(Long id) {
		try {
			Optional<Clinic> optionalClinic = clinicRepository.findById(id);
			if (optionalClinic.isPresent()) {
				return new Response<>(HttpStatus.OK.value(), "Data retrieved successfully", optionalClinic.get());
			}
			return new Response<>(HttpStatus.OK.value(), "Data not found", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> getAllClinic() {
		try {
			List<Clinic> list = clinicRepository.findAll();
			if(list.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(),"No data found",null);
			}
			List<ClinicDto> list1 = list.stream().map(Clinic::convertToDTo).toList();
			
			return new Response<>(HttpStatus.OK.value(), "Data retrieved successfully", list1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong", null);

		}
	}

}
