package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/*Entity class for property's profile*/
@Entity
@Table
public class Property_profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;//id of the property

    @Column(name="address")
    @NotBlank
    @Size(max = 100, message = "Address must not exceed 100 characters")
    private String address;//address of property

    @Column(name = "location")
    @NotBlank
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String location;//location of property

    @Column(name="square_meters")
    @NotNull
    @Min(value = 1, message = "Square meters must be greater than 0")
    private int square_meters;//square meters number of the property

    @Column(name="bedrooms")
    @NotNull
    @Min(value = 0, message = "Bedrooms cannot be negative")
    private int bedrooms;//bedroom number of the property

    @Column(name="floor")
    @NotNull
    private int floor;//floor number of the property

    @Column(name="parking")
    private boolean parking;//has the property parking;

    @Column(name="year_of_construct")
    @NotNull
    @Min(value = 1950, message = "Year of construct must be later than 1950")
    private int year_of_construct;//year of construct number of property

    @Column(name="cost")
    @NotNull
    @Min(value = 0, message = "Value must be a positive value")
    private double cost;//cost of property

    @OneToOne(mappedBy = "property_profile", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private Property property;//property of which the profile is

    /*Getters and setters*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSquare_meters() {
        return square_meters;
    }

    public void setSquare_meters(int square_meters) {
        this.square_meters = square_meters;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public int getYear_of_construct() {
        return year_of_construct;
    }

    public void setYear_of_construct(int year_of_construct) {
        this.year_of_construct = year_of_construct;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    /*Constructors and toString*/
    public Property_profile() {
    }

    public Property_profile(Integer id, String address, String location, int square_meters, int bedrooms, int floor,
                            boolean parking, int year_of_construct, double cost, Property property) {
        this.id = id;
        this.address = address;
        this.location = location;
        this.square_meters = square_meters;
        this.bedrooms = bedrooms;
        this.floor = floor;
        this.parking = parking;
        this.year_of_construct = year_of_construct;
        this.cost = cost;
        this.property = property;
    }

    @Override
    public String toString() {
        return "Property_profile{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", square_meters=" + square_meters +
                ", bedrooms=" + bedrooms +
                ", floor=" + floor +
                ", parking=" + parking +
                ", year_of_construct=" + year_of_construct +
                ", cost=" + cost +
                '}';
    }
}
