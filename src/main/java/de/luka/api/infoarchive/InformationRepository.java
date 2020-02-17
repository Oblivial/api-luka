package de.luka.api.infoarchive;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.luka.api.infoarchive.Information;


public interface InformationRepository extends JpaRepository<Information, Long> {
	
	/* All By Tags */
    @Query(value = "SELECT * FROM information i where information_id IN (SELECT information_id FROM api_luka.information_tag where tag_id IN ?1)", 
            nativeQuery=true
        )
    public List<Information> findByTagsIdsLike(List<Long> tagids);
    
    @Query(value = "SELECT * FROM information i where information_id IN (SELECT information_id FROM api_luka.information_tag where tag_id LIKE %?1%)", 
            nativeQuery=true
        )
    public List<Information> findByTagIdLike(Long tagid);
    
    @Query(value = "SELECT * FROM information i where i.name LIKE %?1%", 
            nativeQuery=true
        )
    public Iterable<Information> findByNameLike(String name);
    
    @Query(value = "SELECT * FROM information i where i.description LIKE %?1%", 
            nativeQuery=true
        )
    public Iterable<Information> findByDescriptionLike(String description);
    
    @Query(value = "SELECT * FROM information i where information_id IN (\n" + 
    		"	SELECT information_id FROM (\n" + 
    		"		SELECT information_id, Pager FROM (\n" + 
    		"			SELECT information_id, ROW_NUMBER() OVER () AS Pager FROM information i\n" + 
    		"            ORDER BY Pager) AS T1 WHERE Pager > ?1 AND Pager < ?2 ) AS T2)", 
            nativeQuery=true
        )
    public Iterable<Information> allFromIndexTo(Integer currentIndex, Integer entryCount);
    
    @Query(value = "SELECT * FROM information i where information_id IN (\n" + 
    		"    SELECT information_id FROM (\n" + 
    		"		SELECT information_id, Pager FROM (\n" + 
    		"			SELECT information_id, ROW_NUMBER() OVER () AS Pager\n" + 
    		"				FROM information i where information_id IN (\n" + 
    		"					SELECT information_id FROM information_tag where tag_id IN (?3)\n" + 
    		"				)\n" + 
    		"ORDER BY Pager) AS T WHERE Pager > ?1 AND Pager < ?2) AS T2)", 
            nativeQuery=true
        )
    public Iterable<Information> allFromIndexToTags(Integer currentIndex, Integer entryCount, List<Long> tagids);
    
    
    
}