package thread.concurrency.learning.concurrencyTools.threadPools.forkJoinFramework_and_parallelStreams;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * A simple example of the Fork/Join framework using {@link RecursiveTask}.
 * <p>
 * This class calculates the sum of all numbers in a given range [start, end]
 * by recursively splitting the work until the range is small enough to be
 * computed sequentially.
 * </p>
 *
 * <h2>How it works</h2>
 * <ol>
 *   <li>If the current range size is less than or equal to {@code THRESHOLD},
 *       the sum is calculated directly in a simple loop (base case).</li>
 *   <li>If the range is larger, it is split into two halves:
 *       <ul>
 *         <li>Left half: {@code [start, mid]}</li>
 *         <li>Right half: {@code [mid + 1, end]}</li>
 *       </ul>
 *   </li>
 *   <li>The left task is submitted asynchronously using {@code fork()}.</li>
 *   <li>The current thread computes the right task directly using {@code compute()}.</li>
 *   <li>The result of the left task is obtained with {@code join()}.</li>
 *   <li>The two partial results are added and returned.</li>
 * </ol>
 *
 * <h2>Key points</h2>
 * <ul>
 *   <li>{@code fork()} – schedules the task for asynchronous execution in the pool.</li>
 *   <li>{@code compute()} – executes the task in the current thread.</li>
 *   <li>{@code join()} – waits for the forked task to complete and retrieves its result.</li>
 *   <li>The threshold prevents excessive task creation (too many small tasks create overhead).</li>
 * </ul>
 *
 * <h2>Usage example</h2>
 * <pre>{@code
 * ForkJoinPool pool = ForkJoinPool.commonPool();
 * long result = pool.invoke(new ForkJoinSum(1, 100_000_000L));
 * System.out.println("Sum = " + result);
 * }</pre>
 *
 * @see RecursiveTask
 * @see ForkJoinPool
 */
public class ForkJoinSum extends RecursiveTask<Long> {

    private final long start;
    private final long end;

    /** Ranges smaller than or equal to this value are computed sequentially. */
    private static final long THRESHOLD = 10_000;

    public ForkJoinSum(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // Base case: range is small enough → compute sequentially
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }

        // Divide the range into two halves
        long mid = (start + end) / 2;
        ForkJoinSum left  = new ForkJoinSum(start, mid);
        ForkJoinSum right = new ForkJoinSum(mid + 1, end);

        // Fork the left task (run asynchronously)
        left.fork();

        // Compute the right task in the current thread
        long rightResult = right.compute();

        // Wait for the left task and get its result
        long leftResult = left.join();

        // Combine results
        return leftResult + rightResult;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long result = pool.invoke(new ForkJoinSum(1, 100_000_000));
        System.out.println("Sum = " + result);
    }
}