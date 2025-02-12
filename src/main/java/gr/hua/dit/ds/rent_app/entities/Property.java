package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.*;

import java.util.List;

/*Entity class for property*/
@Entity
@Table
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//id of the property

    @Enumerated
    private TypeOfProperty typeOfProperty;//type of property (detached_house, floor_apartment, maisonette)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Property_profile_id", referencedColumnName = "id")
    private Property_profile property_profile;//profile of the property with details

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="Owner_id")
    private Owner owner;//owner of the property

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="Renter_id")
    private Renter renter;//renter of the property

    @OneToOne(mappedBy = "property", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private User user;//user of the property

    @OneToMany(mappedBy = "property", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<RentApplication> rentApplications;//rent applications for the property

    //Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeOfProperty getTypeOfProperty() {
        return typeOfProperty;
    }

    public void setTypeOfProperty(TypeOfProperty typeOfProperty) {
        this.typeOfProperty = typeOfProperty;
    }

    public Property_profile getProperty_profile() {
        return property_profile;
    }

    public void setProperty_profile(Property_profile property_profile) {
        this.property_profile = property_profile;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public List<RentApplication> getRentApplications() {
        return rentApplications;
    }

    public void setRentApplications(List<RentApplication> rentApplications) {
        this.rentApplications = rentApplications;
    }

    //Constructors and toString
    public Property() {
    }

    public Property(Integer id, TypeOfProperty typeOfProperty, Property_profile property_profile,
                    Owner owner, Renter renter, List<RentApplication> rentApplications) {
        this.id = id;
        this.typeOfProperty = typeOfProperty;
        this.property_profile = property_profile;
        this.owner = owner;
        this.renter = renter;
        this.rentApplications = rentApplications;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                '}';
    }
}




