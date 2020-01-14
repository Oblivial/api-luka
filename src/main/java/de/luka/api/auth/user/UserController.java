package de.luka.api.auth.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.luka.api.auth.privilege.PrivilegeRepository;
import de.luka.api.auth.role.RoleManagement;
import de.luka.api.auth.role.RoleRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController	// This means that this class is a Controller
@RequestMapping(path="/user")
public class UserController {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PrivilegeRepository privilegeRepo;

	@PostMapping(path="/signup")
	public @ResponseBody String addNewUser (@RequestParam String userName, @RequestParam String password) {
		System.out.println("got request");
		System.out.println((userRepo == null) || (roleRepo == null) || (privilegeRepo == null));
		User newUser = new User();
		newUser.setPassword(password);
		newUser.setUsername(userName);
		newUser.setRoles(roleRepo.findByName("ROLE_USER"));
		userRepo.save(newUser);

		return "Saved " + userName;
	}

	@GetMapping(path="/get/{username}")
	public @ResponseBody User getUser(@PathVariable("username") String userName) {
		if(RoleManagement.getAuthUsername()==userName || RoleManagement.userHasRole("OWNER")) {
			Optional<User> user = userRepo.userByName(userName);
			if(user.isPresent()){
				return user.get();
			}else {
				throw new RuntimeException("User does not exist");
			}
		}else {
			throw new RuntimeException("User does not have access to profile");
		}
	}
	/*
	@GetMapping(path="/nameinuse")
	public @ResponseBody boolean 
	*/
}
