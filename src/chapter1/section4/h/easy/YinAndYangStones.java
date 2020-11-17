package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class YinAndYangStones {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String stones = scanner.next();
        int white = 0;
        int black = 0;

        for (int i = 0; i < stones.length(); i++) {
            if (stones.charAt(i) == 'W') {
                white++;
            } else {
                black++;
            }
        }
        System.out.println(white == black ? 1 : 0);
    }

}
