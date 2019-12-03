package com.knowhow.shield.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ShieldConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

