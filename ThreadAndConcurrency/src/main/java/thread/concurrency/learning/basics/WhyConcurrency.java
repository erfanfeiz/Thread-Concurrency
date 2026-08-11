package thread.concurrency.learning.basics;

public class WhyConcurrency {
    public static void main(String[] args) {
        System.out.println("=== Sequential Execution ===");
        longTask("Task A");  // takes time
        longTask("Task B");

        System.out.println("\n=== Basic Concurrent Execution ===");
        Thread taskA = new Thread(() -> longTask("Task A"));
        Thread taskB = new Thread(() -> longTask("Task B"));

        taskA.start();  // Start running in background
        taskB.start();

        System.out.println("Main thread continues immediately!");
    }

    private static void longTask(String name) {
        System.out.println(name + " started - " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Simulate work (2 seconds)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(name + " completed");
    }
}
