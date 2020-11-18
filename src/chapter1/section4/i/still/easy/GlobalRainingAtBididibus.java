package chapter1.section4.i.still.easy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * Created by Rene Argento on 18/09/20.
 */
public class GlobalRainingAtBididibus {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            String universe = scanner.next();

            Deque<Integer> startPositions = new ArrayDeque<>();
            int unitsOfWater = 0;

            for (int i = 0; i < universe.length(); i++) {
                char symbol = universe.charAt(i);

                if (symbol == '\\') {
                    startPositions.push(i);
                } else if (symbol == '/' && !startPositions.isEmpty()) {
                    int startPosition = startPositions.pop();
                    unitsOfWater += i - startPosition;
                }
            }
            System.out.println(unitsOfWater);
        }
    }

}
