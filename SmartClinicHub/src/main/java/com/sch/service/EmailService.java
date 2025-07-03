package com.sch.service;

import com.sch.entity.User;

public interface EmailService {

	public void sendPasswordResetEmail(User user);
	
	public void sendReminder(String toEmail, java.util.Date expiryDate);
}
