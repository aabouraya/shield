package com.knowhow.shield;

import com.knowhow.shield.config.AppProperties;
import com.knowhow.shield.repository.impl.NaturalRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableBinding(Source.class)
@EnableConfigurationProperties({AppProperties.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(repositoryBaseClass = NaturalRepositoryImpl.class)
public class ShieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShieldApplication.class, args);
    }

}
