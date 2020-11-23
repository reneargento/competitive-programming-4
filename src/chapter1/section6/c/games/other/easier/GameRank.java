package chapter1.section6.c.games.other.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 17/10/20.
 */
public class GameRank {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String matches = scanner.next();

        int rank = 25;
        int stars = 0;
        int consecutiveWins = 0;

        for (int i = 0; i < matches.length(); i++) {
            char result = matches.charAt(i);
            if (result == 'W') {
                consecutiveWins++;
                stars++;

                if (rank >= 6 && rank <= 25 && consecutiveWins >= 3) {
                    stars++;
                }

                if (rank >= 21) {
                    if (stars == 4) {
                        rank--;
                        stars = 2;
                    } else if (stars == 3) {
                        rank--;
                        stars = 1;
                    }
                } else if (rank >= 16) {
                    if (stars == 5) {
                        rank--;
                        stars = 2;
                    } else if (stars == 4) {
                        rank--;
                        stars = 1;
                    }
                } else if (rank >= 11) {
                    if (stars == 6) {
                        rank--;
                        stars = 2;
                    } else if (stars == 5) {
                        rank--;
                        stars = 1;
                    }
                } else if (rank >= 1) {
                    if (stars == 7) {
                        rank--;
                        stars = 2;
                    } else if (stars == 6) {
                        rank--;
                        stars = 1;
                    }
                }
            } else {
                consecutiveWins = 0;
                if (rank >= 1 && rank <= 20) {
                    if (stars > 0) {
                        stars--;
                    } else {
                        if (rank < 20) {
                            rank++;
                            if (rank <= 10) {
                                stars = 4;
                            } else if (rank <= 15) {
                                stars = 3;
                            } else if (rank <= 20) {
                                stars = 2;
                            }
                        }
                    }
                }
            }
        }

        if (rank == 0) {
            System.out.println("Legend");
        } else {
            System.out.println(rank);
        }
    }

}
