package app.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class RatingEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "stars")
    private int stars;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "from_user_id",referencedColumnName = "id")
    private UserEntity fromUser;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "to_user_id",referencedColumnName = "id")
    private UserEntity toUser;

    public RatingEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }

    public UserEntity getToUser() {
        return toUser;
    }

    public void setToUser(UserEntity toUser) {
        this.toUser = toUser;
    }
}
