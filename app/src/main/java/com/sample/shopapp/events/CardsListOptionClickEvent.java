package com.sample.shopapp.events;

import com.sample.shopapp.models.Card;

/**
 * Created by vaibhav on 12/21/15.
 */
public class CardsListOptionClickEvent {
    public Card card;

    public CardsListOptionClickEvent(Card card) {
        this.card = card;
    }
}
