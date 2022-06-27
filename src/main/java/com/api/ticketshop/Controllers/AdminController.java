package com.api.ticketshop.Controllers;

import com.api.ticketshop.Models.BillingAddressModel;
import com.api.ticketshop.Models.PurchaseModel;
import com.api.ticketshop.Models.UserModel;
import com.api.ticketshop.Repositories.BillingAddressRepository;
import com.api.ticketshop.Services.PurchaseService;
import com.api.ticketshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class that contains all endpoints of an Admin
 */
@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    final UserService userService;
    final PurchaseService purchaseService;

    /**
     * Class constructor that receives the Service Interface.
     * This constructor is actually a Dependency Injection Point.
     * @param userService, purchaseService
     */
    public AdminController(UserService userService, PurchaseService purchaseService){

        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @Autowired
    private BillingAddressRepository billingAddressRepository;

    /**
     * Method to find all existing users in the database and return only admin.
     * @param token
     * @return List<UserModel>
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserModel> listAllUsers(@RequestHeader(name="Authorization") String token) {

        if(!userService.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an admin.");
        }

        List<UserModel> users = userService.listAllUsers();

        if(users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No clients yet");
        }

        return users;
    }

    /**
     * Method to find a specific user who is an admin by his id.
     * @param id, token
     * @return Optional<UserModel>
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public Optional<UserModel> getUserByID(@PathVariable String id, @RequestHeader(name="Authorization") String token) {

        if(!userService.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an admin.");
        }

        Optional<UserModel> user = userService.getUserByID(id);

        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No client found with the given id");
        }

        return user;
    }

    /**
     * Method to find the address of a user who is an admin.
     * @param id, token
     * @return BillingAddressModel
     */
    @RequestMapping(value = "/users/{id}/address", method = RequestMethod.GET)
    public BillingAddressModel getUserAddress(@PathVariable String id, @RequestHeader(name="Authorization") String token){

        if(!userService.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an admin.");
        }

        return userService.getUserAddress(id);
    }

    /**
     * Method to update data of a user who is an admin.
     * @param id, fields, token
     * @return UserModel
     */
    @RequestMapping(value="/users/{id}", method = RequestMethod.PATCH)
    public UserModel updateUserInfo(@PathVariable String id, @RequestBody Map<Object, Object> fields, @RequestHeader(name="Authorization") String token) {

        if(!userService.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an admin.");
        }

        return userService.updateUserInfo(id, fields);
    }

    /**
     * Method to delete a user who is an admin by his id.
     * @param id, token
     */
    @RequestMapping(value="/users/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByID(@PathVariable String id, @RequestHeader(name="Authorization") String token)  {

        if(!userService.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an admin.");
        }

        if(!userService.deleteUserByID(id)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Error Ocurred when Deleting a User");
        };
    }

    /**
     * Method to delete a user who is an admin by his purchaseId.
     * @param token, purchaseId
     */
    @DeleteMapping(value = "/purchases/{purchaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePurchaseById(@RequestHeader(name="Authorization") String token, @PathVariable String purchaseId){

        if(!userService.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an admin.");
        }

        if(!purchaseService.deletePurchaseById(purchaseId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This purchase does not exist.");
        }

    }

    /**
     * Method to find all user's Purchases.
     * @param token
     * @return List<PurchaseModel>
     */
    @GetMapping(value = "/purchases")
    public List<PurchaseModel> deletePurchaseById(@RequestHeader(name="Authorization") String token){

        if(!userService.isAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an admin.");
        }

        return purchaseService.getAllPlatformPurchases();
    }
}
