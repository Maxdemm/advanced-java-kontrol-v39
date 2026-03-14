package example.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderRepository {

    private final Map<String, Order> storage = new HashMap<>();

    public void save(Order order) {
        storage.put(order.getId(), order);
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }
}
