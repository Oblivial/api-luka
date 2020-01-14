package de.luka.api.infoarchive;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.luka.api.infoarchive.Information;
import de.luka.api.infotags.Tag;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface InformationRepository extends JpaRepository<Information, Long> {
	/* All By Tags */
    @Query(value = "SELECT * FROM information i where i.tags LIKE %?1%", 
            nativeQuery=true
        )
    public List<Information> findByTagsLike(String tag);
    
    
    /* Tags By Count */
    
    
}