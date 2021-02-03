package chapter2.section2.section3;

/**
 * Created by Rene Argento on 18/01/21.
 */
public class Exercise6 {

    public static void main(String[] args) {
        long numberWithLastConsecutiveUnsetBitsOn = turnOnLastConsecutiveUnsetBits(36);
        System.out.println("Number with last consecutive unset bits on: " + numberWithLastConsecutiveUnsetBitsOn);
        System.out.println("Expected: 39");
    }

    private static long turnOnLastConsecutiveUnsetBits(long number) {
        return number | (number - 1);
    }
}
