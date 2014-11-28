package com.hashedin.artcollective.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.Lead;

@Service
public class EmailHelper {

	private final JavaMailSender javaMailSender;
	
	@Autowired
	public EmailHelper(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
	
	public SimpleMailMessage sendEmailOnNewContact(Lead lead) {        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("artcollective@hashedin.com");
        mailMessage.setFrom("sripathi.krishnan@hashedin.com");
        mailMessage.setSubject("New Lead from " + lead.getName());
        mailMessage.setText("Message : " + lead.getMessage());
        javaMailSender.send(mailMessage);
        return mailMessage;
    }
}
