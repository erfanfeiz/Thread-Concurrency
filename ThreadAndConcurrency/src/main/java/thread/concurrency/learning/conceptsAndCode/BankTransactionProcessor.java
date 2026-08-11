package thread.concurrency.learning.conceptsAndCode;

public class BankTransactionProcessor implements Runnable {
    private String transactionId;

    public BankTransactionProcessor(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void run() {
        System.out.println("Processing transaction: " + transactionId + " - from " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new BankTransactionProcessor("123123"), "Txn-Processor-1");
        Thread thread2 = new Thread(new BankTransactionProcessor("147741"), "Txn-Processor-2");
        Thread thread3 = new Thread(new BankTransactionProcessor("258852"), "Txn-Processor-3");
        Thread thread4 = new Thread(new BankTransactionProcessor("369963"), "Txn-Processor-4");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
