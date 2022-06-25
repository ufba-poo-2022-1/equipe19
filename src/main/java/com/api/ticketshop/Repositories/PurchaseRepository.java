package com.api.ticketshop.Repositories;

import com.api.ticketshop.Models.PurchaseModel;
import com.api.ticketshop.Services.PurchaseService;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<PurchaseModel, Integer> {

    List<PurchaseModel> findByUserId(Integer id);

    PurchaseModel findPurchaseModelByUserIdAndId(Integer userId, Integer purchaseId);
}
