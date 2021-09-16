package app.dto.rating;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RatingDto {

    private String id;

    private String comment;

    private int stars;

}
