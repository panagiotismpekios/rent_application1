package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/*Repository interface for the property entity, provides data access methods for property objects in the database*/
@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> , JpaSpecificationExecutor<Property> {
    List<Property> findByRenterIsNull();
}
