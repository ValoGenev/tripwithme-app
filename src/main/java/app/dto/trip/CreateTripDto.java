package app.dto.trip;

import app.dto.car.CarDto;
import app.dto.user.UserDto;
import app.model.City;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"driver", "passengers"})
@EqualsAndHashCode(callSuper = false, exclude = {"driver", "passengers"})
public class CreateTripDto extends UpdateTripDto {

    private UserDto driver;

    private List<UserDto> passengers;
}
