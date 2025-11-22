package chapter5.section4.section4;

/**
 * Created by Rene Argento on 20/11/25.
 */
public class Exercise8 {

    public static void main(String[] args) {
        long numberOfWays = numberOfWays(3, 2);
        System.out.println("Number of ways: " + numberOfWays);
        System.out.println("Expected: 10");
    }

    public static long numberOfWays(int boys, int girls) {
        long numberOfWays = 0;
        int minimum = Math.min(boys, girls);

        for (int i = 0; i <= minimum; i++) {
            long boysNumberOfWays = binomialCoefficient(boys, i);
            long girlsNumberOfWays = binomialCoefficient(girls, i);
            numberOfWays += boysNumberOfWays * girlsNumberOfWays;
        }
        return numberOfWays;
    }

    private static long binomialCoefficient(int totalNumbers, int numbersToChoose) {
        long result = 1;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result * (totalNumbers - i) / (i + 1);
        }
        return result;
    }
}
