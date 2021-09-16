package app.dto.search;

import app.dto.user.UserDto;
import app.model.City;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchDto {

    private String id;

    private LocalDateTime timePosted;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private City routeStartingPoint;

    private City routeEndPoint;

    private int seats;

    private String description;

}
