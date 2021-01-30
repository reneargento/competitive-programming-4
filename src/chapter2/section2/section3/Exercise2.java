package chapter2.section2.section3;

/**
 * Created by Rene Argento on 18/01/21.
 */
public class Exercise2 {

    public static void main(String[] args) {
        boolean isPowerOf2a = isPowerOf2(7);
        System.out.println("Is power of 2: " + isPowerOf2a + " Expected: false");

        boolean isPowerOf2b = isPowerOf2(8);
        System.out.println("Is power of 2: " + isPowerOf2b + " Expected: true");
    }

    private static boolean isPowerOf2(long number) {
        return (number & (number - 1)) == 0;
    }
}
