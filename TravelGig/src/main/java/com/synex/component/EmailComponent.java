package com.synex.component;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EmailComponent {
	
	@Autowired private JavaMailSender javaMailSender;
	
	public void sendEmail(String emailAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("Booking Confirmation");
        message.setText("Your booking has been confirmed. Thank you!");

        javaMailSender.send(message);
        
        System.out.println("Email sent!");
	}
}
