package org.example;

public class OrderItem {
    private final String productName;
    private final int quantity;
    private final Money price;

    public OrderItem(String productName, int quantity, Money price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
