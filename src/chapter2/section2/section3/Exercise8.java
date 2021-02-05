package chapter2.section2.section3;

import java.util.StringJoiner;

/**
 * Created by Rene Argento on 18/01/21.
 */
public class Exercise8 {

    public static void main(String[] args) {
        int[] greyCodes = { 0, 1, 3, 2, 6, 7, 5, 4 };
        System.out.println("Positions: " + getGreyCodePositions(greyCodes));
        System.out.println("Expected: 0 1 2 3 4 5 6 7");
    }

    private static String getGreyCodePositions(int[] greyCodes) {
        StringJoiner positions = new StringJoiner(" ");
        for (int greyCode : greyCodes) {
            int position = getGreyCodePosition(greyCode);
            positions.add(String.valueOf(position));
        }
        return positions.toString();
    }

    // Based on https://stackoverflow.com/questions/9617782/whats-the-reverse-function-of-x-xor-x-2
    private static int getGreyCodePosition(int greyCode) {
        int position = 0;
        while (greyCode > 0) {
            position ^= greyCode;
            greyCode >>= 1;
        }
        return position;
    }
}
