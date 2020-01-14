package de.luka.api.auth.privilege;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{
    @Query(value = "SELECT * FROM privilege p where p.name = ?1", 
            nativeQuery=true
        )
    public Collection<Privilege> findByName(String name);
}
