package com.api.ticketshop.Services;

import com.api.ticketshop.Models.UserModel;
import com.api.ticketshop.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * Class that contains all the methods to make each endpoint of a JwrUserDetails
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Class constructor that receives the Repository Interface.
     * This constructor is actually a Dependency Injection Point.
     */
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method to load user by username
     * @param username
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel user = userRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found with this email");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());

    }
}
