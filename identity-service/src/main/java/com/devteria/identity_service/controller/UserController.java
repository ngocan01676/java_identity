package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users")
public class UserController {
    //private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @PostMapping()
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(userCreationRequest));
        return apiResponse;
    }

//    @GetMapping()
//    List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
        @GetMapping
        ApiResponse<List<UserResponse>> getUsers() {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("Username {}", authentication.getName());
            //log.info("Roles {}", authentication.getAuthorities());
            authentication.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority {}", grantedAuthority));
            return ApiResponse.<List<UserResponse>>builder()
                    .result(userService.getUsers())
                    .build();
        }

    @GetMapping("/{userId}")
    UserResponse getUserById(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PutMapping(path = "/{userId}")
    UserResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable String userId) {
        return userService.updateUser(userId, userUpdateRequest);
    }

    @DeleteMapping(path = "/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User deleted";
    }

    @GetMapping(path = "/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo()).build();
    }

}
