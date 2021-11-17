package com.example.nevernote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NevernoteApplication {

  public static void main(String[] args) {
    SpringApplication.run(NevernoteApplication.class, args);
  }
}
