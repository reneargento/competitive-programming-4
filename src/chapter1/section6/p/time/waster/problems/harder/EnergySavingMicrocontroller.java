package chapter1.section6.p.time.waster.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/12/20.
 */
public class EnergySavingMicrocontroller {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scenarios = scanner.nextInt();

        for (int s = 1; s <= scenarios; s++) {
            int instructions = scanner.nextInt();
            int timeToGoIdle = scanner.nextInt();
            int timeToActivation = scanner.nextInt();

            int timesGoneInactive = 0;
            int instructionsIgnored = 0;
            int timestampActive = 0;

            for (int i = 0; i < instructions; i++) {
                int instructionTime = scanner.nextInt();

                if (instructionTime < timestampActive) {
                    instructionsIgnored++;
                } else if (instructionTime - timestampActive >= timeToGoIdle) {
                    timesGoneInactive++;
                    timestampActive = instructionTime + timeToActivation;
                } else {
                    timestampActive = instructionTime;
                }
            }
            System.out.printf("Case %d: %d %d\n", s, timesGoneInactive, instructionsIgnored);
        }
    }
}
