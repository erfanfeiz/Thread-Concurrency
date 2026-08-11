package thread.concurrency.learning.conceptsAndCode.deadlocks;

public class DeadLockSolution {
    private static Object lockA = new Object();
    private static Object lockB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread-1 locked A");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread-1: Trying to lock B...");
                synchronized (lockB) {
                    System.out.println("Thread-1 locked B");
                }
            }

        }, "Thread-1");


        Thread t2 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread-2 locked A");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread-2: Trying to lock A...");
                synchronized (lockB) {
                    System.out.println("Thread-2 locked B");
                }
            }
        }, "Thread-2");
        t1.start();
        t2.start();
    }
}
