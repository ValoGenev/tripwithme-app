package app.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "trip_applications")
public class TripApplicationEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "description")
    private String information;

    @ManyToOne(targetEntity = SearchEntity.class)
    @JoinColumn(name = "search_id",referencedColumnName = "id")
    private SearchEntity search;

    @ManyToOne(targetEntity = TripEntity.class)
    @JoinColumn(name = "trip_id",referencedColumnName = "id")
    private TripEntity trip;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity user;

    public TripApplicationEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public TripEntity getTrip() {
        return trip;
    }

    public void setTrip(TripEntity trip) {
        this.trip = trip;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SearchEntity getSearch() {
        return search;
    }

    public void setSearch(SearchEntity search) {
        this.search = search;
    }
}
