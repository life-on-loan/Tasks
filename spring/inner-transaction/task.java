@Component
public class SomeServiceWithTransactional {

    @Transactional
    public void someMethod() {
        // some logic
        someMethod1();
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void someMethod1() {
        // some logic
        someMethod2();
    }

    @Transactional(propagation = REQUIRES_NEW)
    private void someMethod2() {
        ...
    }
}

// TODO:
// 1. Сколько транзакций будет, если вызвать метод someMethod()?
// 2. Как сделать так, чтобы было 3 транзакции?