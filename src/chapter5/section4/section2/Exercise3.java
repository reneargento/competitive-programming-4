package chapter5.section4.section2;

/**
 * Created by Rene Argento on 26/10/25.
 */
public class Exercise3 {

    private static final int MAX_N = 100010;
    private static final int prime = 1000000007;
    private static final long[] factorials = new long[MAX_N];
    private static final long[] modPowFermat = new long[MAX_N];

    public static void main(String[] args) {
        factorials[0] = 1;
        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = (factorials[i - 1] * i) % prime;
        }

        modPowFermat[modPowFermat.length - 1] = modPowFermat(factorials[factorials.length - 1]);
        for (int i = modPowFermat.length - 2; i >= 0; i--) {
            modPowFermat[i] = (modPowFermat[i + 1] * (i + 1)) % prime;
        }

        System.out.println("Result: " + binomialCoefficient(100000, 50000));
        System.out.println("Expected: 149033233");
    }

    private static long modPowFermat(long value) {
        return modPow(value, prime - 2, prime);
    }

    private static long binomialCoefficient(int n, int k) {
        if (n < k) {
            return 0;
        }
        return (((factorials[n] * modPowFermat[k]) % prime) * modPowFermat[n - k]) % prime;
    }

    private static long modPow(long base, long power, long mod) {
        if (power == 0) {
            return 1;
        }
        long result = modPow(base, power / 2, mod);
        result = mod(result * result, mod);
        if (power % 2 == 1) {
            result = mod(result * base, mod);
        }
        return result;
    }

    private static long mod(long number, long mod) {
        return ((number % mod) + mod) % mod;
    }
}
