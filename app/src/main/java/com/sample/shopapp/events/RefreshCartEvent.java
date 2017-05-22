package com.sample.shopapp.events;

import com.sample.shopapp.models.Order;

/**
 * Created by vaibhav on 1/4/16.
 */
public class RefreshCartEvent {
    public Order order;
    public RefreshCartEvent(Order order) {
        this.order = order;
    }
}
