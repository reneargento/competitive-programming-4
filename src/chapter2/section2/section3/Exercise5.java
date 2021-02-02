package chapter2.section2.section3;

/**
 * Created by Rene Argento on 18/01/21.
 */
public class Exercise5 {

    public static void main(String[] args) {
        long numberWithLastConsecutiveSetBitsOff = turnOffLastConsecutiveSetBits(39);
        System.out.println("Number with last consecutive set bits off: " + numberWithLastConsecutiveSetBitsOff);
        System.out.println("Expected: 32");
    }

    private static long turnOffLastConsecutiveSetBits(long number) {
        return number & (number + 1);
    }
}
