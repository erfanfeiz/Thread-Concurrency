package thread.concurrency.learning.conceptsAndCode.atomicClasses;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    private static final AtomicInteger value = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                int current;
                do {
                    current = value.get();
                } while (!value.compareAndSet(current, current + 1));
            }
        };

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final value: " + value.get()); // Always 2000
    }
}
