package ir.pepco.tpgArian.exercises.visibility;

public class VisibilitySolution {
    private static volatile boolean ready = false;   // Try making this volatile
    private static int number = 0;

    /**
     * works with and without volatile
     * Why does the reader eventually see the update (even without volatile)?
     * There are several reasons why it doesn't hang forever in this version:
     *
     * The printing statement forces visibility
     * System.out.println() is a synchronized operation. When the reader thread calls println, it creates a memory barrier (a point where the JVM is forced to synchronize caches with main memory). This can make the reader see the update to ready.
     * The loop is no longer "tight"
     * Adding counter++ and the modulo check + printing makes the loop do more work. This gives the JVM/OS scheduler more opportunities to refresh caches.
     * JVM Implementation Luck
     * On some JVMs, under certain loads, or after some time, the cache coherence protocol (MESI protocol used by CPUs) eventually propagates the change. But this is unreliable.
     * Thread.sleep(1000) in main
     * This gives more time for scheduling.
     */
    public static void main(String[] args) throws InterruptedException {
        Thread reader = new Thread(() -> {
            int counter = 0;
            while (!ready) {
                counter++;
                if (counter % 10_000_000 == 0) {
                    System.out.println("Reader still waiting...");
                }
                // Busy wait - might loop forever due to visibility issue
            }
            System.out.println("Number = " + number);
        }, "Reader-Thread");

        Thread writer = new Thread(() -> {
            number = 42;
            ready = true;
            System.out.println("Writer finished");
        }, "Writer-Thread");

        reader.start();
        Thread.sleep(1000);
        writer.start();
    }

}
