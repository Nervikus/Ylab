package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.stereotype.Component;

@Component
public interface MessageFilter {

    void messageProcess();

    String filter(String message);

    void sendMessage(String message);
}
