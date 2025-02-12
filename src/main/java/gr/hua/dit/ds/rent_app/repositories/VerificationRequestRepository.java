package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.TypeOfRequest;
import gr.hua.dit.ds.rent_app.entities.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*Repository interface for the verification request entity, provides data access methods for verification request
 objects in the database*/
@Repository
public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Integer> {
    List<VerificationRequest> findByTypeOfRequest(TypeOfRequest type);
}
