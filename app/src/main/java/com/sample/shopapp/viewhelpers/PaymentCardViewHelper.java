package com.sample.shopapp.viewhelpers;

import android.view.View;
import android.widget.TextView;

import com.sample.shopapp.R;
import com.sample.shopapp.models.Card;
import com.sample.shopapp.models.Payment;

public class PaymentCardViewHelper {

    private View parentView;
    private TextView cardNumberTxt;
    private TextView cardTypeTxt;

    public PaymentCardViewHelper(View parentView) {
        this.parentView = parentView;
    }

    public void setUpCard(Payment payment) {
        cardNumberTxt   = (TextView) parentView.findViewById(R.id.cart_payment_item_card_txt);
        cardTypeTxt     = (TextView) parentView.findViewById(R.id.cart_payment_item_card_type_txt);

        if (payment != null) {
            Card selectedCardForPayment = payment.getSource();
            cardNumberTxt.setText("XXXX-XXXX-XXXX-" + selectedCardForPayment.getLastDigits());
            cardTypeTxt.setText(selectedCardForPayment.getType());
        } else {
            cardNumberTxt.setText("");
            cardTypeTxt.setText("");
        }
    }

    public String getCardNumber() {
        return cardNumberTxt.getText().toString();
    }
}
