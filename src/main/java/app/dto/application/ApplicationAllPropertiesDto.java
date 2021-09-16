package app.dto.application;

import app.dto.trip.TripDto;
import app.dto.user.UserDto;
import app.entity.TripEntity;
import app.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"user","trip"})
@EqualsAndHashCode(exclude = {"user","trip"})
public class ApplicationAllPropertiesDto {

    private String id;

    private String information;

    private TripDto trip;

    private UserDto user;

}
