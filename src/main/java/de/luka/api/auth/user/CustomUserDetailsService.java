package de.luka.api.auth.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
    @Autowired
    private UserRepository userRepository;
    
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.userByName(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserPrincipal(user.get());
    }

}
