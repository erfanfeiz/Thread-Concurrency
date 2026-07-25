package ir.pepco.tpgArian.exercises.intrinsicLock;

public class BankTransactionProcessorSync {
    private final Object lock = new Object();
    private int balance = 0;

    public static void main(String[] args) throws InterruptedException {
        BankTransactionProcessorSync processor = new BankTransactionProcessorSync();
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
        System.out.println("The balance is " + processor.balance);
    }

    private void withdraw(int amount) {
        synchronized (lock) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount);
        }
    }

    private void deposit(int amount) {
        synchronized (lock) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited " + amount);
        }
    }

    public int getBalance() { // Important for visibility
        synchronized (lock) {           // Important for visibility
            return balance;
        }
    }
}
