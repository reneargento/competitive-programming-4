package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 14/11/20.
 */
public class Tenis {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name1 = scanner.next();
        String name2 = scanner.next();
        int matches = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < matches; i++) {
            String[] sets = scanner.nextLine().split(" ");
            boolean valid = true;
            int[] playerWins = {0, 0};

            if (sets.length < 2 || sets.length > 3) {
                valid = false;
            } else {
                for (int s = 0; s < sets.length; s++) {
                    String set = sets[s];
                    int winner = getWinner(set, s, name1, name2);
                    if (winner == -1) {
                        valid = false;
                        break;
                    }
                    playerWins[winner]++;

                    if (s == 1 && playerWins[winner] == 2 && sets.length == 3) {
                        valid = false;
                        break;
                    }
                }
            }

            if (valid && (playerWins[0] == 2 || playerWins[1] == 2)) {
                System.out.println("da");
            } else {
                System.out.println("ne");
            }
        }
    }

    private static int getWinner(String set, int number, String name1, String name2) {
        String[] scores = set.split(":");
        int score1 = Integer.parseInt(scores[0]);
        int score2 = Integer.parseInt(scores[1]);

        if ((name1.equals("federer") && score1 < score2)
                || (name2.equals("federer") && score2 < score1)) {
            return -1;
        }

        if (number != 2) {
            if (score1 == 7 && score2 == 6) {
                return 0;
            } else if (score1 == 6 && score2 == 7) {
                return 1;
            } else if (score1 > 7 || score2 > 7) {
                return -1;
            }
        }

        int pointsDifference = Math.abs(score1 - score2);
        if (pointsDifference < 2) {
            return -1;
        }
        if ((score1 > 6 || score2 > 6) && pointsDifference != 2) {
            return -1;
        }

        if (score1 >= 6 && score1 > score2) {
            return 0;
        }
        if (score2 >= 6 && score2 > score1) {
            return 1;
        }
        return -1;
    }
}
