package org.example;


public class Order {
    private final String id;
    private final Email customerEmail;
    private final OrderItem[] items;
    private OrderStatus status;
    private PaymentMethod paymentMethod;

    public Order(String id, Email customerEmail, OrderItem[] items) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.items = items.clone();
        this.status = OrderStatus.NEW;
    }

    public String getId() {
        return id;
    }

    public Email getCustomerEmail() {
        return customerEmail;
    }

    public OrderItem[] getItems() {
        return items.clone();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}