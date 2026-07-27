package ir.pepco.tpgArian.conceptsAndCode.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int counter = 0;

    private void incrementCounter() {
        lock.lock(); // acquire the lock
        try {
            counter++;
            System.out.println(Thread.currentThread().getName() + " → " + counter);
        } finally {
            lock.unlock(); // always release the lock
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        Runnable runnable = () -> {
            for (int i = 0; i < 5; i++) {
                demo.incrementCounter();
            }
        };
        Thread t1 = new Thread(runnable, "Thread-A");
        Thread t2 = new Thread(runnable, "Thread-B");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
