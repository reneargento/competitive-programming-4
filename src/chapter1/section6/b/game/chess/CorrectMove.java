package chapter1.section6.b.game.chess;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 13/10/20.
 */
public class CorrectMove {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int kingPlace = scanner.nextInt();
            int queenPlace = scanner.nextInt();
            int queenEndPlace = scanner.nextInt();

            if (kingPlace == queenPlace) {
                System.out.println("Illegal state");
            } else {
                Set<Integer> validMoves = getValidMoves(queenPlace, kingPlace);
                if (!validMoves.contains(queenEndPlace)) {
                    System.out.println("Illegal move");
                } else {
                    if ((queenEndPlace == kingPlace + 1 && !isMultipleOf8(queenEndPlace))
                            || (queenEndPlace == kingPlace - 1 && !isMultipleOf8(kingPlace))
                            || queenEndPlace == kingPlace + 8
                            || queenEndPlace == kingPlace - 8) {
                        System.out.println("Move not allowed");
                    } else {
                        if ((kingPlace == 0 && queenEndPlace == 9)
                                || (kingPlace == 7 && queenEndPlace == 14)
                                || (kingPlace == 56 && queenEndPlace == 49)
                                || (kingPlace == 63 && queenEndPlace == 54)) {
                            System.out.println("Stop");
                        } else {
                            System.out.println("Continue");
                        }
                    }
                }
            }
        }
    }

    private static Set<Integer> getValidMoves(int queenPlace, int kingPlace) {
        Set<Integer> validMoves = new HashSet<>();

        int multipleOf8 = queenPlace / 8;
        int min = multipleOf8 * 8;
        int max = (min + 8) - 1;

        // left
        for (int i = queenPlace - 1; i >= min; i--) {
            if (i == kingPlace) {
                break;
            }
            validMoves.add(i);
        }
        // right
        for (int i = queenPlace + 1; i <= max; i++) {
            if (i == kingPlace) {
                break;
            }
            validMoves.add(i);
        }
        // up
        for (int i = queenPlace - 8; i >= 0; i -= 8) {
            if (i == kingPlace) {
                break;
            }
            validMoves.add(i);
        }
        // down
        for (int i = queenPlace + 8; i <= 63; i += 8) {
            if (i == kingPlace) {
                break;
            }
            validMoves.add(i);
        }

        return validMoves;
    }

    private static boolean isMultipleOf8(int number) {
        int divisionResult = number / 8;
        return (divisionResult * 8) == number;
    }
}
