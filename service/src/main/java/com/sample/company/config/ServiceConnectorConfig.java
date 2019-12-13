package com.sample.company.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ServiceConnectorConfig {

  @Value("${endpoint.validationBaseUrl}")
  private String validationBaseUrl;

}
