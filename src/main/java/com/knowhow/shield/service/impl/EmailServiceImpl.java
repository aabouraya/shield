package com.knowhow.shield.service.impl;

import com.knowhow.shield.config.AppProperties;
import com.knowhow.shield.model.User;
import com.knowhow.shield.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
class EmailServiceImpl implements EmailService {

    private static final String ACTIVATION_MAIL_SUBJECT = "Activation Code";
    private AppProperties appProperties;
    private JavaMailSender mailSender;
    private SimpleMailMessage activationMessage;

    EmailServiceImpl(AppProperties appProperties, JavaMailSender mailSender, SimpleMailMessage activationMessage) {
        this.appProperties = appProperties;
        this.mailSender = mailSender;
        this.activationMessage = activationMessage;
    }

    @Override
    public void sendActivationEmail(User user, String token) {
        activationMessage.setSubject(ACTIVATION_MAIL_SUBJECT);
        activationMessage.setTo(user.getEmail());
        activationMessage.setText(String.format(activationMessage.getText(), user.getFirstName(),
                appProperties.getApp().getActivationDomainUrl(), token));
        mailSender.send(activationMessage);

    }
}
