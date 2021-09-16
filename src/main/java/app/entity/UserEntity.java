package app.entity;

import app.model.UserRole;
import app.model.UserType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity  {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name="full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "facebook_profile_url")
    private String facebookProfileUrl;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "description")
    private String description;

    @Column(name = "user_role")
    private UserRole role;

    @Column(name = "user_type")
    private UserType type;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @OneToMany(mappedBy = "driver",targetEntity = TripEntity.class)
    private List<TripEntity> tripsAsDriver;

    @ManyToMany(mappedBy = "passengers", targetEntity = TripEntity.class)
    private List<TripEntity> tripsAsPassenger;

    @OneToMany(mappedBy = "searcher",targetEntity = SearchEntity.class)
    private List<SearchEntity> searches;

    @OneToMany(targetEntity = CarEntity.class,mappedBy = "owner")
    private List<CarEntity> cars;

    @OneToMany(targetEntity = TripApplicationEntity.class,mappedBy = "user")
    private List<TripApplicationEntity> applications;

    @OneToMany(targetEntity = MessageEntity.class,mappedBy = "user")
    private List<MessageEntity> messages;

    @OneToMany(targetEntity = NotificationEntity.class,mappedBy = "user")
    private List<NotificationEntity> notifications;

    @OneToMany(targetEntity = RatingEntity.class,mappedBy = "fromUser")
    private List<RatingEntity> receivedRatings;

    @OneToMany(targetEntity = RatingEntity.class,mappedBy = "toUser")
    private List<RatingEntity> givenRatings;

    public UserEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public List<TripEntity> getTripsAsDriver() {
        return tripsAsDriver;
    }

    public void setTripsAsDriver(List<TripEntity> tripsAsDriver) {
        this.tripsAsDriver = tripsAsDriver;
    }

    public List<TripEntity> getTripsAsPassenger() {
        return tripsAsPassenger;
    }

    public void setTripsAsPassenger(List<TripEntity> tripsAsPassenger) {
        this.tripsAsPassenger = tripsAsPassenger;
    }

    public List<SearchEntity> getSearches() {
        return searches;
    }

    public void setSearches(List<SearchEntity> searches) {
        this.searches = searches;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CarEntity> getCars() {
        return cars;
    }

    public void setCars(List<CarEntity> cars) {
        this.cars = cars;
    }

    public List<TripApplicationEntity> getApplications() {
        return applications;
    }

    public void setApplications(List<TripApplicationEntity> applications) {
        this.applications = applications;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public List<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    public List<RatingEntity> getReceivedRatings() {
        return receivedRatings;
    }

    public void setReceivedRatings(List<RatingEntity> receivedRatings) {
        this.receivedRatings = receivedRatings;
    }

    public List<RatingEntity> getGivenRatings() {
        return givenRatings;
    }

    public void setGivenRatings(List<RatingEntity> givenRatings) {
        this.givenRatings = givenRatings;
    }

    public String getFacebookProfileUrl() {
        return facebookProfileUrl;
    }

    public void setFacebookProfileUrl(String facebookProfileUrl) {
        this.facebookProfileUrl = facebookProfileUrl;
    }
}
