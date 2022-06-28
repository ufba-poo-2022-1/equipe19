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
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

/**
 * Class that contains all endpoints of a User
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    final UserService userService;

    /**
     * Class constructor that receives the Service Interface.
     * This constructor is actually a Dependency Injection Point.
     * @param userService
     */
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Autowired
    private BillingAddressRepository billingAddressRepository;

    /**
     * Method to creat a new User.
     * @param newUser
     * @return UserModel
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel createNewUser(@RequestBody @Valid UserDTO newUser) {
        return userService.createNewUser(newUser);
    }

    /**
     * Method to update a specific User.
     * @param fields, token
     * @return UserModel
     */
    @RequestMapping(method = RequestMethod.PATCH)
    public UserModel updateUserInfo(@RequestBody Map<Object, Object> fields, @RequestHeader(name="Authorization") String token) {

        String id = userService.getUserIdFromToken(token);

        if (id.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Error Ocurred when Deleting a User");
        }

        return userService.updateUserInfo(id, fields);
    }

    /**
     * Method to delete a User by its id.
     * @param token
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByID(@RequestHeader(name="Authorization") String token)  {

        String id = userService.getUserIdFromToken(token);

        if(!userService.deleteUserByID(id)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Error Ocurred when Deleting a User");
        };
    }

    /**
     * Method to find the user's address by user's id.
     * @param token
     * @return BillingAddressModel
     */
    @RequestMapping(value = "address", method = RequestMethod.GET)
    public BillingAddressModel getUserAddress(@RequestHeader(name="Authorization") String token){
        String id = userService.getUserIdFromToken(token);
        return userService.getUserAddress(id);
    }

    /**
     * Method to update the user's address by user's id.
     * @param fields, token
     * @return BillingAddressModel
     */
    @RequestMapping(value = "address", method = RequestMethod.PATCH)
    public BillingAddressModel updateUserAddress(@RequestBody  Map<Object, Object> fields, @RequestHeader(name="Authorization") String token) {
        String id = userService.getUserIdFromToken(token);
        return userService.updateUserAddress(id, fields);
    }

    /**
     * Method to find a user by its id.
     * @param token
     * @return  Optional<UserModel>
     */
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public Optional<UserModel> getUserProfile(@RequestHeader(name="Authorization") String token) {
        String id = userService.getUserIdFromToken(token);
        return userService.getUserByID(id);
    }

}
