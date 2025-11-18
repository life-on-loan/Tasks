public class MockClient {
    private static final int N = 4;
    private final AtomicInteger counter = new AtomicInteger();

    public CompletableFuture<String> restCall() {
        int current;
        do {
            current = counter.get();
            if (current >= N) {
                return CompletableFuture.failedFuture(new RuntimeException("not ok"));
            }
        } while (!counter.compareAndSet(current, current + 1));

        return CompletableFuture.completedFuture("ok");
    }
}