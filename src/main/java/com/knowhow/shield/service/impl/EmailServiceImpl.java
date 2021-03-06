package com.knowhow.shield.service.impl;

import com.knowhow.postoffice.contract.EmailDto;
import com.knowhow.postoffice.contract.Template;
import com.knowhow.shield.config.AppProperties;
import com.knowhow.shield.model.User;
import com.knowhow.shield.service.EmailService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

    private static final String ACTIVATION_MAIL_SUBJECT = "Activation Code";
    private final AppProperties appProperties;
    private final Source source;


    @Override
    @SendTo(Source.OUTPUT)
    public EmailDto sendActivationEmail(User user, String token) {
        Map<String, Object> map = new HashMap();
        map.put("name", user.getFirstName());
        map.put("activateUrl", appProperties.getApp().getActivationDomainUrl() + "/" + token);

        EmailDto message = new EmailDto(ACTIVATION_MAIL_SUBJECT, user.getEmail(), Template.ACTIVATION, map);
        source.output().send((MessageBuilder.withPayload(message).build()));
        return message;
    }

}
