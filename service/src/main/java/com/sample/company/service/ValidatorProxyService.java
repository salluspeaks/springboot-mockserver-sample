package com.sample.company.service;

import com.sample.company.config.ServiceConnectorConfig;
import com.sample.company.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidatorProxyService {


  private RestTemplate restTemplate;
  private ServiceConnectorConfig serviceConnectorConfig;

  @Autowired
  public ValidatorProxyService(RestTemplate restTemplate,
      ServiceConnectorConfig serviceConnectorConfig){
    this.restTemplate = restTemplate;
    this.serviceConnectorConfig = serviceConnectorConfig;

  }

  public boolean validateUserPhoneNumber(String number){
    try {
      restTemplate
          .postForEntity(serviceConnectorConfig.getValidationBaseUrl(),
              Phone.builder().phone(number).build(), null);
      return true;
    }catch (HttpClientErrorException ex) {
      //TODO: Handle exception by throwing exception
      return false;

    }

  }


}
