package com.api.ticketshop.Controllers;

import com.api.ticketshop.DTOs.UserDTO;
import com.api.ticketshop.Models.BillingAddressModel;
import com.api.ticketshop.Models.UserModel;
import com.api.ticketshop.Repositories.BillingAddressRepository;
import com.api.ticketshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Autowired
    private BillingAddressRepository billingAddressRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel createNewUser(@RequestBody UserDTO newUser) {
        return userService.createNewUser(newUser);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public UserModel updateUserInfo(@RequestBody Map<Object, Object> fields, @RequestHeader(name="Authorization") String token) {

        String id = userService.getUserIdFromToken(token);

        if (id.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Error Ocurred when Deleting a User");
        }

        return userService.updateUserInfo(id, fields);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByID(@RequestHeader(name="Authorization") String token)  {

        String id = userService.getUserIdFromToken(token);

        if(!userService.deleteUserByID(id)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Error Ocurred when Deleting a User");
        };
    }

    @RequestMapping(value = "address", method = RequestMethod.GET)
    public BillingAddressModel getUserAddress(@RequestHeader(name="Authorization") String token){
        String id = userService.getUserIdFromToken(token);
        return userService.getUserAddress(id);
    }

    @RequestMapping(value = "address", method = RequestMethod.PATCH)
    public BillingAddressModel updateUserAddress(@RequestBody  Map<Object, Object> fields, @RequestHeader(name="Authorization") String token) {
        String id = userService.getUserIdFromToken(token);
        return userService.updateUserAddress(id, fields);
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public Optional<UserModel> getUserProfile(@RequestHeader(name="Authorization") String token) {
        String id = userService.getUserIdFromToken(token);
        return userService.getUserByID(id);
    }

}
