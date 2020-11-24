package chapter1.section6.d.games.other.harder;

import java.util.*;

/**
 * Created by Rene Argento on 29/10/20.
 */
public class ChutesAndLadders {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> trows = new ArrayList<>();

        int throwValue = scanner.nextInt();

        while (throwValue != 0) {
            trows.add(throwValue);
            throwValue = scanner.nextInt();
        }

        int players = scanner.nextInt();

        while (players != 0) {
            Map<Integer, Integer> squareTransport = new HashMap<>();
            Set<Integer> loseTurnSquares = new HashSet<>();
            Set<Integer> extraTurnSquares = new HashSet<>();

            int start = scanner.nextInt();
            int end = scanner.nextInt();

            while (start != 0 || end != 0) {
                squareTransport.put(start, end);

                start = scanner.nextInt();
                end = scanner.nextInt();
            }

            int specialSquare = scanner.nextInt();

            while (specialSquare != 0) {
                if (specialSquare < 0) {
                    loseTurnSquares.add(Math.abs(specialSquare));
                } else {
                    extraTurnSquares.add(specialSquare);
                }
                specialSquare = scanner.nextInt();
            }

            int turn = 0;
            int[] playerLocations = new int[2];
            int winner = -1;
            boolean extraTurn = false;

            for (int currentThrow : trows) {
                int nextLocation = playerLocations[turn] + currentThrow;
                if (squareTransport.containsKey(nextLocation)) {
                    nextLocation = squareTransport.get(nextLocation);
                }

                playerLocations[turn] = nextLocation;
                if (playerLocations[turn] == 100) {
                    winner = turn + 1;
                    break;
                }

                if (!extraTurnSquares.contains(nextLocation) && !extraTurn) {
                    turn = 1 - turn;
                }
                if (extraTurn) {
                    extraTurn = false;
                }

                if (loseTurnSquares.contains(nextLocation)) {
                    extraTurn = true;
                }
            }
            System.out.println(winner);

            players = scanner.nextInt();
        }
    }
}
