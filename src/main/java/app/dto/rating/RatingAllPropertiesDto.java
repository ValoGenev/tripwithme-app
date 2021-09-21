package app.dto.rating;

import app.dto.common.SearchTripCommonProperties;
import app.dto.user.UserDto;
import app.entity.UserEntity;
import app.validations.groups.FieldChecks;
import app.validations.ratings.AssertDifferentUsers;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"fromUser","toUser"})
@EqualsAndHashCode(exclude = {"fromUser","toUser"}, callSuper = false)
@GroupSequence({FieldChecks.class, RatingAllPropertiesDto.class})
@AssertDifferentUsers(groups = {Default.class})
public class RatingAllPropertiesDto extends RatingDto {

    @NotNull(message = "fromUser cannot be null",groups = {FieldChecks.class})
    private UserDto fromUser;

    @NotNull(message = "toUser cannot be null",groups = {FieldChecks.class})
    private UserDto toUser;
}
