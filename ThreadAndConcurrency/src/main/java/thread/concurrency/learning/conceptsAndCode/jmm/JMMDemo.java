package thread.concurrency.learning.conceptsAndCode.jmm;

public class JMMDemo {
    private static int data = 0;
    private static volatile boolean ready = false;   // volatile creates happens-before

    public static void main(String[] args) {
        Thread writer = new Thread(() -> {
            data = 42;          // (1)
            ready = true;       // (2)  write to volatile
        });

        Thread reader = new Thread(() -> {
            while (!ready) {    // (3)  read of volatile
                // spin
            }
            System.out.println(data);  // (4)  guaranteed to see 42
        });

        reader.start();
        writer.start();
    }
}
