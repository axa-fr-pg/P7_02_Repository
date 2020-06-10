package swa.poseidon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import swa.poseidon.model.User;
import swa.poseidon.repositories.UserRepository;

import java.util.Arrays;

@Service
public class UserSecurityDetails implements UserDetailsService 
{
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
        		user.getUsername(), user.getPassword(), Arrays.asList(authority));
        return userDetails;
    }
    
    
	@Bean // This method is used during login to check if the password is correct
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
