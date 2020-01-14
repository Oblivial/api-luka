package de.luka.api.infotags;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.luka.api.infoarchive.Information;

public interface TagRepository extends CrudRepository<Tag, Long> {

    @Query(value = "SELECT * FROM tag t where t.name = ?1", 
            nativeQuery=true
        )
    public Optional<Tag> findTagLike(String tag);
    
}
