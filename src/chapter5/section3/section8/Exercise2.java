package chapter5.section3.section8;

/**
 * Created by Rene Argento on 14/05/25.
 */
public class Exercise2 {

    public static int countTrailingZeros(int number) {
        int factors5 = 0;

        for (int n = 5; n <= number; n++) {
            int multiple = n;

            while (multiple % 5 == 0) {
                multiple /= 5;
                factors5++;
            }
        }
        return factors5;
    }

    public static void main(String[] args) {
        int trailingZeros1 = countTrailingZeros(12);
        System.out.println("Trailing zeros: " + trailingZeros1);
        System.out.println("Expected: 2");

        int trailingZeros2 = countTrailingZeros(15);
        System.out.println("\nTrailing zeros: " + trailingZeros2);
        System.out.println("Expected: 3");
    }
}
