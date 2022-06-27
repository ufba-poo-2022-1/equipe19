package com.api.ticketshop.Controllers;

import com.api.ticketshop.Models.PurchaseModel;
import com.api.ticketshop.Services.PurchaseService;
import com.api.ticketshop.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/purchases")
public class PuchaseController {

    final PurchaseService purchaseService;
    final UserService userService;


    public PuchaseController(PurchaseService purchaseService, UserService userService) {
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @PostMapping
    public void createPurchase(@RequestBody @Valid PurchaseModel purchase, @RequestHeader(name="Authorization") String token) {

        String userId = userService.getUserIdFromToken(token);

        purchaseService.createPuchase(purchase, userId);

    }

    @GetMapping
    public List<PurchaseModel> getUserPurchases(@RequestHeader(name="Authorization") String token){
        String userId = userService.getUserIdFromToken(token);
        return purchaseService.getUserPurchases(userId);
    }

    @GetMapping("/{purchaseId}")
    public PurchaseModel getUserPurchaseByPurchaseId(@RequestHeader(name="Authorization") String token, @PathVariable String purchaseId){
        String userId = userService.getUserIdFromToken(token);
        PurchaseModel purchase = purchaseService.getUserPurchaseByPurchaseId(purchaseId, userId);
        if(purchase == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not have this purchase");
        }
        return purchase;
    }

}
