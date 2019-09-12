package com.knowhow.shield.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EmailChannel {

    String INPUT = "input";

    String OUTPUT = "output";

    @Input
    SubscribableChannel emailInput();

    @Output
    MessageChannel emailOutput();
}
