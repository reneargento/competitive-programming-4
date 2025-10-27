package chapter5.section4.section1;

/**
 * Created by Rene Argento on 25/10/25.
 */
public class Exercise1 {

    public static void main(String[] args) {
        long[] fibonacci = new long[1000];
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        for (int i = 2; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }

        boolean test7 = testFormula(7, fibonacci[7]);
        System.out.println(test7 ? "7: results are the same" : "7: results are not the same");

        boolean test9 = testFormula(9, fibonacci[9]);
        System.out.println(test9 ? "9: results are the same" : "9: results are not the same");

        boolean test11 = testFormula(11, fibonacci[11]);
        System.out.println(test11 ? "11: results are the same" : "11: results are not the same");

        for (int n = 12; n < fibonacci.length; n++) {
            if (!testFormula(n, fibonacci[n])) {
                System.out.println("\nThe first value of n where the results differ is " + n);
                break;
            }
        }
    }

    private static boolean testFormula(int n, long fibonacci) {
        long binetFormula = computeBinetFormula(n);
        return binetFormula == fibonacci;
    }

    private static long computeBinetFormula(long n) {
        double goldenRatio = (1.0 + Math.sqrt(5)) / 2;
        return (long) ((Math.pow(goldenRatio, n) - (Math.pow(-goldenRatio, -n))) / Math.sqrt(5));
    }
}
