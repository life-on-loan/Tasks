// 1. Ответ: 1 транзакция

//someMethod() [транзакция открыта]
//        └─ someMethod1() [выполняется в существующей транзакции, REQUIRES_NEW игнорируется]
//                 └─ someMethod2() [выполняется в той же транзакции, аннотация на private игнорируется]

// 2. Как сделать 3 транзакции, вариант 1 - self-injection:

@Component
public class SomeServiceWithTransactional {

    @Autowired
    private SomeServiceWithTransactional self;

    @Transactional
    public void someMethod() {
        // some logic
        self.someMethod1(); // Вызов через прокси
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void someMethod1() {
        // some logic
        self.someMethod2(); // Вызов через прокси
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void someMethod2() {
        // some logic
    }
}

// Вариант 2 - использовать AopContext.currentProxy()

@Component
@EnableAspectJAutoProxy(exposeProxy = true)
public class SomeServiceWithTransactional {

    @Transactional
    public void someMethod() {
        // some logic
        ((SomeServiceWithTransactional) AopContext.currentProxy()).someMethod1();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void someMethod1() {
        // some logic
        ((SomeServiceWithTransactional) AopContext.currentProxy()).someMethod2();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void someMethod2() {
        // some logic
    }
}