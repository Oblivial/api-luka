package de.luka.api.auth.user;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long>{
    @Query(value = "SELECT * FROM api_luka.user u where u.username = ?1", 
            nativeQuery=true
        )
    public Optional<User> userByName(String name);
}
