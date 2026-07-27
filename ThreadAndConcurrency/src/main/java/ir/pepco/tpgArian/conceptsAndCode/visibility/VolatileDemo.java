package ir.pepco.tpgArian.conceptsAndCode.visibility;

public class VolatileDemo {
    private static  boolean running = true; //Try removing volatile

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Worker started");
            while (running) {
                // do work
            }
            System.out.println("Worker stopped");
        }, "Worker");
        thread.start();
        Thread.sleep(1000);
        System.out.println("Main thread setting running = false");
        running = false;
    }
}
