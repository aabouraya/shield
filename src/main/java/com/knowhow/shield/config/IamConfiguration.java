package com.knowhow.shield.config;

import java.time.Clock;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
class IamConfiguration {

    private DataSource dataSource;

    IamConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

