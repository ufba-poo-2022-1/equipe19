package com.api.ticketshop.Controllers;

import com.api.ticketshop.DTOs.UserDTO;
import com.api.ticketshop.Models.BillingAddressModel;
import com.api.ticketshop.Models.UserModel;
import com.api.ticketshop.Repositories.BillingAddressRepository;
import com.api.ticketshop.Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<UserModel> listAllUsers() {

        List<UserModel> users = userService.listAllUsers();

        if(users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No clients yet");
        }

        return users;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Optional<UserModel> getUserByID(@PathVariable String id) {

        Optional<UserModel> user = userService.getUserByID(id);

        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No client found with the given id");
        }

        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel createNewUser(@RequestBody UserDTO newUser) {
        return userService.createNewUser(newUser);
    }

    @RequestMapping(value="{id}", method = RequestMethod.PATCH)
    public UserModel updateUserInfo(@PathVariable String id, @RequestBody Map<Object, Object> fields) {
        return userService.updateUserInfo(id, fields);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByID(@PathVariable String id)  {
        if(!userService.deleteUserByID(id)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Error Ocurred when Deleting a User");
        };
    }

    @RequestMapping(value = "{id}/address", method = RequestMethod.GET)
    public BillingAddressModel getUserAddress(@PathVariable String id){
        return userService.getUserAddress(id);
    }

    @RequestMapping(value = "{userId}/address", method = RequestMethod.PATCH)
    public BillingAddressModel updateUserAddress(@PathVariable String userId, @RequestBody  Map<Object, Object> fields) {
        return userService.updateUserAddress(userId, fields);
    }

}
