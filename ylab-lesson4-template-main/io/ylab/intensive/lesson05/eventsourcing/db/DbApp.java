package io.ylab.intensive.lesson05.eventsourcing.db;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
  public static void main(String[] args) throws Exception {
    // тут создание и запуск приложения работы с БД
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    DataProcessor dataProcessor = applicationContext.getBean(DataProcessor.class);
    dataProcessor.handleMessage();
  }
}
