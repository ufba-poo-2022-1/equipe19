package com.api.ticketshop.Repositories;

import com.api.ticketshop.Models.PurchaseModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends CrudRepository<PurchaseModel, Integer> {

    List<PurchaseModel> findByUserId(Integer id);

    PurchaseModel findPurchaseModelByUserIdAndId(Integer userId, Integer purchaseId);
    Optional<PurchaseModel> findPurchaseModelByUserIdAndUserBillingAddressIdAndId(Integer userId, Integer userBillingAddressId, Integer purchaseId);
}
