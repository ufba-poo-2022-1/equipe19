package com.api.ticketshop.Services;

import com.api.ticketshop.Models.PurchaseModel;
import com.api.ticketshop.Models.UserModel;
import com.api.ticketshop.Repositories.PurchaseRepository;
import com.api.ticketshop.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PurchaseService {

    final PurchaseRepository purchaseRepository;
    final UserService userService;

    public PurchaseService(PurchaseRepository purchaseRepository, UserService userService) {
        this.purchaseRepository = purchaseRepository;
        this.userService = userService;
    }

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

    @Transactional
    public List<PurchaseModel> getUserPurchases(String userId) {
        return purchaseRepository.findByUserId(Integer.parseInt(userId));
    }

    @Transactional
    public PurchaseModel getUserPurchaseByPurchaseId(String purchaseId, String userId){
        return purchaseRepository.findPurchaseModelByUserIdAndId(Integer.parseInt(userId), Integer.parseInt(purchaseId));
    }

    @Transactional
    public Optional<PurchaseModel> getPurchaseModelByUserIdAndUserBillingAddressIdAndId(Integer userId, Integer userBillingAddressId, Integer purchaseId){
        return purchaseRepository.findPurchaseModelByUserIdAndUserBillingAddressIdAndId(userId, userBillingAddressId, purchaseId);
    }

    @Transactional
    public boolean deletePurchaseById(String purchaseId) {
        if(purchaseRepository.findById(Integer.parseInt(purchaseId)).isPresent()){
            purchaseRepository.deleteById(Integer.parseInt(purchaseId));
            return true;
        }
        return false;
    }

    @Transactional
    public List<PurchaseModel> getAllPlatformPurchases() {
        return (List<PurchaseModel>) purchaseRepository.findAll();
    }
}
