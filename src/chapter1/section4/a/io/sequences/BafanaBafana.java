package chapter1.section4.a.io.sequences;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class BafanaBafana {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 1; i <= tests; i++) {
            int players = scanner.nextInt();
            int initialPlayer = scanner.nextInt();
            int passes = scanner.nextInt();

            int finalPlayer = (initialPlayer + passes) % players;
            if (finalPlayer == 0) {
                finalPlayer = players;
            }
            System.out.println("Case " + i + ": " + finalPlayer);
        }
    }
}
