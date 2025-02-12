package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.*;

import java.util.List;

/*Entity class for owner which extends the user entity*/
@Entity
public class Owner{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id of owner
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false, unique = true)
    private User user;//user linked with owner
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Property> properties;//Properties of the owner

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="owner_renter",
            joinColumns = @JoinColumn(name="owner_id"),
            inverseJoinColumns = @JoinColumn(name="renter_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "renter_id"})
    )
    private List<Renter> renters;//renters of the owner

    /*Getters and setters*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Renter> getRenters() {
        return renters;
    }

    public void setRenters(List<Renter> renters) {
        this.renters = renters;
    }

    /*Constructors and toString*/
    public Owner() {
    }

    public Owner(Integer id, User user, List<Property> properties, List<Renter> renters) {
        this.id = id;
        this.user = user;
        this.properties = properties;
        this.renters = renters;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", user=" + user +
                ", properties=" + properties +
                ", renters=" + renters +
                '}';
    }
}
