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
@EqualsAndHashCode(exclude = {"searcher","applications"}, callSuper = false)
public class SearchAllPropertiesDto extends SearchDto {

    private UserDto searcher;

    private List<ApplicationDto> applications;

}
