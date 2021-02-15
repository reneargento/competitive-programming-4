package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class BurgerStand {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int possibleSpaces = 0;

            String road = scanner.next();
            for (int i = 0; i < road.length(); i++) {
                if (road.charAt(i) == '-') {
                    if ((i > 0 && road.charAt(i - 1) == 'S')
                            || (i < road.length() - 1 && (road.charAt(i + 1) == 'S' || road.charAt(i + 1) == 'B'))
                            || (i < road.length() - 2 && road.charAt(i + 2) == 'B')) {
                        continue;
                    }
                    possibleSpaces++;
                }
            }
            System.out.printf("Case %d: %d\n", t, possibleSpaces);
        }
    }
}
