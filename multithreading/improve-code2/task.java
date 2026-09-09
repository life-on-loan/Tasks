public class MockClient {
    static int N = 4;
    AtomicInteger counter = new AtomicInteger();

    public CompletableFuture<String> restCall() {
        if (counter.get() != N) {
            counter.incrementAndGet();
            return CompletableFuture.completedFuture("ok");
        } else {
            return CompletableFuture.failedFuture(new RuntimeException("not ok"));
        }
    }
}

// TODO: Понять, что делает метод, найти и показать многопоточную проблему