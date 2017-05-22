package com.sample.shopapp.events;

/**
 * Created by vaibhav on 12/29/15.
 */
public class CheckoutAddressSelectedEvent {
    public Integer addressId;

    public CheckoutAddressSelectedEvent(Integer addressId) {
        this.addressId = addressId;
    }
}
