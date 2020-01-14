package de.luka.api.infoarchive;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import de.luka.api.infotags.Tag;
import de.luka.api.infotags.TagRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController	// This means that this class is a Controller
@RequestMapping(path="/information")
public class InformationController {
	
	@Autowired 
	private InformationRepository informationRepository;
	
	@Autowired 
	private TagRepository tagRepository;
	
	private UsernamePasswordAuthenticationToken accessToken;
	
	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewInformation (@RequestParam String name, 
			@RequestParam String description, 
			@RequestParam String url, 
			@RequestParam List<String> tags) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Information n = new Information();
		n.setDescription(description);
		n.setName(name);
		n.setValidated(RoleManagement.userHasRole("OWNER"));
		try {
			n.setUrl(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<Tag> taglist = new HashSet<Tag>();
		for(String tag : tags) {
			Optional<Tag> t = tagRepository.findTagLike(tag);
			System.out.println("present?: " + t.isPresent());
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
		return "Saved " + name;
	}
	
	@PostMapping(path="/add/object")
	public @ResponseBody String addNewInformation (@RequestBody Information info) {
		informationRepository.save(info);
		return "Saved " + info.getid();
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
		// This returns a JSON or XML with the 
		List<Information> info = informationRepository.findByTagsLike(tag.toString());
		if(!info.isEmpty()) {
			return info;
		}else {
			throw new RuntimeException("Information not found");
		}
	}
	
	@GetMapping(path="/bytag")
	public @ResponseBody List<Information> getInfoByTagRequestParam(@RequestParam String tag) {
		// This returns a JSON or XML with the 
		List<Information> info = informationRepository.findByTagsLike(tag.toString());
		System.out.println("info size:" + info.size());
		if(!info.isEmpty()) {
			return info;
		}else {
			throw new RuntimeException("Information not found");
		}
	}
	
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Information> getAllInfo() {
		// This returns a JSON or XML with the 
		return informationRepository.findAll();
	}
}
