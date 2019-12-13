package com.sample.company.rest;

import com.sample.company.model.UserRequest;
import com.sample.company.model.UserResponse;
import com.sample.company.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

  private UserService userService;

  @Autowired
  private UserController(UserService userService){
    this.userService = userService;

  }

  @PostMapping("/user")
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
    UserResponse userResponse = userService.saveUser(userRequest);
    return ResponseEntity.ok(userResponse);

  }

  @GetMapping("/user/{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable("id") int id){
    Optional<UserResponse>  userResponse = userService.getUser(id);
    if(userResponse.isPresent()) {
      return ResponseEntity.ok(userResponse.get());
    }else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

  }

}
