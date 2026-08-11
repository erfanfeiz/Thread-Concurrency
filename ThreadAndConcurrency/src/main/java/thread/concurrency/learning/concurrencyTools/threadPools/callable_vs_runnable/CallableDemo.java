package thread.concurrency.learning.concurrencyTools.threadPools.callable_vs_runnable;

import java.util.concurrent.*;

/**
 * Runnable vs Callable
 *
 * Runnable:
 * - Represents a task that does not return a result.
 * - Its method is:
 *       void run()
 * - Cannot declare checked exceptions.
 *
 * Callable:
 * - Represents a task that returns a result.
 * - Its method is:
 *       V call() throws Exception
 * - Can declare and propagate checked exceptions.
 * - The return type is generic and defined as Callable<V>.
 *
 * Example:
 *
 * Runnable:
 *     Runnable task = () -> {
 *         System.out.println("Doing some work");
 *     };
 *
 * Callable:
 *     Callable<Integer> task = () -> {
 *         return 42;
 *     };
 *
 * When submitted to an ExecutorService:
 *
 *     executor.submit(Runnable)
 *         -> Future<?>
 *
 *     executor.submit(Callable<V>)
 *         -> Future<V>
 *
 * Key difference:
 *
 *     Runnable  = "Do this work."
 *     Callable  = "Do this work and give me a result."
 *
 * Callable is a separate functional interface designed for computations
 * that produce a result and may throw checked exceptions.
 */
public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<Integer> taks = () -> {
            System.out.println("Callable is called");
            Thread.sleep(2000);
            return 42;
        };
        Future<Integer> future = executor.submit(taks);
        Integer result = null;
        System.out.println("Doing other work");
        try {
            result = future.get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Result : " + result);
        executor.shutdown();
    }
}
