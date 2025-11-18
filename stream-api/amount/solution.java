@Value
@Builder
public class Order {
    String title;
    Double amount;
}

public class OrderService {
    private final List<Order> orders;

    public Double getTotalAmount() {
        return orders.stream()
                .map(Order::getAmount)    // Преобразуем Order в Double (amount)
                .filter(Objects::nonNull) // Игнорируем null значения
                .mapToDouble(Double::doubleValue) // Преобразуем в double
                .sum();                   // Суммируем
    }
}