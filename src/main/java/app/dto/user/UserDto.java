package app.dto.user;

import app.model.UserRole;
import app.model.UserType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto {

    private String id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String socialId;

    private String profilePic;

    private String facebookProfileUrl;

    private String description;

    private UserRole role;

    private UserType type;

    private LocalDateTime registrationTime;

}
