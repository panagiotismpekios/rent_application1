package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.entities.Role;
import gr.hua.dit.ds.rent_app.entities.User;
import gr.hua.dit.ds.rent_app.repositories.OwnerRepository;
import gr.hua.dit.ds.rent_app.repositories.RoleRepository;
import gr.hua.dit.ds.rent_app.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*Controller class for authentication requests*/
@Controller
public class AuthController {

    RoleRepository roleRepository;

    UserRepository userRepository;

    OwnerRepository ownerRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public AuthController(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                          OwnerRepository ownerRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ownerRepository = ownerRepository;
    }

    /*Sets up default roles and the admin user*/
    @PostConstruct
    public void setup() {
        /*Default roles*/
        Role role_owner = new Role("ROLE_OWNER");
        Role role_renter = new Role("ROLE_RENTER");
        Role role_admin = new Role("ROLE_ADMIN");

        /*Inserts or updates roles in database*/
        roleRepository.updateOrInsert(role_owner);
        roleRepository.updateOrInsert(role_renter);
        roleRepository.updateOrInsert(role_admin);

        /*Checks if admin user exists; if not, it creates one user and one owner*/
        if (userRepository.findByUsername("Admin").isEmpty()) {
            User admin = new User("Admin", "admin", "admin", "admin123", "admin@email.gr", true );
            Owner Admin = new Owner();
            Admin.setUser(admin);
            ownerRepository.save(Admin);
            /*Creates encoded password for admin*/
            String encodedPassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(encodedPassword);
            /*Assigns admin role to user*/
            Role roleAdmin1 = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
            Role roleOwner = roleRepository.findByName("ROLE_OWNER").orElseThrow();
            admin.getRoles().add(roleAdmin1);
            admin.getRoles().add(roleOwner);
            /*Saves the user to database*/
            userRepository.save(admin);
        }
    }

    /*Handles requests to the login page and returns the login page*/
    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

}
