package example.example;

public class ConsoleOrderNotifier implements OrderNotifier {

    @Override
    public void notifyCustomer(Order order) {
        System.out.println("Order notification sent to " + order.getCustomerEmail());
    }
}