package app.dto.car;

import app.model.TrunkSize;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CarDto {

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

}
