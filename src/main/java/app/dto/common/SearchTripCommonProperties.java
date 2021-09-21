package app.dto.common;

import app.dto.trip.TripDto;
import app.model.City;
import app.util.CustomDateDeserializer;
import app.validations.groups.FieldChecks;
import app.validations.time.ValidTimeFrame;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@GroupSequence({FieldChecks.class, SearchTripCommonProperties.class})
@ValidTimeFrame(groups = {Default.class})
public class SearchTripCommonProperties {

    @Null(message = "id should be null")
    private String id;

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

    @Min(value = 0,message = "seats cannot be negative number")
    @Max(value = 10,message = "seats cannot be more then 10")
    private int seats;

    private String description;
}
