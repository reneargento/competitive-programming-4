package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/12/20.
 */
public class AverageSpeed {

    private static class Time {
        int seconds;
        int minutes;
        int hours;

        public Time(int seconds, int minutes, int hours) {
            this.seconds = seconds;
            this.minutes = minutes;
            this.hours = hours;
        }

        public Time(String time) {
            hours = Integer.parseInt(time.substring(0, 2));
            minutes = Integer.parseInt(time.substring(3, 5));
            seconds = Integer.parseInt(time.substring(6));
        }

        private double deltaTime(Time previousTime) {
            return (hours - previousTime.hours)
                    + ((minutes - previousTime.minutes) / 60.0)
                    + ((seconds - previousTime.seconds) / 3600.0);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double distanceTravelled = 0;
        Time previousTime = new Time(0, 0, 0);
        int speed = 0;

        while (scanner.hasNext()) {
            String[] input = scanner.nextLine().split(" ");
            Time time = new Time(input[0]);
            distanceTravelled += getDistanceTravelled(previousTime, time, speed);

            if (input.length == 1) {
                System.out.printf("%s %.2f km\n", input[0], distanceTravelled);
            } else {
                speed = Integer.parseInt(input[1]);
            }
            previousTime = time;
        }
    }

    private static double getDistanceTravelled(Time startTime, Time endTime, int speed) {
        return endTime.deltaTime(startTime) * speed;
    }
}
