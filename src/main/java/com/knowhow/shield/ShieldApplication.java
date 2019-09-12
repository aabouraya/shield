package com.knowhow.shield;

import com.knowhow.shield.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
@EnableBinding({Source.class, Sink.class})
@EnableConfigurationProperties({AppProperties.class})
public class ShieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShieldApplication.class, args);
    }

}
