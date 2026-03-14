package example.example;

import example.example.exception.PaymentException;

public class BankTransferPayment implements PaymentMethod {

    @Override
    public void pay(Money amount) {
        if (amount.getAmount() <= 0)
            throw new PaymentException("amount must be positive");

        int total = calculateTotalWithCommission(amount);

        if (total <= 0)
            throw new PaymentException("Invalid transfer");
    }

    public int calculateTotalWithCommission(Money amount) {
        return amount.getAmount() + (amount.getAmount() * 2 / 100);
    }
}
