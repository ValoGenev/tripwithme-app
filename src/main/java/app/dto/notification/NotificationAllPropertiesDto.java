package app.dto.notification;

import app.dto.user.UserDto;
import app.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"user"})
@EqualsAndHashCode(exclude = {"user"})
public class NotificationAllPropertiesDto {

    private String id;

    private String message;

    private String action;

    private UserDto user;
}
