package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class SecretResearch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            String result = scanner.next();

            if (result.equals("1") || result.equals("4") || result.equals("78")) {
                System.out.println("+");
            } else if (result.length() >= 3 && result.substring(result.length() - 2).equals("35")) {
                System.out.println("-");
            } else if (result.charAt(0) == '9' && result.charAt(result.length() - 1) == '4') {
                System.out.println("*");
            } else {
                System.out.println("?");
            }
        }
    }

}
