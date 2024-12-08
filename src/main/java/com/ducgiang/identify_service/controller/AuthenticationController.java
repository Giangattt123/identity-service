package com.ducgiang.identify_service.controller;

import com.ducgiang.identify_service.dto.request.ApiResponse;
import com.ducgiang.identify_service.dto.request.AuthenticationRequest;
import com.ducgiang.identify_service.dto.request.IntrospectRequest;
import com.ducgiang.identify_service.dto.response.AuthenticationResponse;
import com.ducgiang.identify_service.dto.response.IntrospectResponse;
import com.ducgiang.identify_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
//        return ApiResponse.<AuthenticationResponse>builder()
//                .result(AuthenticationResponse.builder()
//                        .authenticated(result)
//                        .build())
//                .build();
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
//        return ApiResponse.<AuthenticationResponse>builder()
//                .result(AuthenticationResponse.builder()
//                        .authenticated(result)
//                        .build())
//                .build();
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
