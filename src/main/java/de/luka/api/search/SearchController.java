package de.luka.api.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.luka.api.infoarchive.Information;
import de.luka.api.infoarchive.InformationRepository;
import de.luka.api.infotags.Tag;
import de.luka.api.infotags.TagRepository;

@RestController	
@RequestMapping(path="/search")
public class SearchController {
	
	@Autowired 
	private InformationRepository informationRepository;
	
	@Autowired 
	private TagRepository tagRepository;
	
	
	@GetMapping(path="/{keyword}")
	public @ResponseBody List<Search<?>> searchKeyword(@PathVariable("keyword") String keyword){
		List<Search<?>> matches = new ArrayList<Search<?>>();
		
		Search<Information> infoMatchesDescription = new Search<Information>(Information.class, "description");
		infoMatchesDescription.setMatches(informationRepository.findByDescriptionLike(keyword));
		matches.add(infoMatchesDescription);
		
		Search<Information> infoMatchesTags = new Search<Information>(Information.class, "tag");
		List<Long> tagIds = tagRepository.findTagIdsLike(keyword);
		if(!tagIds.isEmpty() && tagIds.size() > 1) {
			infoMatchesTags.setMatches(informationRepository.findByTagsIdsLike(tagRepository.findTagIdsLike(keyword)));
		}else if(!tagIds.isEmpty()){
			infoMatchesTags.setMatches(informationRepository.findByTagIdLike(tagRepository.findTagIdsLike(keyword).get(0)));
		}
		matches.add(infoMatchesTags);
		
		Search<Information> infoMatchesName = new Search<Information>(Information.class, "name");
		infoMatchesName.setMatches(informationRepository.findByNameLike(keyword));
		matches.add(infoMatchesName);
		
		Search<Tag> tagMatches = new Search<Tag>(Tag.class, "tag");
		tagMatches.setMatches(tagRepository.findTagsLike(keyword));
		matches.add(tagMatches);
		
		return matches;
	}
	

}
