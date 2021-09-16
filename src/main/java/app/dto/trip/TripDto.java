package app.dto.trip;

import app.model.City;
import app.util.CustomDateDeserializer;
import app.validations.route.ValidFullRoute;
import app.validations.time.ValidTimeFrame;
import app.validations.groups.FieldChecks;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.validation.GroupSequence;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@GroupSequence({FieldChecks.class,TripDto.class})
@ValidTimeFrame(groups = {Default.class})
@ValidFullRoute(groups = {Default.class})
public class TripDto {

    @Null(message = "id should be null")
    private String id;

    @Null(message = "time posted cannot be set")
    private LocalDateTime timePosted;

    @NotNull(groups = {FieldChecks.class})
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Future(message = "Start time should not be in the past")
    private LocalDateTime startTime;

    @NotNull
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Future(message = "End time should not be in the past")
    private LocalDateTime endTime;

    @NotNull(message = "city cannot be null")
    private City routeStartingPoint;

    @NotNull(message = "city cannot be null")
    private City routeEndPoint;

    @Size(min = 2,max = 10,message = "full route size must be between 2 and 10")
    private List<City> fullRoute;

    private int seats;

    private int seatsLeft;

    private int price;

    private String description;


}