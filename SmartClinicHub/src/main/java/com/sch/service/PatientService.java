package com.sch.service;

import com.sch.dto.PatientDto;
import com.sch.dto.Response;

public interface PatientService {

	Response<?> registerPatient(PatientDto patientDto);

	Response<?> getPatientByClinic();

}
