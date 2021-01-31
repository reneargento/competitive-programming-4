package chapter2.section2.section3;

/**
 * Created by Rene Argento on 18/01/21.
 */
public class Exercise3 {

    public static void main(String[] args) {
        long numberWithLast1Off = turnOffLast1(40);
        System.out.println("Number with last bit 1 off: " + numberWithLast1Off);
        System.out.println("Expected: 32");
    }

    private static long turnOffLast1(long number) {
        return number & (number - 1);
    }
}
