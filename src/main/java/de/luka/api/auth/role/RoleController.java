package de.luka.api.auth.role;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.luka.api.auth.user.User;

@RestController
@RequestMapping(path="/role")
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepository;

	@PutMapping(path="/{role}/adduser")
	public String updateUserRoles(@PathVariable Long roleId, @RequestParam User user) {
		if(RoleManagement.userHasRole("OWNER")) {
			Role r = roleRepository.getOne(roleId);
			r.addUser(user);
			return "added " + user.getUsername() + " to role " + r.getName();
		}else {
			throw new RuntimeException("No Access!");
		}
	}
}
