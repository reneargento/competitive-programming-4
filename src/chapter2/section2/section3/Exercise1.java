package chapter2.section2.section3;

/**
 * Created by Rene Argento on 18/01/21.
 */
public class Exercise1 {

    public static void main(String[] args) {
        long remainder1 = remainderByPowerOf2(7, 4);
        System.out.println("Remainder: " + remainder1 + " Expected: 3");

        long remainder2 = remainderByPowerOf2(10, 4);
        System.out.println("Remainder: " + remainder2 + " Expected: 2");

        long remainder3 = remainderByPowerOf2(357, 16);
        System.out.println("Remainder: " + remainder3 + " Expected: 5");
    }

    private static long remainderByPowerOf2(long number, long powerOf2) {
        return number & (powerOf2 - 1);
    }
}
