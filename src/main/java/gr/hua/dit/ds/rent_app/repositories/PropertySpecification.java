package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.Property;
import gr.hua.dit.ds.rent_app.entities.Property_profile;
import gr.hua.dit.ds.rent_app.entities.SearchFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


// This class defines a specification for filtering Property entities based on search criteria.
public class PropertySpecification implements Specification<Property> {

    private final SearchFilter filter;

    public PropertySpecification(SearchFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>(); // List to store filtering conditions

        Join<Property, Property_profile> profileJoin = root.join("property_profile");

        // Apply filters based on SearchFilter criteria
        if (filter.getLocation() != null) {
            predicates.add(criteriaBuilder.equal(profileJoin.get("location"), filter.getLocation()));
        }
        if (filter.getMinCost() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(profileJoin.get("cost"), filter.getMinCost()));
        }
        if (filter.getMaxCost() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(profileJoin.get("cost"), filter.getMaxCost()));
        }
        if (filter.getTypeOfProperty() != null) {
            predicates.add(criteriaBuilder.equal(root.get("typeOfProperty"), filter.getTypeOfProperty()));
        }
        if (filter.getMinSquare_Meters() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(profileJoin.get("square_meters"), filter.getMinSquare_Meters()));
        }
        if (filter.getMaxSquare_Meters() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(profileJoin.get("square_meters"), filter.getMaxSquare_Meters()));
        }
        if (filter.getBedrooms() != null) {
            predicates.add(criteriaBuilder.equal(profileJoin.get("bedrooms"), filter.getBedrooms()));
        }
        if (filter.getParking() != null) {
            predicates.add(criteriaBuilder.equal(profileJoin.get("parking"), filter.getParking()));
        }

        // Combines all predicates with AND operator and returns the final condition
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

