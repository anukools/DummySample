package com.sample.shopapp.events;

/**
 * Created by vaibhav on 12/29/15.
 */
public class CheckoutCardSelectedEvent {
    public Integer cardId;

    public CheckoutCardSelectedEvent(Integer cardId) {
        this.cardId = cardId;
    }
}
