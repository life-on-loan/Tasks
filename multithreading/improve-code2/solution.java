public class MockClient {
    private static final int N = 4;
    private final AtomicInteger counter = new AtomicInteger();

    public CompletableFuture<String> restCall() {
        if (counter.incrementAndGet() <= N) {
            return CompletableFuture.completedFuture("ok");
        } else {
            return CompletableFuture.failedFuture(new RuntimeException("not ok"));
        }
    }
}