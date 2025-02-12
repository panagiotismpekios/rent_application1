package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.Property;
import gr.hua.dit.ds.rent_app.entities.Renter;
import gr.hua.dit.ds.rent_app.entities.User;
import gr.hua.dit.ds.rent_app.repositories.RenterRepository;
import gr.hua.dit.ds.rent_app.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing renter-related operations*/
@Service
public class RenterService {
    private RenterRepository renterRepository;

    private UserRepository userRepository;

    public RenterService(RenterRepository renterRepository, UserRepository userRepository) {
        this.renterRepository = renterRepository;
        this.userRepository = userRepository;
    }

    //Retrieves a list of all renters and returns it
    @Transactional
    public List<Renter> getRenters(){ return renterRepository.findAll();}

    //Retrieves a specific renter and returns it
    @Transactional
    public Renter getRenter(Integer renterId){ return renterRepository.findById(renterId).get();}


    public Renter getRenterByUsername(String username) {
        //Gets the user by its email
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        //Gets the renter by user
        return renterRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Renter not found for user with email: " + username));
    }
}
