package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.Entity;

/*Class for search filter*/
public class SearchFilter {

    private String location;

    private Double minCost;

    private Double maxCost;

    private TypeOfProperty typeOfProperty;

    private Integer minSquare_Meters;

    private Integer maxSquare_Meters;

    private Integer bedrooms;

    private Boolean parking;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getMinCost() {
        return minCost;
    }

    public void setMinCost(Double minCost) {
        this.minCost = minCost;
    }

    public Double getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(Double maxCost) {
        this.maxCost = maxCost;
    }

    public TypeOfProperty getTypeOfProperty() {
        return typeOfProperty;
    }

    public void setTypeOfProperty(TypeOfProperty typeOfProperty) {
        this.typeOfProperty = typeOfProperty;
    }

    public Integer getMinSquare_Meters() {
        return minSquare_Meters;
    }

    public void setMinSquare_Meters(Integer minSquare_Meters) {
        this.minSquare_Meters = minSquare_Meters;
    }

    public Integer getMaxSquare_Meters() {
        return maxSquare_Meters;
    }

    public void setMaxSquare_Meters(Integer maxSquare_Meters) {
        this.maxSquare_Meters = maxSquare_Meters;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public SearchFilter() {
    }

    public SearchFilter(String location, Double minCost, Double maxCost, TypeOfProperty typeOfProperty,
                        Integer minSquare_Meters, Integer maxSquare_Meters, Integer bedrooms, Boolean parking) {
        this.location = location;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.typeOfProperty = typeOfProperty;
        this.minSquare_Meters = getMinSquare_Meters();
        this.maxSquare_Meters = getMaxSquare_Meters();
        this.bedrooms = bedrooms;
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "location='" + location + '\'' +
                ", minCost=" + minCost +
                ", maxCost=" + maxCost +
                ", typeOfProperty=" + typeOfProperty +
                ", minSquare_Meters=" + minSquare_Meters +
                ", maxSquare_Meters=" + maxSquare_Meters +
                ", bedrooms=" + bedrooms +
                ", parking=" + parking +
                '}';
    }
}
