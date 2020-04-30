package de.luka.api.auth.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.luka.api.auth.role.RoleManagement;
import de.luka.api.auth.role.RoleRepository;

@RestController	// This means that this class is a Controller
@RequestMapping(path="/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;


	@PostMapping(path="/signup")
	public @ResponseBody String addNewUser (@RequestParam String userName, @RequestParam String password) {
		User newUser = new User();
		newUser.setPassword(password);
		newUser.setUsername(userName);
		newUser.setRoles(roleRepo.findByName("ROLE_USER"));
		userRepo.save(newUser);

		return "Saved " + userName;
	}
	
	@PutMapping(path="/update/{username}/password")
	public @ResponseBody String modifyPassword(@PathVariable("username") String userName, @RequestParam String newPassword) {
		if(RoleManagement.getAuthUsername().equals(userName)) {
			Optional<User> user = userRepo.userByName(userName);
			if(user.isPresent()) {
				User userObject = user.get();
				userObject.setPassword(newPassword);
				userRepo.save(userObject);
				return "Set new password for: " + userName;
			}else {
				throw new RuntimeException("User does not exist");
			}
		}else {
			throw new RuntimeException("Cannot change password of other users");
		}
		
	}
	
	@GetMapping(path="/get/{username}")
	public @ResponseBody User getUser(@PathVariable("username") String userName) {
		if(RoleManagement.getAuthUsername().equals(userName) || RoleManagement.userHasRole("OWNER")) {
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
	
	@GetMapping(path="/nameinuse")
	public @ResponseBody boolean isNameInUse(@RequestParam String userName) {
		Optional<User> user = userRepo.userByName(userName);
		return user.isPresent();
	}
	
}
