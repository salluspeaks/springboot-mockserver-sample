package com.sample.company;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.company.model.Phone;
import org.mockserver.integration.ClientAndServer;

public class MockExpectations {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void setUp(ClientAndServer mockServer) throws JsonProcessingException {

    mockServer
        .when(
            request()
                .withMethod("POST")
                .withPath("/validate")
            .withBody(buildJson(buildPhoneValidationRequest("9876543210")))
        )
        .respond(
            response().withStatusCode(200)
        );

    mockServer
        .when(
            request()
                .withMethod("POST")
                .withPath("/validate")
                .withBody(buildJson(buildPhoneValidationRequest("abcd")))
        )
        .respond(
            response().withStatusCode(400)
        );

  }


  private static Phone buildPhoneValidationRequest(String phone) {
    return Phone.builder().phone(phone).build();

  }

  private static String buildJson(Object object) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(object);

  }

}
