package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.*;
import gr.hua.dit.ds.rent_app.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing verification request-related operations*/
@Service
public class VerificationRequestService {

    private VerificationRequestRepository verificationRequestRepository;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private OwnerRepository ownerRepository;

    private RenterRepository renterRepository;

    private PropertyRepository propertyRepository;

    public VerificationRequestService(VerificationRequestRepository verificationRequestRepository,
                                      UserRepository userRepository, RoleRepository roleRepository,
                                      OwnerRepository ownerRepository, RenterRepository renterRepository,
                                      PropertyRepository propertyRepository) {
        this.verificationRequestRepository = verificationRequestRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.ownerRepository = ownerRepository;
        this.renterRepository = renterRepository;
        this.propertyRepository = propertyRepository;
    }

    //Retrieves a list of all verification requests and returns it
    @Transactional
    public List<VerificationRequest> getVerificationRequests(){ return verificationRequestRepository.findAll();}

    //Retrieves a specific verification request and returns it
    @Transactional
    public VerificationRequest getVerificationRequest(Integer verificationRequestId){
        return verificationRequestRepository.findById(verificationRequestId).get();}

    //Submits a new verification request
    @Transactional
    public void submitVerificationRequest(Integer userId, TypeOfRequest typeOfRequest) {
        //checks if user with this id exists; if not throws error
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        VerificationRequest request = new VerificationRequest();
        request.setUser(user);//sets the user
        request.setStatus(Status.Pending);//sets the status to pending
        request.setTypeOfRequest(typeOfRequest);//sets the type of request

        verificationRequestRepository.save(request);//saves it
    }

    //Saves the new Verification Request status
    @Transactional
    public void saveVerificationRequest(VerificationRequest request) {
        verificationRequestRepository.save(request);
    }

    //Approves a verification request
    @Transactional
    public void approveVerificationRequestOwner(Integer requestId) {
        //checks if verification request with this id exist; if not throws error
        VerificationRequest request = verificationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Error: Verification request not found."));
        //checks if request's status is pending; if not throws error
        if (request.getStatus() != Status.Pending) {
            throw new RuntimeException("Error: Only pending requests can be approved.");
        }

        User user = request.getUser();
        Role role_owner = roleRepository.findByName("ROLE_OWNER")
                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_OWNER not found."));
        user.getRoles().add(role_owner);//adds role owner to user
        userRepository.save(user);//saves the user
        request.setStatus(Status.Approved);//sets the status to approved
        verificationRequestRepository.save(request);//saves the request

        //Creates the owner entity
        Owner owner = new Owner();
        owner.setUser(user); // Sets user entity of owner
        ownerRepository.save(owner); // Saves the owner
        System.out.println("Owner created successfully with ID: " + owner.getId());

    }

    @Transactional
    public void approveVerificationRequestUser(Integer requestId) {
        // checks if verification request with this id exist; if not throws error
        VerificationRequest request = verificationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Error: Verification request not found."));

        //checks if request's status is pending; if not throws error
        if (request.getStatus() != Status.Pending) {
            throw new RuntimeException("Error: Only pending requests can be approved.");
        }

        User user = request.getUser();
        Role roleRenter = roleRepository.findByName("ROLE_RENTER")
                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_RENTER not found."));

        user.getRoles().add(roleRenter);
        user.setActive(true);

        request.setStatus(Status.Approved);
        verificationRequestRepository.save(request);

        //creates renter entity
        Renter renter = new Renter();
        renter.setUser(user);
        renterRepository.save(renter);

        System.out.println("User approved successfully with ID: " + user.getId());
    }

    @Transactional
    public void approveVerificationRequestProperty(Integer requestId){
        //Gets request by id
        VerificationRequest request = verificationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Error: Verification request not found."));

        //Checks if status is not pending
        if (request.getStatus() != Status.Pending) {
            throw new RuntimeException("Error: Only pending requests can be approved.");
        }

        //Gets user and owner of request
        User user = request.getUser();
        Owner owner = user.getOwner();

        //Sets approved status
        request.setStatus(Status.Approved);
        verificationRequestRepository.save(request);

        //Creates the property
        Property property = new Property();
        property.setOwner(owner);
        property.setProperty_profile(request.getPropertyProfile());
        property.setTypeOfProperty(request.getTypeOfProperty());

        propertyRepository.save(property);

        System.out.println("Property approved successfully with ID: " + property.getId());
    }


    //Rejects verification request
    @Transactional
    public void rejectVerificationRequest(Integer requestId) {
        //checks if request with this id exists; if not throws error
        VerificationRequest request = verificationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Error: Verification request not found."));
        //checks if request's status is pending; if not throws error
        if (request.getStatus() != Status.Pending) {
            throw new RuntimeException("Error: Only pending requests can be rejected.");
        }

        request.setStatus(Status.Rejected);//sets status to rejected
        verificationRequestRepository.save(request);//saves the request
    }

    //Deletes a request
    @Transactional
    public void deleteRequest(VerificationRequest verificationRequest){
        verificationRequestRepository.delete(verificationRequest);
    }
}
