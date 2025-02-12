package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.*;
import gr.hua.dit.ds.rent_app.repositories.PropertyRepository;
import gr.hua.dit.ds.rent_app.repositories.RentApplicationRepository;
import gr.hua.dit.ds.rent_app.repositories.RenterRepository;
import gr.hua.dit.ds.rent_app.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing Rent application-related operations*/
@Service
public class RentApplicationService {

    public RentApplicationRepository rentApplicationRepository;

    public PropertyRepository propertyRepository;

    public RenterRepository renterRepository;

    public RoleRepository roleRepository;

    public RentApplicationService(RentApplicationRepository rentApplicationRepository,
                                  PropertyRepository propertyRepository, RenterRepository renterRepository,
                                  RoleRepository roleRepository) {
        this.rentApplicationRepository = rentApplicationRepository;
        this.propertyRepository = propertyRepository;
        this.renterRepository = renterRepository;
        this.roleRepository = roleRepository;
    }

    //Retrieves a list of rent applications and returns it
    @Transactional
    public List<RentApplication> getRentApplications(){ return rentApplicationRepository.findAll();}
    
    //Retrieves the list of properties of a specific owner based on his id
    public List<RentApplication> getRentApplicationsByOwnerId(Integer ownerId) {
        return rentApplicationRepository.findByOwnerId(ownerId);
    }


    //Retrieves a specific rent application and returns it
    @Transactional
    public RentApplication getRentApplication(Integer rentApplicationId){
        return rentApplicationRepository.findById(rentApplicationId).get();}

    //Creates a rent application and saves it
    @Transactional
    public RentApplication createApplication(Integer propertyId, Integer renterId){
        //Gets the property
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        //Gets the renter
        Renter renter = renterRepository.findById(renterId)
                .orElseThrow(() -> new RuntimeException("Renter not found"));

        RentApplication application =new RentApplication();
        application.setProperty(property);
        application.setRenters(renter);
        application.setStatus(Status.Pending);

        return rentApplicationRepository.save(application);
    }

    //Approves a rent application
    @Transactional
    public void approveRentApplicationRequest(Integer applicationId) {
        //checks if verification request with this id exist; if not throws error
        RentApplication application = rentApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Error: Rent application not found."));
        //checks if request's status is pending; if not throws error
        if (application.getStatus() != Status.Pending) {
            throw new RuntimeException("Error: Only pending requests can be approved.");
        }
        application.setStatus(Status.Approved);
        rentApplicationRepository.save(application);

        Property property = application.getProperty();
        property.setRenter(application.getRenter());
        propertyRepository.save(property);
    }

    ///Rejects a rent application
    @Transactional
    public void rejectRentApplicationRequest(Integer applicationId){
        RentApplication application = rentApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Error: Rent application not found."));
        if (application.getStatus() != Status.Pending) {
            throw new RuntimeException("Error: Only pending requests can be approved.");
        }
        application.setStatus(Status.Rejected);
        rentApplicationRepository.save(application);
    }

    //Saves a rent application
    @Transactional
    public void saveRentApplication(RentApplication application) {
        rentApplicationRepository.save(application);
    }

    //Deletes a rent application
    @Transactional
    public void deleteApplication(RentApplication rentApplication){
        rentApplicationRepository.delete(rentApplication);
    }
}
