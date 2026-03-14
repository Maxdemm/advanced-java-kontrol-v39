package example.example;

import example.example.exception.ShipmentConfirmationException;
import example.example.exception.ValidationException;

public class Validator extends OrderProcessor {

    public Validator(AppLogger logger, OrderNotifier notifier) {
        super(logger, notifier);
    }

    @Override
    protected void validate(Order order) {
        if (order == null)
            throw new ValidationException("Order must not be null");
        OrderItem[] items = order.getItems();
        if (items.length == 0)
            throw new ValidationException("Order must contain at least one item");
        if (items.length > 5)
            throw new ValidationException("Order can contain at most 5 items");

        for (OrderItem item : items) {
            if (item.getQuantity() <= 0)
                throw new ValidationException("must be more then 0");
            if (item.getQuantity() > 20)
                throw new ValidationException("you exceed limit 20");
        }
    }

    @Override
    protected void confirmShipment(Order order) {
        if (!order.isShipmentConfirmed()) {
            throw new ShipmentConfirmationException("Shipment must be confirmed ");
        }
    }
}
