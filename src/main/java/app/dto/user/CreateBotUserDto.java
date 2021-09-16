package app.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CreateBotUserDto {

    @Null(message = "id should be null")
    private String id;

    @NotBlank(message = "Full name should not be null or empty")
    private String fullName;

    private String phoneNumber;

    @NotBlank(message = "Social id cannot be null or empty")
    private String socialId;

    @NotBlank(message = "Profile pic cannot be nul or empty")
    private String profilePic;

    private String facebookProfileUrl;


}
