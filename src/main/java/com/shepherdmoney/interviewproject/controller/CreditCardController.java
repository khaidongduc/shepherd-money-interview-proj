package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.repository.CreditCardRepository;
import com.shepherdmoney.interviewproject.repository.UserRepository;
import com.shepherdmoney.interviewproject.vo.request.AddCreditCardToUserPayload;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import com.shepherdmoney.interviewproject.vo.response.CreditCardView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CreditCardController {

    // TODO: wire in CreditCard repository here (~1 line)
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;

    @PostMapping("/credit-card")
    public ResponseEntity<Integer> addCreditCardToUser(@RequestBody AddCreditCardToUserPayload payload) {
        // TODO: Create a credit card entity, and then associate that credit card with user with given userId
        //       Return 200 OK with the credit card id if the user exists and credit card is successfully associated with the user
        //       Return other appropriate response code for other exception cases
        //       Do not worry about validating the card number, assume card number could be any arbitrary format and length

        int userId = payload.getUserId();
        String issuanceBank = payload.getCardIssuanceBank();
        String number = payload.getCardNumber();

        if(issuanceBank == null || number == null || issuanceBank.trim().isEmpty() || number.trim().isEmpty() ||
            !userRepository.existsById(userId) || creditCardRepository.existsByNumber(number)){
            return ResponseEntity.badRequest().build();
        }

        CreditCard creditCard = new CreditCard();
        creditCard.setIssuanceBank(issuanceBank);
        creditCard.setNumber(number);
        User user = new User();
        user.setId(userId);
        creditCard.setUser(user);
        creditCardRepository.save(creditCard);

        return ResponseEntity.ok().body(creditCard.getId());
    }

    @GetMapping("/credit-card:all")
    public ResponseEntity<List<CreditCardView>> getAllCardOfUser(@RequestParam int userId) {
        // TODO: return a list of all credit card associated with the given userId, using CreditCardView class
        //       if the user has no credit card, return empty list, never return null

        if(!userRepository.existsById(userId)){
            return ResponseEntity.badRequest().build();
        }

        List<CreditCardView> creditCards = creditCardRepository.findAllByUserId(userId)
            .stream().map(x -> new CreditCardView(x.getIssuanceBank(), x.getNumber())).toList();
        return ResponseEntity.ok().body(creditCards);
    }

    @GetMapping("/credit-card:user-id")
    public ResponseEntity<Integer> getUserIdForCreditCard(@RequestParam String creditCardNumber) {
        // TODO: Given a credit card number, efficiently find whether there is a user associated with the credit card
        //       If so, return the user id in a 200 OK response. If no such user exists, return 400 Bad Request
        return null;
    }

    // @PostMapping("/credit-card:update-balance")
    // public SomeEnityData postMethodName(@RequestBody UpdateBalancePayload[] payload) {
    //     //TODO: Given a list of transactions, update credit cards' balance history.
    //     //      1. For the balance history in the credit card
    //     //      2. If there are gaps between two balance dates, fill the empty date with the balance of the previous date
    //     //      3. Given the payload `payload`, calculate the balance different between the payload and the actual balance stored in the database
    //     //      4. If the different is not 0, update all the following budget with the difference
    //     //      For example: if today is 4/12, a credit card's balanceHistory is [{date: 4/12, balance: 110}, {date: 4/10, balance: 100}],
    //     //      Given a balance amount of {date: 4/11, amount: 110}, the new balanceHistory is
    //     //      [{date: 4/12, balance: 120}, {date: 4/11, balance: 110}, {date: 4/10, balance: 100}]
    //     //      Return 200 OK if update is done and successful, 400 Bad Request if the given card number
    //     //        is not associated with a card.
        
    //     return null;
    // }
    
}
