package example.example;

import example.example.exception.PaymentException;

public class CardPayment implements PaymentMethod {

    private static final int LIMIT = 30_000;

    @Override
    public void pay(Money amount) {
        if (amount.getAmount() <= 0)
            throw new PaymentException("Amount must be positive");
        if (amount.getAmount() > LIMIT)
            throw new PaymentException("you have exceeded the limit");
    }

}
