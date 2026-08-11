package thread.concurrency.learning.conceptsAndCode.atomicClasses;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterAtomicSolution {
    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * proper usage of Atomic classes
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Runnable job = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": is incrementing " + counter.getAndIncrement());
            }

        };
        Thread t1 = new Thread(job, "executer-1");
        Thread t2 = new Thread(job, "executer-2");
        Thread t3 = new Thread(job, "executer-3");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Final counter value: " + counter);
        // Expected: 20000, Actual: Usually less (14237, 13137)
    }
}
