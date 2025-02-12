package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.*;

/*Entity class for verification request*/
@Entity
public class VerificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer Id;//id of request

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "User_id")
    private User user;//user of request

    @Enumerated
    private Status status;//status of request(Pending, Approved, Rejected)

    @Enumerated
    private TypeOfRequest typeOfRequest;//type of request(OwnerApproval, RenterApproval, PropertyApproval)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_profile_id")
    private Property_profile propertyProfile;//profile of property

    @Enumerated
    private TypeOfProperty typeOfProperty;//Type of property

    //Getters and setters
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TypeOfRequest getTypeOfRequest() {
        return typeOfRequest;
    }

    public void setTypeOfRequest(TypeOfRequest typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public Property_profile getPropertyProfile() {
        return propertyProfile;
    }

    public void setPropertyProfile(Property_profile propertyProfile) {
        this.propertyProfile = propertyProfile;
    }

    public TypeOfProperty getTypeOfProperty() {
        return typeOfProperty;
    }

    public void setTypeOfProperty(TypeOfProperty typeOfProperty) {
        this.typeOfProperty = typeOfProperty;
    }

    //Constructors and toString
    public VerificationRequest() {
    }

    public VerificationRequest(Integer id, User user, Status status, TypeOfRequest typeOfRequest,
                               Property_profile propertyProfile, TypeOfProperty typeOfProperty) {
        Id = id;
        this.user = user;
        this.status = status;
        this.typeOfRequest = typeOfRequest;
        this.propertyProfile = propertyProfile;
        this.typeOfProperty = typeOfProperty;
    }

    @Override
    public String toString() {
        return "VerificationRequest{" +
                "Id=" + Id +
                ", userId=" + (user != null ? user.getId() : "null") +

                ", status=" + status +
                ", typeOfRequest=" + typeOfRequest +
                '}';
    }
}
