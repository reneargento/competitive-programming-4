package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/09/20.
 */
public class TrainTracks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            String[] pieces = scanner.nextLine().split(" ");
            int male = 0;
            int female = 0;

            for (String piece : pieces) {
                if (piece.charAt(0) == 'M') {
                    male++;
                } else {
                    female++;
                }

                if (piece.charAt(1) == 'M') {
                    male++;
                } else {
                    female++;
                }
            }

            System.out.println(male == female && pieces.length > 1 ? "LOOP" : "NO LOOP");
        }
    }

}
