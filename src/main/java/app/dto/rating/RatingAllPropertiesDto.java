package app.dto.rating;

import app.dto.user.UserDto;
import app.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"fromUser","toUser"})
@EqualsAndHashCode(exclude = {"fromUser","toUser"})
public class RatingAllPropertiesDto {

    private String id;

    private String comment;

    private int stars;

    private UserDto fromUser;

    private UserDto toUser;
}
