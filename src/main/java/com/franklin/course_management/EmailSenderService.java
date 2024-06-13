package com.franklin.course_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService { 
    
    @Autowired
    private JavaMailSender mailSender;
    
    // Sends an email to the reciever with the supplied subject and body.
    @Async
    public void sendEmail(String reciever, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reciever);
        message.setFrom("erronjames4@gmail.com");
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        
        System.out.println("Email sent!");
    }
}
