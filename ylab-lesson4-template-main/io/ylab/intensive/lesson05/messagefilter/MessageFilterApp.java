package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MessageFilterApp {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    TableCreator creator = applicationContext.getBean(TableCreator.class);
    creator.addToTable();
    MessageFilter messageFilter = applicationContext.getBean(MessageFilterImpl.class);
    messageFilter.messageProcess();
  }
}
