package chapter1.section6.c.games.other.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 17/10/20.
 */
public class SnakesAndLadders {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int players = scanner.nextInt();
            int ladderSnakes = scanner.nextInt();
            int rolls = scanner.nextInt();

            Map<Integer, Integer> ladderSnakeMap = new HashMap<>();
            for (int i = 0; i < ladderSnakes; i++) {
                ladderSnakeMap.put(scanner.nextInt() - 1, scanner.nextInt() - 1);
            }

            int[] positions = new int[players];
            int playerId = 0;
            boolean gameOver = false;

            for (int i = 0; i < rolls; i++) {
                if (gameOver) {
                    scanner.nextInt();
                    continue;
                }

                int newPosition = positions[playerId] + scanner.nextInt();

                if (ladderSnakeMap.containsKey(newPosition)) {
                    newPosition = ladderSnakeMap.get(newPosition);
                }
                positions[playerId] = Math.min(newPosition, 99);

                if (positions[playerId] == 99) {
                    gameOver = true;
                }
                playerId++;
                playerId %= players;
            }

            for (int i = 0; i < positions.length; i++) {
                System.out.printf("Position of player %d is %d.\n", i + 1, positions[i] + 1);
            }
        }
    }

}
