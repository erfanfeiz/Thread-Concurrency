package ir.pepco.tpgArian.concurrencyTools.threadPools.forkJoinFramework_and_parallelStreams;

import java.util.stream.LongStream;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        long sum = LongStream.rangeClosed(1, 10).parallel().sum();
        System.out.println(sum);
    }
}
