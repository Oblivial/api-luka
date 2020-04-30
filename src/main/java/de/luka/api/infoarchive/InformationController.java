package de.luka.api.infoarchive;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.luka.api.auth.role.RoleManagement;
import de.luka.api.auth.user.User;
import de.luka.api.auth.user.UserRepository;
import de.luka.api.infotags.Tag;
import de.luka.api.infotags.TagRepository;


@RestController	// This means that this class is a Controller
@RequestMapping(path="/information")
public class InformationController {
	
	@Autowired 
	private InformationRepository informationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private TagRepository tagRepository;
	
	
	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody Information addNewInformation (@RequestParam String name, 
			@RequestParam String description, 
			@RequestParam String url, 
			@RequestParam List<String> tags) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Information n = new Information();
		n.setDescription(description);
		n.setName(name);
		Optional<User> user = userRepository.userByName(RoleManagement.getAuthUsername());
		if(user.isPresent()) {
			n.setCreator(user.get());
		}else {
			throw new RuntimeException("Creator could not be set, User: " + RoleManagement.getAuthUsername() + " not found");
		}
		n.setValidated(RoleManagement.userHasRole("OWNER"));
		n.setLastVisited(Instant.now());
		try {
			n.setUrl(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<Tag> taglist = new HashSet<Tag>();
		for(String tag : tags) {
			Optional<Tag> t = tagRepository.findTagLike(tag);
			if(!t.isPresent()) {
				Tag newTag = new Tag();
				newTag.setName(tag);
				Set<Information> informationSet = new HashSet<>();
				informationSet.add(n);
				newTag.setRelatedInformation(informationSet);
				//newTag.setInformation(n);
				tagRepository.save(newTag);
				taglist.add(newTag);
			}else {
				Tag existingTag = t.get();
				existingTag.addRelatedInformation(n);
				tagRepository.save(existingTag);
				taglist.add(existingTag);
			}
			
		}
		n.setTags(taglist);
		informationRepository.save(n);
		return n;
	}
	
	@PostMapping(path="/add/object")
	public @ResponseBody String addNewInformation (@RequestBody Information info) {
		info.setValidated(RoleManagement.userHasRole("OWNER"));
		informationRepository.save(info);
		return "Saved " + info.getName();
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteInformation(@PathVariable("id") long id) {
		Optional<Information> info = informationRepository.findById(id);
		if(info.isPresent()) {
			informationRepository.delete(info.get());
			return "Deleted " + id;
		}else {
			throw new RuntimeException("Information not found + " + id);
		}
	}
	
	@PutMapping(path="/update")
	public @ResponseBody String updateInformation(@RequestBody Information info) {
		informationRepository.save(info);
		return "Saved " + info.getName();
	}
	
	@PutMapping(path="/update/url/{id}")
	public @ResponseBody String updateInformationURL(@PathVariable("id") long id, @RequestParam String newURLValue) {
		Optional<Information> info = informationRepository.findById(id);
		if(info.isPresent()) {
			try {
				info.get().setUrl(new URL(newURLValue));
				return "Saved " + newURLValue;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("unable to parse URL + " + id);
			}
		}else {
			throw new RuntimeException("Information not found + " + id);
		}
	}
	

	
	@GetMapping(path="/entrycount")
	public @ResponseBody long getCount() {
		return informationRepository.count();
	}

	
	@GetMapping(path="/{id}")
	public @ResponseBody Information getInfo(@PathVariable("id") long id) {
		// This returns a JSON or XML with the 
		Optional<Information> info = informationRepository.findById(id);
		if(info.isPresent()) {
			return info.get();
		}else {
			throw new RuntimeException("Information not found + " + id);
		}
	}
	
	@GetMapping(path="/bytag/{tag}")
	public @ResponseBody List<Information> getInfoByTag(@PathVariable("tag") String tag) {
		// This returns a JSON or XML with the s
		List<Information> info = informationRepository.findByTagsIdsLike(tagRepository.findTagIdsLike(tag));
		if(!info.isEmpty()) {
			return info;
		}else {
			throw new RuntimeException("Information not found");
		}
	}
	
	@GetMapping(path="/bytag")
	public @ResponseBody List<Information> getInfoByTagRequestParam(@RequestParam String tag) {
		// This returns a JSON or XML with the 
		List<Information> info = informationRepository.findByTagsIdsLike(tagRepository.findTagIdsLike(tag));
		if(!info.isEmpty()) {
			return info;
		}else {
			throw new RuntimeException("Information not found");
		}
	}
	
	@GetMapping(path="/next")
	public @ResponseBody Iterable<Information> getNextInfo(@RequestParam Integer currentIndex, @RequestParam Integer entryCount, @RequestParam Optional<List<String>> tags) {
		if(tags.isPresent()) {
			List<Long> tagids = tagRepository.getTagIds(tags.get());
			if(tagids != null && tagids.size() > 0) {
				return informationRepository.allFromIndexToTags(currentIndex, entryCount, tagids);
			}else {
				throw new RuntimeException("No Information found that matches the Tag");
			}
		}else {
			return informationRepository.allFromIndexTo(currentIndex, entryCount);
		}	
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Information> getAllInfo() {
		return informationRepository.findAll();
	}
}
