package example.example;

import example.example.exception.AppException;
import example.example.exception.InfrastructureException;

public abstract class OrderProcessor {

    private final AppLogger logger;
    private final OrderNotifier notifier;

    protected OrderProcessor(AppLogger logger, OrderNotifier notifier) {
        this.logger = logger;
        this.notifier = notifier;
    }

    public final void process(Order order) {
        try {
            logger.info("Order started for id=" + order.getId());
            validate(order);
            order.setStatus(OrderStatus.VALIDATED);
            logger.info("Order validated: id=" + order.getId());

            int totalAmount = calculateTotal(order);
            logger.info("Total: " + totalAmount + " for id=" + order.getId());

            pay(order, totalAmount);
            order.setStatus(OrderStatus.PAID);
            logger.info("Order paid: id=" + order.getId());

            ship(order);
            order.setStatus(OrderStatus.SHIPPED);
            logger.info("Order shipped: id=" + order.getId());

            confirmShipment(order);
            order.setStatus(OrderStatus.DELIVERED);
            logger.info("Order delivered: id=" + order.getId());

            complete(order);
            logger.info("Order completed for id=" + order.getId());

        } catch (AppException e) {
            logger.warn("Business error while processing order id=" + order.getId() + ": " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            logger.error("Unexpected error while processing order id=" + order.getId(), e);
            throw new InfrastructureException("Unexpected processing error", e);
        }
    }

    protected abstract void validate(Order order);

    protected int calculateTotal(Order order) {
        int total = 0;
        for (OrderItem item: order.getItems())
            total += item.getPrice().getAmount() * item.getQuantity();
        if (total >= 15_000)
            total -= total * 8 / 100;
        if (order.isVip())
            total -= total * 2 / 100;

        return total;
    }

    protected void pay(Order order, int totalAmount) {
        order.getPaymentMethod().pay(new Money(totalAmount));
    }

    protected void ship(Order order) {
        logger.info("Shipping started order id=" + order.getId());
    }

    protected abstract void confirmShipment(Order order);

    protected void complete(Order order) {
        notifier.notifyCustomer(order);
    }
}