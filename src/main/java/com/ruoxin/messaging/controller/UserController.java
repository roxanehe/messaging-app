package com.ruoxin.messaging.controller;

import com.ruoxin.messaging.enums.Status;
import com.ruoxin.messaging.request.ActivateUserRequest;
import com.ruoxin.messaging.request.RegisterUserRequest;
import com.ruoxin.messaging.response.BaseResponse;
import com.ruoxin.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register") // /users/register
    public BaseResponse register(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
        //        System.out.println(registerUserRequest);
        this.userService.register(registerUserRequest.getUsername(),
                                  registerUserRequest.getPassword(),
                                  registerUserRequest.getRepeatPassword(),
                                  registerUserRequest.getEmail(),
                                  registerUserRequest.getAddress(),
                                  registerUserRequest.getNickname(),
                                  registerUserRequest.getGender());
        return new BaseResponse(Status.OK);
    }

    @PostMapping("/activate")
    public BaseResponse activate(@RequestBody ActivateUserRequest activateUserRequest) throws Exception {
        this.userService.activate(activateUserRequest.getUsername(),
                                  activateUserRequest.getValidationCode());
        return new BaseResponse(Status.OK);
    }

    @PostMapping("/login") // /users/login
    public void login() {

    }
}
