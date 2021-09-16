package app.dto.application;

import app.dto.trip.TripDto;
import app.dto.user.UserDto;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ApplicationDto {

    private String id;

    private String information;

}
