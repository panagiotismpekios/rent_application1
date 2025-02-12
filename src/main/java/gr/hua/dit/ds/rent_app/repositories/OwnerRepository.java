package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.entities.Renter;
import gr.hua.dit.ds.rent_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*Repository interface for the owner entity, provides data access methods for owner objects in the database*/
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Optional<Owner> findByUser(User user);
}
