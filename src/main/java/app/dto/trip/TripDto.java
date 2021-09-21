package app.dto.trip;

import app.dto.common.SearchTripCommonProperties;
import app.model.City;
import app.util.CustomDateDeserializer;
import app.validations.route.ValidFullRoute;
import app.validations.time.ValidTimeFrame;
import app.validations.groups.FieldChecks;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import javax.validation.groups.Default;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@ValidFullRoute(groups = {Default.class})
public class TripDto extends SearchTripCommonProperties {

    @Size(min = 2,max = 10,message = "full route size must be between 2 and 10")
    private List<City> fullRoute;

    private int price;

}
