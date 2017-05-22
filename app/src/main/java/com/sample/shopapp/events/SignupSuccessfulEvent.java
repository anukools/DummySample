package com.sample.shopapp.events;

import com.sample.shopapp.models.User;

/**
 * Created by vaibhav on 12/3/15.
 */
public class SignupSuccessfulEvent {
    public User user;

    public SignupSuccessfulEvent(User user) {
        this.user = user;
    }
}
