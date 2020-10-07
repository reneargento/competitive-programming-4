package chapter1.section4.b.repetition;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class SearchingForNessy {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int rows = scanner.nextInt() - 2;
            double columns = scanner.nextInt() - 2;

            int vertical = (int) Math.ceil(rows / 3.0);
            int horizontal = (int) Math.ceil(columns / 3.0);

            System.out.println(vertical * horizontal);
        }
    }

}
