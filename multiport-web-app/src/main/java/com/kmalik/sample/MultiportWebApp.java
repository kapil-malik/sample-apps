package com.kmalik.sample;

import org.springframework.boot.SpringApplication;

public class MultiportWebApp {

  public static void main(String[] args) {
    SpringApplication.run(TextApp.class, args);
    SpringApplication.run(XmlApp.class, args);
  }
}
