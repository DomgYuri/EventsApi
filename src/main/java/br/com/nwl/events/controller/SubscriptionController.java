package br.com.nwl.events.controller;

import br.com.nwl.events.dto.ErrorMessage;
import br.com.nwl.events.dto.SubscriptionResponse;
import br.com.nwl.events.exception.EventNotFound;
import br.com.nwl.events.exception.SubscriptionConflictException;
import br.com.nwl.events.exception.UserIndicatorNotFound;
import br.com.nwl.events.model.Subscription;
import br.com.nwl.events.model.User;
import br.com.nwl.events.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createNewSubscription (@PathVariable String prettyName, @RequestBody User subscriber,
                                                    @PathVariable(required = false) Long userId) {
        try {
            SubscriptionResponse subscription = subscriptionService.createNewSubscription(prettyName, subscriber, userId);
            if (subscription != null) {
                return ResponseEntity.ok().body(subscription);
            }
        } catch (EventNotFound | UserIndicatorNotFound eventNF) {
            return ResponseEntity.status(404).body(new ErrorMessage(eventNF.getMessage()));
        } catch (SubscriptionConflictException subsConflict) {
            return ResponseEntity.status(409).body(new ErrorMessage(subsConflict.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{prettyName}/Ranking")
    public ResponseEntity<?> generateRanking (@PathVariable String prettyName) {
        try{
            return ResponseEntity.ok(subscriptionService.generateRanking(prettyName));
        }catch (EventNotFound eventNotFound) {
            return ResponseEntity.status(404).body(new ErrorMessage(eventNotFound.getMessage()));
        }
    }

    @GetMapping("/subscription/{prettyName}/Ranking/{userId}")
    public ResponseEntity<?> generateRankingByEventAndUser (@PathVariable String prettyName,
                                                            @PathVariable Integer userId){
        try {
            return ResponseEntity.ok(subscriptionService.getRankingByUser(prettyName, userId));
        }catch (UserIndicatorNotFound | EventNotFound exception) {
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
    }

}
