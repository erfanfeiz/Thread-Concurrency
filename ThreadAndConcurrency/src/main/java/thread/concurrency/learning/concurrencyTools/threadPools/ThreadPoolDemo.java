package thread.concurrency.learning.concurrencyTools.threadPools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        // Create a pool with 3 worker threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " executed by "
                        + Thread.currentThread().getName());
                try { Thread.sleep(500); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Graceful shutdown
        executor.shutdown();                       // No more new tasks
        executor.awaitTermination(5, TimeUnit.SECONDS); // Wait for existing tasks
        System.out.println("All tasks finished");
    }
}
