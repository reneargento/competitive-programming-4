package chapter2.section2.section3;

/**
 * Created by Rene Argento on 18/01/21.
 */
public class Exercise4 {

    public static void main(String[] args) {
        long numberWithLast0On = turnOnLast0(41);
        System.out.println("Number with last bit 0 on: " + numberWithLast0On);
        System.out.println("Expected: 43");
    }

    private static long turnOnLast0(long number) {
        return number | (number + 1);
    }
}
