package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/12/20.
 */
public class Banglawash {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int matches = scanner.nextInt();
            String results = scanner.next();
            int bangladeshWins = 0;
            int wwwWins = 0;
            int ties = 0;
            int abandonedMatches = 0;

            for (char result : results.toCharArray()) {
                switch (result) {
                    case 'B': bangladeshWins++; break;
                    case 'W': wwwWins++; break;
                    case 'T': ties++; break;
                    default: abandonedMatches++;
                }
            }

            System.out.printf("Case %d: ", t);
            if (bangladeshWins == (matches - abandonedMatches) && bangladeshWins != 0) {
                System.out.println("BANGLAWASH");
            } else if (wwwWins == (matches - abandonedMatches) && wwwWins != 0) {
                System.out.println("WHITEWASH");
            } else if (abandonedMatches == matches) {
                System.out.println("ABANDONED");
            } else if (bangladeshWins > wwwWins) {
                System.out.printf("BANGLADESH %d - %d\n", bangladeshWins, wwwWins);
            } else if (wwwWins > bangladeshWins) {
                System.out.printf("WWW %d - %d\n", wwwWins, bangladeshWins);
            } else {
                System.out.printf("DRAW %d %d\n", bangladeshWins, ties);
            }
        }
    }
}
