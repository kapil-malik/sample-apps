package com.kmalik.sample;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.kmalik.sample.xml")
@EnableAutoConfiguration(exclude = VelocityAutoConfiguration.class)
@PropertySource("classpath:xml.properties")
public class XmlApp {
}
