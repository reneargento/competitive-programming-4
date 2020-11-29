package chapter1.section6.i.time.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/11/20.
 */
public class Semafori {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int trafficLights = scanner.nextInt();
        int roadLength = scanner.nextInt();

        int seconds = 0;
        int previousStop = 0;

        for (int i = 0; i < trafficLights; i++) {
            int distance = scanner.nextInt();
            int redTime = scanner.nextInt();
            int greenTime = scanner.nextInt();

            seconds += distance - previousStop;

            int nextGreenLightTime = seconds % (redTime + greenTime);
            if (nextGreenLightTime < redTime) {
                seconds += redTime - nextGreenLightTime;
            }

            previousStop = distance;
        }
        seconds += roadLength - previousStop;

        System.out.println(seconds);
    }
}
