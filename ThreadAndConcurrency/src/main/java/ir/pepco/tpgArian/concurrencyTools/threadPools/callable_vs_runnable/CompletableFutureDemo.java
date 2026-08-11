package ir.pepco.tpgArian.concurrencyTools.threadPools.callable_vs_runnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(5000); } catch (InterruptedException e) {}
            return 42;
        });
        System.out.println("Doing other work!");
        // non-blocking transformation
        CompletableFuture<String> resultFuture = future.thenApply(n -> "The answer is: " + n)
                .exceptionally(ex -> "Error: " + ex.getMessage());
        System.out.println("Doing some other work!!");
        System.out.println(resultFuture.get());
    }
}
