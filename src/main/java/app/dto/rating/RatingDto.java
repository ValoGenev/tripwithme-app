package app.dto.rating;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RatingDto {


    private String id;

    private String comment;

    @Min(value = 0,message = "Rating should not be negative number")
    @Max(value = 5,message = "Rating must be less then 5")
    @NotNull(message = "stars cannot be null")
    private int stars;

}
