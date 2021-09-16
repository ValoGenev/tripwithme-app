package app.dto.user;

import app.dto.application.ApplicationDto;
import app.dto.car.CarDto;
import app.dto.message.MessageDto;
import app.dto.notification.NotificationDto;
import app.dto.rating.RatingDto;
import app.dto.search.SearchDto;
import app.dto.trip.TripDto;
import app.entity.*;
import app.model.UserRole;
import app.model.UserType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@ToString(exclude={"tripsAsDriver","tripsAsPassenger","searches","cars","applications","messages","notifications","givenRatings","receivedRatings"})
@EqualsAndHashCode(exclude = {"tripsAsDriver","tripsAsPassenger","searches","cars","applications","messages","notifications","givenRatings","receivedRatings"})
public class UserAllPropertiesDto {

    private String id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String socialId;

    private String profilePic;

    private String facebookProfileUrl;

    private String description;

    private UserRole role;

    private UserType type;

    private LocalDateTime registrationTime;

    private List<TripDto> tripsAsDriver;

    private List<TripDto> tripsAsPassenger;

    private List<SearchDto> searches;

    private List<CarDto> cars;

    private List<ApplicationDto> applications;

    private List<MessageDto> messages;

    private List<NotificationDto> notifications;

    private List<RatingDto> givenRatings;

    private List<RatingDto> receivedRatings;

}
