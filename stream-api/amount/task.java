@Value
@Builder
public class Order { }

String title;
Double amount;

public class OrderService {

    private final List<Order> orders;

    public Double getTotalAmount() {
        // TODO: Посчитать с помощью StreamAPI общую сумму по полю amount.

    }
}