package com.ducgiang.identify_service.dto.request;

import com.ducgiang.identify_service.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min = 10 , message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
}
