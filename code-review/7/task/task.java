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

// TODO: проведи код-ревью, распознай проблему и предложи решение