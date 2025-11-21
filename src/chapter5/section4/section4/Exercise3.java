package chapter5.section4.section4;

/**
 * Created by Rene Argento on 29/10/25.
 */
public class Exercise3 {

    public static void main(String[] args) {
        long possiblePasswords = 0;
        int mod = 1000000007;
        long possibleChars = 62;

        for (int i = 1; i <= 10; i++) {
            possiblePasswords += possibleChars;
            possibleChars *= 62;
            possiblePasswords %= mod;
        }
        System.out.println("Possible passwords: " + possiblePasswords);
    }
}
