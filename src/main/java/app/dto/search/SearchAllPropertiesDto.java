package app.dto.search;

import app.dto.application.ApplicationDto;
import app.dto.user.UserDto;
import app.entity.UserEntity;
import app.model.City;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"searcher","applications"})
@EqualsAndHashCode(exclude = {"searcher","applications"})
public class SearchAllPropertiesDto {

    private String id;

    private LocalDateTime timePosted;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private City routeStartingPoint;

    private City routeEndPoint;

    private int seats;

    private String description;

    private UserDto searcher;

    private List<ApplicationDto> applications;

}
