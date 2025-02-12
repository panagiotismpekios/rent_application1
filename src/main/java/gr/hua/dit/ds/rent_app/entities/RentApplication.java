package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.*;

/*Entity for rent application*/
@Entity
public class RentApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;//id of application

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="Property_id")
    private Property property;//property of application

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "Renter_id")
    private Renter renter;//renter of application

    @Enumerated
    private Status status;//status of application (Pending, Approved, Rejected)

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenters(Renter renter) {
        this.renter = renter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    //Constructors and toString
    public RentApplication() {
    }

    public RentApplication(Long id, Property property, Renter renter, Status status) {
        this.id = id;
        this.property = property;
        this.renter = renter;
        this.status = status;
    }

    @Override
    public String toString() {
        return "RentApplication{" +
                "id=" + id +
                ", property=" + property +
                ", renter=" + renter +
                ", status=" + status +
                '}';
    }
}
