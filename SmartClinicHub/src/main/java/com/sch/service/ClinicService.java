package com.sch.service;

import com.sch.dto.Response;

public interface ClinicService {

	Response<?> getClinicById(Long id);

	Response<?> getAllClinic();

}
