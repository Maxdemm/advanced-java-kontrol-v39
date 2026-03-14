package org.example;

import org.example.exception.PaymentException;

public class PayPalPayment implements PaymentMethod {

    private static final int MIN_AMOUNT = 300;

    @Override
    public void pay(Money amount) {
        if (amount.getAmount() <= MIN_AMOUNT) {
            throw new PaymentException("PayPal requires amount >= 300");
        }
    }
}
