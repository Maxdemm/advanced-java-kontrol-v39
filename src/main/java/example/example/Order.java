package example.example;

public class Order {
    private final String id;
    private final Email customerEmail;
    private final OrderItem[] items;
    private final PaymentMethod paymentMethod;
    private final boolean vip;
    private OrderStatus status;
    private boolean shipmentConfirmed;

    public Order(String id, Email customerEmail, OrderItem[] items, PaymentMethod paymentMethod, boolean vip) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.items = items.clone();
        this.paymentMethod = paymentMethod;
        this.vip = vip;
        this.status = OrderStatus.NEW;
        this.shipmentConfirmed = false;
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

    public boolean isVip() {
        return vip;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isShipmentConfirmed() {
        return shipmentConfirmed;
    }

    public void confirmShipment() {
        this.shipmentConfirmed = true;
    }
}