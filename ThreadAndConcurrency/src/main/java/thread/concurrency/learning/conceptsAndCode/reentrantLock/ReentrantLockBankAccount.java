package thread.concurrency.learning.conceptsAndCode.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockBankAccount {
    private final ReentrantLock lock = new ReentrantLock();
    private int balance = 0;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockBankAccount processor = new ReentrantLockBankAccount();
        Thread thread1 = new Thread(() -> {
            processor.deposit(100);
        }, "Txn-Processor-1");
        Thread thread2 = new Thread(() -> {
            processor.withdraw(50);
        }, "Txn-Processor-2");
        Thread thread3 = new Thread(() -> {
            processor.deposit(150);
        }, "Txn-Processor-3");
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("The balance is " + processor.getBalance());
    }

    private void withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " withdrew " + amount);
            }
        } finally {
            lock.unlock();
        }
    }

    private void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited " + amount);
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() { // Important for visibility
        lock.lock();
        try {           // Important for visibility
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
