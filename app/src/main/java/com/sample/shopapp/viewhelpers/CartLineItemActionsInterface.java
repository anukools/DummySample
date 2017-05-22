package com.sample.shopapp.viewhelpers;

import com.sample.shopapp.models.LineItem;

public interface CartLineItemActionsInterface {
    public void editQuantity(LineItem lineItem);
    public void removeLineItem(LineItem lineItem);
}
