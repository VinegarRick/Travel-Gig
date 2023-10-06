package com.synex.component;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class EmailComponent {
	
	@Autowired private JavaMailSender javaMailSender;
	
	public void sendEmail(String emailAddress, JsonNode booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("Booking Confirmation");
        //message.setText("Your booking has been confirmed. Thank you!");
        message.setText("Dear " + booking.get("userName").asText() + ",\n\nYour booking has been confirmed. You can find the details below. We hope you enjoy your stay!\n\n" + "Check-In Date: " + booking.get("checkInDate").asText() + "\nCheck-Out Date: " + booking.get("checkOutDate").asText() + "\nRoom Type: " + booking.get("roomType").asText() + "\nNumber of Rooms: " + booking.get("noRooms").asText() + "\nTotal Cost: " + booking.get("finalCharges").asText() + "\nTotal Savings: " + booking.get("totalSavings") + "\n\nBest Regards,\nTravel Gig");
        
        javaMailSender.send(message);
        
        System.out.println("Email sent!");
	}
}
