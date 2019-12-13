package com.sample.company;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import com.sample.company.model.ApiExceptionResponse;
import com.sample.company.model.UserRequest;
import com.sample.company.model.UserResponse;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {UserServiceApplication.class})
public class UserControllerTest {

  @LocalServerPort
  private int port;

  private TestRestTemplate testRestTemplate = new TestRestTemplate();

  private static ClientAndServer mockServer;

  @BeforeClass
  public static void startMockServer() throws IOException {
    mockServer = startClientAndServer(1080);
    MockExpectations.setUp(mockServer);
  }

  @Test
  public void testCreateUser(){

    ResponseEntity<UserResponse> responseEntity =  testRestTemplate
        .postForEntity(createURLWithPort("/user"),buildMockUserRequest("9876543210"),
            UserResponse.class);
    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    UserResponse userResponse = responseEntity.getBody();
    Assert.assertEquals("user", userResponse.getName());

  }

  @Test
  public void testCreateUserInvalidPhone(){

    ResponseEntity<ApiExceptionResponse> responseEntity =  testRestTemplate
        .postForEntity(createURLWithPort("/user"),buildMockUserRequest("abcd"),
            ApiExceptionResponse.class);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    ApiExceptionResponse error = responseEntity.getBody();
    Assert.assertEquals("phone validation failed", error.getDescription());

  }

  private UserRequest buildMockUserRequest(String phone){
    return new UserRequest("user", phone, "user");
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}
