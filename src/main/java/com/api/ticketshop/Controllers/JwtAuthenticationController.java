package com.api.ticketshop.Controllers;

import com.api.ticketshop.Config.JwtTokenUtil;
import com.api.ticketshop.Models.JwtRequest;
import com.api.ticketshop.Models.JwtResponse;
import com.api.ticketshop.Models.UserModel;
import com.api.ticketshop.Services.JwtUserDetailsService;
import com.api.ticketshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class that contains all endpoints of an JwtAuthenticationController
 */
@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private UserService userService;

    /**
     * Class constructor that receives the Service Interface.
     * This constructor is actually a Dependency Injection Point.
     * @param userService
     */
    public JwtAuthenticationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method that performs user authentication.
     * @param authRequest
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/v1/auth",  method = RequestMethod.POST)
    public ResponseEntity<?> auth(@RequestBody JwtRequest authRequest) throws Exception {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            UserModel user = userService.getUserByEmail(authRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails, user.getId(), user.getType());
            return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * Method that performs user login and logoff.
     * @param username, password
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    }

}