package com.knowhow.shield.service.impl;

import com.knowhow.shield.config.AppProperties;
import com.knowhow.shield.dto.ActivationMessageDto;
import com.knowhow.shield.model.User;
import com.knowhow.shield.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

    private static final String ACTIVATION_MAIL_SUBJECT = "Activation Code";
    private final AppProperties appProperties;
    private final JavaMailSender mailSender;
    private final SimpleMailMessage activationMessage;
    private final Source source;


    @Override
    @SendTo(Source.OUTPUT)
    public ActivationMessageDto sendActivationEmail(User user, String token) {
//        ActivationMessageDto message =
        return new ActivationMessageDto(ACTIVATION_MAIL_SUBJECT, user.getEmail(),
                String.format(activationMessage.getText(), user.getFirstName(),
                        appProperties.getApp().getActivationDomainUrl(), token));
//        source.output().send((MessageBuilder.withPayload(message).build()));
//        return message;
    }

//    @Override
//    @StreamListener(Sink.INPUT)
//    public void send(ActivationMessageDto message) {
//        SimpleMailMessage m = new SimpleMailMessage();
//        m.setTo(message.getTo());
//        m.setFrom("admin@shied.com");
//        m.setSubject(message.getSubject());
//        m.setText(message.getText());
//        mailSender.send(m);
//    }

}
