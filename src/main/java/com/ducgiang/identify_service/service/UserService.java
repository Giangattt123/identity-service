package com.ducgiang.identify_service.service;

import com.ducgiang.identify_service.dto.request.UserCreationRequest;
import com.ducgiang.identify_service.dto.request.UserUpdateRequest;
import com.ducgiang.identify_service.dto.response.UserResponse;
import com.ducgiang.identify_service.entity.User;
import com.ducgiang.identify_service.enums.Role;
import com.ducgiang.identify_service.exception.AppException;
import com.ducgiang.identify_service.exception.ErrorCode;
import com.ducgiang.identify_service.mapper.UserMapper;
import com.ducgiang.identify_service.repository.RoleRepository;
import com.ducgiang.identify_service.repository.UserRepository;
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

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
//        if (userRepository.existsByUsername(request.getUsername()))
//            throw new RuntimeException("ErrorCode.USER_EXISTED");

//        Builder class -> create Object faster -> Annotation Builder from Lombok
//        UserCreationRequest ucr = UserCreationRequest.builder()
//                .username("")
//                .password("")
//                .firstName("")
//                .lastName("")
//                .build();
        User user = userMapper.toUser(request);
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<String>();
        roles.add(Role.USER.name());

       // user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<UserResponse> getUsers() {
        log.info("In method get users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String userId) {
        log.info("Method get user by id");
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId , UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user , request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }


    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
