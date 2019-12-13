package com.sample.company.service;

import com.sample.company.exception.DataValidationException;
import com.sample.company.model.UserRequest;
import com.sample.company.model.UserResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private Map<Integer, UserRequest> userDb = new HashMap<>();

  private ValidatorProxyService validatorProxyService;

  public UserService(ValidatorProxyService validatorProxyService) {
    this.validatorProxyService = validatorProxyService;

  }

  public UserResponse saveUser(UserRequest userRequest) {
    //validate phone number
    if(!validatorProxyService.validateUserPhoneNumber(userRequest.getPhone())){
      throw new DataValidationException("phone validation failed");
    }
    int id = Math.abs(userRequest.hashCode());
    userDb.put(id, userRequest);

    return UserResponse.builder()
        .id(id)
        .name(userRequest.getName())
        .phone(userRequest.getPhone())
        .build();

  }

  public Optional<UserResponse> getUser(int id) {
    UserRequest user = userDb.get(id);
    return user != null ? Optional.of(UserResponse
        .builder().id(id).name(user.getName()).role(user.getRole())
        .phone(user.getPhone())
        .build())
        : Optional.empty();

  }

}
