package gr.hua.dit.ds.rent_app.repositories;


import gr.hua.dit.ds.rent_app.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*Repository interface for the role entity, provides data access methods for role objects in the database*/
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String Name);//finds the role by his name

    //updates or inserts a role
    default Role updateOrInsert(Role role) {
        Role existing_role = findByName(role.getName()).orElse(null);
        if (existing_role != null) {
            return existing_role;
        }
        else {
            return save(role);
        }
    }

}
