package app.dto.car;

import app.dto.trip.TripDto;
import app.dto.user.UserDto;
import app.entity.TripEntity;
import app.entity.UserEntity;
import app.model.TrunkSize;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"owner","trips"})
@EqualsAndHashCode(exclude = {"owner","trips"})
public class CarAllPropertiesDto {

    private String id;

    private String picUrl;

    private String model;

    private int year;

    private int seats;

    private String color;

    private boolean airConditioning;

    private TrunkSize trunkSize;

    private boolean foodAllowed;

    private boolean drinksAllowed;

    private boolean petsAllowed;

    private boolean smokeInTheCar;

    private String description;

    @NotNull(message = "owner cannot be null")
    private UserDto owner;

    private List<TripDto> trips;

}
