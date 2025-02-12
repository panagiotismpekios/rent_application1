package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.entities.Property;
import gr.hua.dit.ds.rent_app.entities.User;
import gr.hua.dit.ds.rent_app.repositories.OwnerRepository;
import gr.hua.dit.ds.rent_app.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing owner-related operations*/
@Service
public class OwnerService {

    private OwnerRepository ownerRepository;
    private UserRepository userRepository;

    public OwnerService(OwnerRepository ownerRepository, UserRepository userRepository){
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
    }

    //Retrieves a list of all owners and returns it
    @Transactional
    public List<Owner> getOwners(){ return ownerRepository.findAll();}

    //Retrieves a specific owner by its id and returns it
    @Transactional
    public Owner getOwner(Integer ownerId){ return ownerRepository.findById(ownerId).get();}

    //Retrieves an owner by his username and returns the owner if exists
    @Transactional
    public Owner getOwnerByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        return ownerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Owner not found for user with email: " + username));
    }
}
