package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.*;
import gr.hua.dit.ds.rent_app.repositories.PropertyRepository;
import gr.hua.dit.ds.rent_app.repositories.PropertySpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing property-related operations*/
@Service
public class PropertyService {

    private PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository){
        this.propertyRepository = propertyRepository;
    }

    //Retrieves a list of all properties and returns the list
    @Transactional
    public List<Property> getAvailablePropertiesForRenters() {
        return propertyRepository.findByRenterIsNull();
    }

    //Retrieves list of properties that apply to search filter
    @Transactional
    public List<Property> searchProperties(SearchFilter filter) {
        Specification<Property> spec = new PropertySpecification(filter);
        return propertyRepository.findAll(spec);
    }


    //Retrieves a specific property and returns the property
    @Transactional
    public Property getProperty(Integer propertyId){ return propertyRepository.findById(propertyId).get();}

    //Retrieves a specific property and returns the details of it
    @Transactional
    public Property_profile getPropertyDetails(Integer propertyId){
        return propertyRepository.findById(propertyId).get().getProperty_profile();
    }

    //Saves a new property
    @Transactional
    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }
}
