package de.luka.api.auth.role;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.luka.api.auth.privilege.Privilege;


public interface RoleRepository extends JpaRepository<Role, Long>{
    @Query(value = "SELECT * FROM api_luka.role r where r.name = ?1", 
            nativeQuery=true
        )
    public Collection<Role> findByName(String name);
}
