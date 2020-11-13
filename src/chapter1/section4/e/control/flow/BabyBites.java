package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class BabyBites {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int bites = scanner.nextInt();
        boolean makesSense = true;

        for (int i = 1; i <= bites; i++) {
            String word = scanner.next();
            if (!word.equals("mumble")) {
                int biteNumber = Integer.parseInt(word);
                if (biteNumber != i) {
                    makesSense = false;
                }
            }
        }
        System.out.println(makesSense ? "makes sense" : "something is fishy");
    }

}
