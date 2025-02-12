package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*Repository interface for the user entity, provides data access methods for user objects in the database*/
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByIsActive(Boolean isActive);
}
