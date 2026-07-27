package ir.pepco.tpgArian.conceptsAndCode.state;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Worker(), "State-Demo-Thread");
        System.out.println("Before start: " + t.getState()); //NEW
        t.start();
        System.out.println("After start: " + t.getState()); // RUNNABLE
        Thread.sleep(500);
        synchronized (Worker.lock) {
            // This may show BLOCKED if Worker is holding the lock
            System.out.println("Main holding lock, worker state: " + t.getState());
        }
        t.join(); // Main waits for t
        System.out.println("After Completion: " + t.getState()); // TERMINATED
    }

    static class Worker implements Runnable {
        static final Object lock = new Object();
        @Override
        public void run() {
            System.out.println("Worker started - " + Thread.currentThread().getState());
            try {
                Thread.sleep(1000); // TIMED_WAITING
                synchronized (lock) {
                    lock.wait(2000); // WAITING/TIMED_WAITING
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Worker finished");
        }
    }
}
