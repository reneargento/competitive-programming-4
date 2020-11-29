package chapter1.section6.i.time.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/11/20.
 */
public class ExtraordinarilyLargeLED {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int homeScore = 0;
        int guestScore = 0;
        int previousTime = 0;
        int totalPowerConsumed = 0;
        int caseNumber = 1;

        while (scanner.hasNext()) {
            String[] data = scanner.nextLine().split(" ");
            String event = data[0];
            int seconds = getSeconds(data[1]);

            if (event.equals("START")) {
                previousTime = seconds;
            } else {
                int currentTime = getSeconds(data[1]);
                int deltaSeconds = currentTime - previousTime;
                int powerConsumption = getPowerConsumption(homeScore) + getPowerConsumption(guestScore);
                totalPowerConsumed += powerConsumption * deltaSeconds;

                if (event.equals("SCORE")) {
                    String team = data[2];
                    int score = Integer.parseInt(data[3]);
                    if (team.equals("home")) {
                        homeScore += score;
                    } else {
                        guestScore += score;
                    }
                    previousTime = currentTime;
                } else {
                    System.out.printf("Case %d: %d\n", caseNumber, totalPowerConsumed);
                    homeScore = 0;
                    guestScore = 0;
                    previousTime = 0;
                    totalPowerConsumed = 0;
                    caseNumber++;
                }
            }
        }
    }

    private static int getSeconds(String time) {
        String[] timeData = time.split(":");
        int hours = Integer.parseInt(timeData[0]);
        int minutes = Integer.parseInt(timeData[1]);
        int seconds = Integer.parseInt(timeData[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }

    private static int getPowerConsumption(int score) {
        String digits = String.valueOf(score);
        int powerConsumption = 0;

        for (char digit : digits.toCharArray()) {
            switch (digit) {
                case '0': powerConsumption += 6; break;
                case '1': powerConsumption += 2; break;
                case '2': powerConsumption += 5; break;
                case '3': powerConsumption += 5; break;
                case '4': powerConsumption += 4; break;
                case '5': powerConsumption += 5; break;
                case '6': powerConsumption += 6; break;
                case '7': powerConsumption += 3; break;
                case '8': powerConsumption += 7; break;
                case '9': powerConsumption += 6; break;
            }
        }
        return powerConsumption;
    }
}
