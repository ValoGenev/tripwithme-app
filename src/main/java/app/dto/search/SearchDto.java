package app.dto.search;

import app.dto.common.SearchTripCommonProperties;
import app.dto.user.UserDto;
import app.model.City;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SearchDto extends SearchTripCommonProperties {

}
