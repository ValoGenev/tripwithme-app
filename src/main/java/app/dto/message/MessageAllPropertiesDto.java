package app.dto.message;

import app.dto.trip.TripDto;
import app.dto.user.UserDto;
import app.entity.TripEntity;
import app.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"user","trip"})
@EqualsAndHashCode(exclude = {"user","trip"})
public class MessageAllPropertiesDto {

    private String id;

    private String message;

    private LocalDateTime sentTime;

    private UserDto user;

    private TripDto trip;

}
