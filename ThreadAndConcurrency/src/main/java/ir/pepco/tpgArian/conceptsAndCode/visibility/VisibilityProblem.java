package ir.pepco.tpgArian.conceptsAndCode.visibility;

public class VisibilityProblem {
    private static boolean ready = false;   // Try making this volatile
    private static int number = 0;

    /**
     * Possible behaviors:
     *
     * Works correctly
     * Prints 0 (stale value of number)
     * Reader thread never exits (never sees ready = true)
     * @param args
     */
    public static void main(String[] args) {
        Thread reader = new Thread(() -> {
            while (!ready) {
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
        writer.start();
    }
}
