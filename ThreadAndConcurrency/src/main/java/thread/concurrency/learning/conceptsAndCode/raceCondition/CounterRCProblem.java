package thread.concurrency.learning.conceptsAndCode.raceCondition;

public class CounterRCProblem {
    private static int counter = 0;

    /**
     * Scenario that causes lost update:
     *
     * Current counter = 5
     * Thread A reads 5
     * Thread B reads 5 (before A writes)
     * Thread A adds 1 → 6, writes 6
     * Thread B adds 1 → 6, writes 6
     *
     * Result: Counter becomes 6 instead of 7. One increment was lost.
     * This can happen thousands of times when you have many threads and many iterations.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Runnable job = () -> {
            for (int i = 0; i < 10000; i++)
                counter++; // Race condition here
        };
        Thread t1 = new Thread(job, "executer-1");
        Thread t2 = new Thread(job, "executer-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Final counter value: " + counter);
        // Expected: 20000, Actual: Usually less (14237, 13137)
    }
}
