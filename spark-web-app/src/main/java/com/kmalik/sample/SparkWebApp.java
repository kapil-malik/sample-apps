package com.kmalik.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.kmalik.sample")
@EnableAutoConfiguration(exclude = VelocityAutoConfiguration.class)
public class SparkWebApp {

  public static void main(String[] args) {
    SpringApplication.run(SparkWebApp.class, args);
  }
}
