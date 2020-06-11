package swa.poseidon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
    @Autowired
    private UserSecurityDetails userSecurityDetails;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http
	    	// Page for user profile registration available to all
		    .authorizeRequests().antMatchers("/users/add").permitAll()
		    // Login page available to all
		    .and().authorizeRequests().antMatchers("/login").permitAll()
        	// All other pages requires authentication
		    .and().authorizeRequests().anyRequest().authenticated()
		    // Use default login controller
            .and().formLogin()
            // Secure login with a non standard processing URL
            .loginProcessingUrl("/processPoseidonLogin")
            // Use custom home page
            .defaultSuccessUrl("/home.html")
            // Use custom failure page
            .failureUrl("/accessDenied")
            // Use default logout controller
            .and().logout()
            // Use custom user details management
        	.and().userDetailsService(userSecurityDetails);
    }
}
