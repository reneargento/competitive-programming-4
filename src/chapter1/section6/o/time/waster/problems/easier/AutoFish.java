package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/12/20.
 */
public class AutoFish {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        String instruction = scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }
            int fishCaught = 0;
            int baits = 0;
            int partialBaits = 0;

            int instructionsAfterLastFishWasCaught = 0;
            int fishInstructionsAfterLastFishWasCaught = 0;

            while (!instruction.isEmpty()) {
                instructionsAfterLastFishWasCaught++;
                if (instruction.equals("fish") && baits > 0) {
                    fishInstructionsAfterLastFishWasCaught++;
                }

                if (instruction.equals("bait")) {
                    if (baits != 3) {
                        partialBaits++;
                        if (partialBaits == 2) {
                            partialBaits = 0;
                            baits++;
                        }
                    }
                } else if (instruction.equals("fish")) {
                    if (baits > 0 &&
                            (fishCaught == 0
                            || (instructionsAfterLastFishWasCaught >= 7 && fishInstructionsAfterLastFishWasCaught >= 3))) {
                        fishCaught++;
                        baits--;
                        instructionsAfterLastFishWasCaught = 0;
                        fishInstructionsAfterLastFishWasCaught = 0;
                    }
                }

                if (scanner.hasNext()) {
                    instruction = scanner.nextLine();
                } else {
                    break;
                }
            }
            System.out.println(fishCaught);

            if (scanner.hasNext()) {
                instruction = scanner.nextLine();
            }
        }
    }
}
