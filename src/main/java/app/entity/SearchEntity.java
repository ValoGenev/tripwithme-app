package app.entity;

import app.model.City;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "searches")
public class SearchEntity {

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

    @Column(name="seats")
    private int seats;

    @Column(name="description")
    private String description;

    @OneToMany(targetEntity = TripApplicationEntity.class,mappedBy = "search")
    private List<TripApplicationEntity> applications;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name="searcher_id",referencedColumnName = "id")
    private UserEntity searcher;

    public SearchEntity() {
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public UserEntity getSearcher() {
        return searcher;
    }

    public void setSearcher(UserEntity searcher) {
        this.searcher = searcher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TripApplicationEntity> getApplications() {
        return applications;
    }

    public void setApplications(List<TripApplicationEntity> applications) {
        this.applications = applications;
    }
}
