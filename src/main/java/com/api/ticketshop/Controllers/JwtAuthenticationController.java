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

@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private UserService userService;

    public JwtAuthenticationController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/v1/auth",  method = RequestMethod.POST)
    public ResponseEntity<?> auth(@RequestBody JwtRequest authRequest) throws Exception {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            UserModel user = userService.getUserByEmail(authRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails, user.getId(), user.getType());
            return ResponseEntity.ok(new JwtResponse(token));
    }

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
