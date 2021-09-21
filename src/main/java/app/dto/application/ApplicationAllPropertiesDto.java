package app.dto.application;

import app.dto.search.SearchDto;
import app.dto.trip.TripDto;
import app.dto.user.UserDto;
import app.entity.TripEntity;
import app.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"user","trip","search"})
@EqualsAndHashCode(exclude = {"user","trip","search"})
public class ApplicationAllPropertiesDto {

    private String id;

    private String information;

    @NotNull(message = "trip cannot be null")
    private TripDto trip;

    @NotNull(message = "user cannot be null")
    private UserDto user;

    @NotNull(message = "search cannot be null")
    private SearchDto search;

}
