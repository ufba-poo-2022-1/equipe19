package com.api.ticketshop.Services;

import com.api.ticketshop.Models.PurchaseModel;
import com.api.ticketshop.Models.UserModel;
import com.api.ticketshop.Repositories.PurchaseRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Class that contains all the methods to make each endpoint of an Purchase
 */
@Service
public class PurchaseService {

    final PurchaseRepository purchaseRepository;
    final UserService userService;

    /**
     * Class constructor that receives the Repository Interface.
     * This constructor is actually a Dependency Injection Point.
     * @param purchaseRepository, userService
     */
    public PurchaseService(PurchaseRepository purchaseRepository, UserService userService) {
        this.purchaseRepository = purchaseRepository;
        this.userService = userService;
    }

    /**
     * Method to create a Purchase in the database.
     * @param purchase, userID
     */
    @Transactional
    public void createPuchase(PurchaseModel purchase, String userID) {
        Optional<UserModel> user = userService.getUserByID(userID);
        if(user.isPresent()){
            UserModel userReturned = user.get();
            purchase.setUserBillingAddressId(userReturned.getBilling_address_id());
        }
        purchase.setUserId(Integer.parseInt(userID));
        purchase.setHash(UUID.randomUUID().toString());
        purchase.setCreation_date(LocalDateTime.now());
        purchase.setLast_update(LocalDateTime.now());
        purchaseRepository.save(purchase);
    }

    /**
     * Method to find all Purchases of a specific user.
     * @param userId
     * @return List<PurchaseModel>
     */
    @Transactional
    public List<PurchaseModel> getUserPurchases(String userId) {
        return purchaseRepository.findByUserId(Integer.parseInt(userId));
    }

    /**
     * Method to find a Purchase of a specific user by Purchase's id and user's id.
     * @param purchaseId, userId
     * @return PurchaseModel
     */
    @Transactional
    public PurchaseModel getUserPurchaseByPurchaseId(String purchaseId, String userId){
        return purchaseRepository.findPurchaseModelByUserIdAndId(Integer.parseInt(userId), Integer.parseInt(purchaseId));
    }

    /**
     * Method to find a Purchase by user's id, userBillingAddress's id and Purchase's id.
     * @param userId, userBillingAddressId, purchaseId
     * @return Optional<PurchaseModel>
     */
    @Transactional
    public Optional<PurchaseModel> getPurchaseModelByUserIdAndUserBillingAddressIdAndId(Integer userId, Integer userBillingAddressId, Integer purchaseId){
        return purchaseRepository.findPurchaseModelByUserIdAndUserBillingAddressIdAndId(userId, userBillingAddressId, purchaseId);
    }

    /**
     * Method to delete a Purchase by its id.
     * @param purchaseId
     * @return boolean
     */
    @Transactional
    public boolean deletePurchaseById(String purchaseId) {
        if(purchaseRepository.findById(Integer.parseInt(purchaseId)).isPresent()){
            purchaseRepository.deleteById(Integer.parseInt(purchaseId));
            return true;
        }
        return false;
    }

    /**
     * Method to list all Purchases.
     * @return List<PurchaseModel>
     */
    @Transactional
    public List<PurchaseModel> getAllPlatformPurchases() {
        return (List<PurchaseModel>) purchaseRepository.findAll();
    }
}
