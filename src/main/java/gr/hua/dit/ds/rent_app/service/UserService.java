package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.Role;
import gr.hua.dit.ds.rent_app.entities.User;
import gr.hua.dit.ds.rent_app.repositories.RoleRepository;
import gr.hua.dit.ds.rent_app.repositories.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*Service class for managing user-related operations*/
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Saves a new user, encodes its password and sets the default role of renter to him
    @Transactional
    public Integer saveUser(User user) {
        //Encodes password
        String passwd = user.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        user.setPassword(encodedPassword);

        // Gives the role Renter
        Role roleRenter = roleRepository.findByName("ROLE_RENTER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRenter);
        user.setRoles(roles);

        // Saves user to database
        user = userRepository.save(user);
        System.out.println("User saved with ID: " + user.getId());
        return user.getId();
    }

    //Loads a user by his username and returns the user's details
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);
        //checks if user with this username exists; if not it throws error
        if (opt.isEmpty()){
            throw new UsernameNotFoundException("User with username: " + username + " not found !");
         }
            User user = opt.get();

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + username + " has no roles assigned.");
        }

        if (Boolean.FALSE.equals(user.isActive())) {
            throw new DisabledException("User with username: " + username + " is not activated yet!");
        }

        System.out.println("User found: " + user);
        System.out.println("Roles: " + user.getRoles());
        System.out.println("Is Active: " + user.isActive());

        return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role.toString()))
                            .collect(Collectors.toSet())
            );
    }
}
