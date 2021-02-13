package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class GreedlyIncreasingSubsequence {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfElements = scanner.nextInt();
        int[] elements = new int[numberOfElements];

        for (int i = 0; i < elements.length; i++) {
            elements[i] = scanner.nextInt();
        }

        List<Integer> gis = new ArrayList<>();
        gis.add(elements[0]);
        int currentNumber = elements[0];

        for (int i = 1; i < elements.length; i++) {
            if (elements[i] > currentNumber) {
                gis.add(elements[i]);
                currentNumber = elements[i];
            }
        }
        printGis(gis);
    }

    private static void printGis(List<Integer> gis) {
        System.out.println(gis.size());
        StringJoiner gisElements = new StringJoiner(" ");
        for (int value : gis) {
            gisElements.add(String.valueOf(value));
        }
        System.out.println(gisElements);
    }
}
