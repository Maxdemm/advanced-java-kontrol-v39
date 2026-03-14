package example;

import example.example.*;
import example.example.exception.PaymentException;
import example.example.exception.ShipmentConfirmationException;
import example.example.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    private final Validator processor = new Validator(new AppLogger(), new ConsoleOrderNotifier());

    @Test
    void successProcess() {
        Order order = buildOrder(item(2, 1000), new CardPayment(), false);
        order.confirmShipment();
        processor.process(order);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    void tooManyItems() {
        OrderItem[] items = {
                new OrderItem("A", 1, new Money(100)),
                new OrderItem("B", 1, new Money(100)),
                new OrderItem("C", 1, new Money(100)),
                new OrderItem("D", 1, new Money(100)),
                new OrderItem("E", 1, new Money(100)),
                new OrderItem("F", 1, new Money(100))
        };
        Order order = buildOrder(items, new CardPayment(), false);
        assertThrows(ValidationException.class, () -> processor.process(order));
    }

    @Test
    void qtyTooBig() {
        Order order = buildOrder(item(21, 100), new CardPayment(), false);
        assertThrows(ValidationException.class, () -> processor.process(order));
    }

    @Test
    void qtyNotPositive() {
        Order order = buildOrder(item(0, 100), new CardPayment(), false);
        assertThrows(ValidationException.class, () -> processor.process(order));
    }

    @Test
    void noShipmentConfirm() {
        Order order = buildOrder(item(2, 1000), new CardPayment(), false);
        assertThrows(ShipmentConfirmationException.class, () -> processor.process(order));
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
    }

    @Test
    void cardLimit() {
        Order order = buildOrder(item(2, 20000), new CardPayment(), false);
        order.confirmShipment();
        assertThrows(PaymentException.class, () -> processor.process(order));
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 500, 1000})
    void paypalOk(int amount) {
        PayPalPayment payment = new PayPalPayment();
        assertDoesNotThrow(() -> payment.pay(new Money(amount)));
    }

    @Test
    void paypalTooSmall() {
        PayPalPayment payment = new PayPalPayment();
        assertThrows(PaymentException.class, () -> payment.pay(new Money(299)));
    }
    private Order buildOrder(OrderItem[] items, PaymentMethod paymentMethod, boolean vip) {
        return new Order("order-1", new Email("customer@test.com"), items, paymentMethod, vip);
    }

    private OrderItem[] item(int qty, int price) {
        return new OrderItem[]{new OrderItem("Book", qty, new Money(price))};
    }

    @Test
    void findOrder() {
        OrderRepository repo = new OrderRepository();
        Order order = buildOrder(item(1,100), new CardPayment(), false);

        repo.save(order);

        Optional<Order> found = repo.findById(order.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void orderNotFound() {
        OrderRepository repo = new OrderRepository();
        assertTrue(repo.findById("unknown").isEmpty());
    }
}
