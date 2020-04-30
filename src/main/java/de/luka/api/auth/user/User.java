package de.luka.api.auth.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import de.luka.api.auth.role.Role;
import de.luka.api.auth.user.listconverter.StringListConverter;
import de.luka.api.infoarchive.Information;

@Entity
public class User {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String username;
    private String password;

	@ManyToMany
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;
	
	@Convert(converter = StringListConverter.class)
	private List<String> rolesAsString = new ArrayList<String>();
	
	@OneToMany(mappedBy = "creator")
	private List<Information> informationList;
	
	@Transient
	BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	protected String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = "{bcrypt}" + pwEncoder.encode(password);
	}

	public List<String> getRolesAsString() {
		return rolesAsString;
	}

	public List<String> getAuth() {
		return rolesAsString;
	}

	public void setRoles(Collection<Role> roles) {
		for(Role r : roles) {
			rolesAsString.add(r.getName());
		}
		this.roles = roles;
	}

}