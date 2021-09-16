package app.dto.trip;

import app.dto.car.CarDto;
import app.dto.message.MessageDto;
import app.dto.user.UserDto;
import app.entity.CarEntity;
import app.entity.MessageEntity;
import app.entity.TripApplicationEntity;
import app.entity.UserEntity;
import app.model.City;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"driver","car","messages","passengers","applicants"})
@EqualsAndHashCode(exclude = {"driver","car","messages","passengers","applicants"}, callSuper = false)
public class TripAllPropertiesDto extends TripDto {

    private UserDto driver;

    private CarDto car;

    private List<MessageDto> messages;

    private List<UserDto> passengers;

    private List<TripDto> applicants;

}
