package de.luka.api.infotags;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {

    @Query(value = "SELECT * FROM tag t where t.name = ?1", 
            nativeQuery=true
        )
    public Optional<Tag> findTagLike(String tag);
    
    @Query(value = "SELECT * FROM tag t where t.name LIKE %?1%", 
            nativeQuery=true
     )  
    public Iterable<Tag> findTagsLike(String tag);
    
    @Query(value = "SELECT tag_id FROM tag t where t.name LIKE %?1%", 
            nativeQuery=true
     )  
    public List<Long> findTagIdsLike(String tag);
    
    @Query(value = "SELECT * FROM tag t where t.name IN (?1)", 
            nativeQuery=true
        )
    public List<Long> getTagIds(List<String> tags);
    
    @Query(value = "SELECT tag_id FROM\r\n"+ 
    		" (SELECT tag_id, COUNT(tag_id) AS popularity\r\n" + 
    		"FROM information_tag\r\n" + 
    		"GROUP BY tag_id\r\n" + 
    		"ORDER BY popularity DESC limit 10) As T",
    		nativeQuery=true
    	)
    public List<Long> getPopularTagIDs();
}
