package ru.netology.task2;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

/**
 * Tax report
 *
 */
public class App {
    private static final int LENGTH_OF_ARRAY = 1_000;
    private static final int SEED = 10_000;

    public static void main(String[] args) {
        int[] proceeds1 = getRandomArray(LENGTH_OF_ARRAY, SEED);
        int[] proceeds2 = getRandomArray(LENGTH_OF_ARRAY, SEED);
        int[] proceeds3 = getRandomArray(LENGTH_OF_ARRAY, SEED);

        LongAdder stat = new LongAdder();

        Thread threadShop1 =
                new Thread(() -> Arrays.stream(proceeds1).forEach(stat::add), "Магазин1");
        Thread threadShop2 =
                new Thread(() -> Arrays.stream(proceeds2).forEach(stat::add), "Магазин2");
        Thread threadShop3 =
                new Thread(() -> Arrays.stream(proceeds3).forEach(stat::add), "Магазин3");

        threadShop1.start();
        threadShop2.start();
        threadShop3.start();

        try {
            threadShop1.join();
            threadShop2.join();
            threadShop3.join();
        } catch (InterruptedException ignored) { }

        System.out.println("Выручка всех магазинов: " + stat.sum());
    }

    private static int[] getRandomArray(int length, int seed) {
        int[] array = new int[length];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(seed);
        }

        return array;
    }
}
