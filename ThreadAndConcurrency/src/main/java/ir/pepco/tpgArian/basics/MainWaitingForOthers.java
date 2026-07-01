package ir.pepco.tpgArian.basics;

public class MainWaitingForOthers {
    public static void main(String[] args) {
        System.out.println("Main Thread started - " + Thread.currentThread().getName());
        Thread scan = new Thread(MainWaitingForOthers::virusScan, "Virus-Scanner");
        Thread inform = new Thread(MainWaitingForOthers::informUser, "User-Notifier");
        scan.start();
        inform.start();
        try {
            scan.join();    // Main waits for scan
            inform.join();  // Main waits for inform
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt(); // Restore interrupt status (best practice)
            System.err.println("Main thread interrupted");
        }
        System.out.println("System is done scanning!");
    }

    public static void virusScan() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Starting Virus Scan!");
    }

    public static void informUser() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Informing user!");
    }
}
