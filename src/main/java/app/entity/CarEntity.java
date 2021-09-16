package app.entity;

import app.model.TrunkSize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cars")
public class CarEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name="model")
    private String model;

    @Column(name="year")
    private int year;

    @Column(name="seats")
    private int seats;

    @Column(name="color")
    private String color;

    @Column(name="air_conditioning")
    private boolean airConditioning;

    @Column(name = "trunkSize")
    @Enumerated(value = EnumType.STRING)
    private TrunkSize trunkSize;

    @Column(name="foodAllowed")
    private boolean foodAllowed;

    @Column(name="drinksAllowed")
    private boolean drinksAllowed;

    @Column(name="petsAllowed")
    private boolean petsAllowed;

    @Column(name="smokeInTheCar")
    private boolean smokeInTheCar;

    @Column(name="description")
    private String description;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name="owner_id",referencedColumnName = "id")
    private UserEntity owner;

    @OneToMany(targetEntity = TripEntity.class,mappedBy = "car")
    private List<TripEntity> trips;


    public CarEntity(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public TrunkSize getTrunkSize() {
        return trunkSize;
    }

    public void setTrunkSize(TrunkSize trunkSize) {
        this.trunkSize = trunkSize;
    }

    public boolean isFoodAllowed() {
        return foodAllowed;
    }

    public void setFoodAllowed(boolean foodAllowed) {
        this.foodAllowed = foodAllowed;
    }

    public boolean isDrinksAllowed() {
        return drinksAllowed;
    }

    public void setDrinksAllowed(boolean drinksAllowed) {
        this.drinksAllowed = drinksAllowed;
    }

    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public boolean isSmokeInTheCar() {
        return smokeInTheCar;
    }

    public void setSmokeInTheCar(boolean smokeInTheCar) {
        this.smokeInTheCar = smokeInTheCar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TripEntity> getTrips() {
        return trips;
    }

    public void setTrips(List<TripEntity> trips) {
        this.trips = trips;
    }


}
