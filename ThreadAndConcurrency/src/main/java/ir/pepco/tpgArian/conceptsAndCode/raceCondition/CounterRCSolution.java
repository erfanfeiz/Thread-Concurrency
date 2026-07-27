package ir.pepco.tpgArian.conceptsAndCode.raceCondition;

public class CounterRCSolution {
    private static int counter = 0;

    /**
     * proper usage of synchronization
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Runnable job = () -> {
            for (int i = 0; i < 10000; i++)
                synchronized (CounterRCSolution.class) {
                    increment(); // Race condition here
                }
        };
        Thread t1 = new Thread(job, "executer-1");
        Thread t2 = new Thread(job, "executer-2");
        Thread t3 = new Thread(job, "executer-3");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Final counter value: " + counter);
        // Expected: 20000, Actual: Usually less (14237, 13137)
    }

    private static void increment() {
        counter++;
    }
}
