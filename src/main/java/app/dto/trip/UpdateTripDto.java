package app.dto.trip;

import app.dto.car.CarDto;
import app.dto.user.UserDto;
import app.model.City;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"car"})
@EqualsAndHashCode(exclude = {"car"}, callSuper = false)
public class UpdateTripDto extends TripDto {

    private CarDto car;

}
