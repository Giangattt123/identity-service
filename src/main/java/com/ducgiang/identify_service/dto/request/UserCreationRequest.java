package com.ducgiang.identify_service.dto.request;

import com.ducgiang.identify_service.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // private default properties
public class UserCreationRequest {
    @Size(min = 3 , message = "USERNAME_INVALID")
    String username;
    @Size(min = 8 , message = "INVALID_PASSWORD")
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 10 , message = "INVALID_DOB")
    LocalDate dob;

}
