package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*Entity class for user which has unique constraint for each user: username and email*/
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
    })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id of user

    @Column(name = "username")
    @NotBlank
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;//username of user

    @Column(name = "firstname")
    @NotBlank
    @Size(max = 20, message = "Firstname must not exceed 20 characters")
    private String firstname;//firstname of user

    @Column(name = "lastname")
    @NotBlank
    @Size(max = 20, message = "Lastname must not exceed 20 characters ")
    private String lastname;//lastname of user

    @Column(name = "password")
    @NotBlank
    @Size(min = 8,  message = "Password must be between 8 and 50 characters")
    private String password;//password of user

    @Column(name = "email")
    @NotBlank
    @Email
    private String email;//email of user

    @Column(name = "isActive", nullable = false)
    private Boolean isActive = false;//default false value for non confirmed users

    @OneToOne(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Owner owner;//owner linked with user

    @OneToOne(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Renter renter;//renter linked with user

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();//roles of user

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<VerificationRequest> verificationRequests;//verification requests of user

    //Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActive() {return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<VerificationRequest> getVerificationRequests() {
        return verificationRequests;
    }

    public void setVerificationRequests(List<VerificationRequest> verificationRequests) {
        this.verificationRequests = verificationRequests;
    }

    //Constructors and toString
    public User() {
    }

    public User(String username, String firstname, String lastname, String password, String email, Boolean isActive) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
    }

    public User(Integer id, String username, String firstname, String lastname, String password, String email,
                Owner owner, Renter renter, Set<Role> roles, List<VerificationRequest> verificationRequests) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.owner = owner;
        this.renter = renter;
        this.roles = roles;
        this.verificationRequests = verificationRequests;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
