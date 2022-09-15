package com.example.loan_backend;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LoanBackendApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(LoanBackendApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}
