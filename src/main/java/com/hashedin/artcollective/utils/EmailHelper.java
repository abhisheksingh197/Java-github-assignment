package com.hashedin.artcollective.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hashedin.artcollective.entity.Lead;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailHelper implements InitializingBean {

	private Session session;
	
	@Autowired
	private Configuration freemarkerConfiguration;
	
    @Value("${mail.host}")
    private String host;
    
    @Value("${mail.port}")
    private int port;
    
    @Value("${mail.smtp.auth}")
    private String auth;
    
    @Value("${mail.smtp.starttls.enable}")
    private String starttls;
    
    @Value("${mail.from}")
    private String from;
    
    @Value("${mail.to.onnewlead}")
    private String toEmailOnNewLead;
    
    @Value("${mail.username}")
    private String username;
    
    @Value("${mail.password}")
    private String password;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttls);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		this.session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		
	}
	
	public void sendEmailOnNewContact(Lead lead) {
		sendEmail(toEmailOnNewLead,
				String.format("%s : New Lead - %s", lead.getSource(), lead.getName()),
				"email/newlead.ftl", Collections.singletonMap("lead", lead));
    }
	
	void sendEmail(String to, String subject, String templateName, Map<String, ?> model) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			
			Template template = freemarkerConfiguration.getTemplate(templateName);
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			message.setContent(body, "text/html");
			
			Transport.send(message);
		}
		catch(IOException ioe) {
			throw new RuntimeException(ioe);
		}
		catch (TemplateException te) {
			throw new RuntimeException(te);
		}
		catch (MessagingException me) {
			throw new RuntimeException(me);
		}
	}
	
}
