package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 07/11/20.
 */
public class Score {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scores = scanner.nextInt();
        int homeLeadingTime = 0;
        int awayLeadingTime = 0;

        int homePoints = 0;
        int awayPoints = 0;

        boolean isHomeLeading = false;
        boolean isAwayLeading = false;
        int previousTime = 0;

        for (int s = 0; s < scores; s++) {
            char team = scanner.next().charAt(0);
            int points = scanner.nextInt();
            String time = scanner.next();
            int timeInSeconds = getSeconds(time);
            int deltaTime = timeInSeconds - previousTime;

            if (team == 'H') {
                homePoints += points;
            } else {
                awayPoints += points;
            }

            if (homePoints > awayPoints) {
                if (isHomeLeading) {
                    homeLeadingTime += deltaTime;
                } else if (isAwayLeading) {
                    awayLeadingTime += deltaTime;
                    isAwayLeading = false;
                }
                isHomeLeading = true;
            } else if (awayPoints > homePoints) {
                if (isAwayLeading) {
                    awayLeadingTime += deltaTime;
                } else if (isHomeLeading) {
                    homeLeadingTime += deltaTime;
                    isHomeLeading = false;
                }
                isAwayLeading = true;
            } else {
                if (isHomeLeading) {
                    homeLeadingTime += deltaTime;
                } else if (isAwayLeading) {
                    awayLeadingTime += deltaTime;
                }
                isHomeLeading = false;
                isAwayLeading = false;
            }
            previousTime = timeInSeconds;
        }

        char winner = '-';
        int finalDelta = (32 * 60) - previousTime;
        if (homePoints > awayPoints) {
            homeLeadingTime += finalDelta;
            winner = 'H';
        } else if (awayPoints > homePoints) {
            awayLeadingTime += finalDelta;
            winner = 'A';
        }
        System.out.printf("%c %s %s\n", winner, getFormattedTime(homeLeadingTime), getFormattedTime(awayLeadingTime));
    }

    private static int getSeconds(String time) {
        String[] values = time.split(":");
        return 60 * Integer.parseInt(values[0]) + Integer.parseInt(values[1]);
    }

    private static String getFormattedTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }

}
