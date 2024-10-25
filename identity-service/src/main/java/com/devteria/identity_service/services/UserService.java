package com.devteria.identity_service.services;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.enums.Role;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.UserMapper;
import com.devteria.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service

//tạo 1 constructor cho tất cả các biến define là final
@RequiredArgsConstructor
// bất cứ field nào trong class không khai báo gì hết sẽ tự động định nghĩa là final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserMapper userMapper;
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            //throw new RuntimeException("Username already exists");
            throw new AppException(ErrorCode.USER_EXISTED);
        }
//        UserCreationRequest request1 = UserCreationRequest.builder()
//                .username(request.getUsername())
//                .build();

//        User user = new User();
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setPassword(request.getPassword());
//        user.setUsername(request.getUsername());
//        user.setDob(request.getDob());
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    //PreAuthorize
//    PreAuthorize kiểm tra trước khi vào method, tức thỏa mãn điều kiện thì mới truy cập vào method
//    PostAuthorize kiểm tra sau khi method thực hiện xog nếu thỏa mãn thì return về, không thỏa sẽ chặn không trả về


    @PreAuthorize("hasRole('ADMIN')")
    // PreAuthorize spring se tao 1 proxy trước hàm này
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    //@PostAuthorize("hasRole('ADMIN')")
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setPassword(request.getPassword());
//        user.setDob(request.getDob());
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
