package app.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CreateUserDto {

    @NotBlank(message = "Full name should not be null or empty")
    private String fullName;

    private String email; //MANDATORY for REAL USERS (ADMIN,USER)

    private String phoneNumber; // OPTIONAL FOR NOW

    private String socialId;

    private String facebookProfileUrl;

    private String profilePic; // OPTION FOR NOW

    private String description;

}
