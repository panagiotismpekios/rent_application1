package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.RentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*Repository interface for the rent application entity, provides data access methods for rent application objects in the database*/
@Repository
public interface RentApplicationRepository extends JpaRepository<RentApplication, Integer> {
        @Query("SELECT ra FROM RentApplication ra WHERE ra.property.owner.id = :ownerId")
        List<RentApplication> findByOwnerId(@Param("ownerId") Integer ownerId);
}
