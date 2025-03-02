package top.sorie.juc.stream;

import java.util.stream.LongStream;

public class ParallelStreamExample {

    public static void main(String[] args) {
        // 顺序流
        long startTime = System.nanoTime();
        long sum = LongStream.range(1, 1_000_000_000)
                             .sum();
        long endTime = System.nanoTime();
        System.out.println("顺序流耗时: " + (endTime - startTime) + " 纳秒");

        // 并行流
        startTime = System.nanoTime();
        sum = LongStream.range(1, 1_000_000_000)
                        .parallel()
                        .sum();
        endTime = System.nanoTime();
        System.out.println("并行流耗时: " + (endTime - startTime) + " 纳秒");
    }
}