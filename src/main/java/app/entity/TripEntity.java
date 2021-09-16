package app.entity;

import app.model.City;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@Entity
@Table(name = "trips")
public class TripEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "time_posted")
    private LocalDateTime timePosted;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "route_start_point")
    @Enumerated(EnumType.STRING)
    private City routeStartingPoint;

    @Column(name = "route_end_point")
    @Enumerated(EnumType.STRING)
    private City routeEndPoint;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "full_route", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private List<City> fullRoute;

    @Column(name = "seats")
    private int seats;

    @Column(name = "seats_left")
    private int seatsLeft;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "driver_id",referencedColumnName = "id")
    private UserEntity driver;

    @ManyToOne(targetEntity = CarEntity.class)
    @JoinColumn(name = "car_id",referencedColumnName = "id")
    private CarEntity car;

    @OneToMany(targetEntity = MessageEntity.class,mappedBy = "trip")
    private List<MessageEntity> messages;

    @ManyToMany
    @JoinTable(name = "trips_users",
            joinColumns = @JoinColumn(name = "trip_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<UserEntity> passengers;

    @OneToMany(targetEntity = TripApplicationEntity.class,mappedBy = "trip")
    private List<TripApplicationEntity> applicants;


    public TripEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public City getRouteStartingPoint() {
        return routeStartingPoint;
    }

    public void setRouteStartingPoint(City routeStartingPoint) {
        this.routeStartingPoint = routeStartingPoint;
    }

    public City getRouteEndPoint() {
        return routeEndPoint;
    }

    public void setRouteEndPoint(City routeEndPoint) {
        this.routeEndPoint = routeEndPoint;
    }

    public List<City> getFullRoute() {
        return fullRoute;
    }

    public void setFullRoute(List<City> fullRoute) {
        this.fullRoute = fullRoute;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getDriver() {
        return driver;
    }

    public void setDriver(UserEntity driver) {
        this.driver = driver;
    }

    public List<UserEntity> getPassengers() {
        return passengers;
    }

    public List<TripApplicationEntity> getApplicants() {
        return applicants;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public void setApplicants(List<TripApplicationEntity> applicants) {
        this.applicants = applicants;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public void setPassengers(List<UserEntity> passengers) {
        this.passengers = passengers;
    }
}
