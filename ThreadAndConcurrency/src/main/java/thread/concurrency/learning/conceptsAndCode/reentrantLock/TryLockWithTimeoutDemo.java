package thread.concurrency.learning.conceptsAndCode.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockWithTimeoutDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int balance = 1000;

    public boolean withdraw(int amount) {
        try {
            // Wait maximum 2 seconds for the lock
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                try {
                    if (balance >= amount) {
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName() 
                            + " withdrew " + amount + " | New balance: " + balance);
                        return true;
                    } else {
                        System.out.println(Thread.currentThread().getName() 
                            + " - insufficient funds");
                        return false;
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                // Could not get the lock within 2 seconds
                System.out.println(Thread.currentThread().getName() 
                    + " - could not acquire lock in time");
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " was interrupted");
            return false;
        }
    }

    public static void main(String[] args) {
        TryLockWithTimeoutDemo account = new TryLockWithTimeoutDemo();

        Runnable task = () -> account.withdraw(300);

        Thread t1 = new Thread(task, "Customer-1");
        Thread t2 = new Thread(task, "Customer-2");
        Thread t3 = new Thread(task, "Customer-3");

        t1.start();
        t2.start();
        t3.start();
    }
}