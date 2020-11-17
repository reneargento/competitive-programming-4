package chapter1.section4.h.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class RobotInstructions {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int instructions = scanner.nextInt();
            scanner.nextLine(); // Move to next line
            int position = 0;
            Map<Integer, String> pastInstructions = new HashMap<>();

            for (int i = 1; i <= instructions; i++) {
                String instruction = scanner.nextLine();

                switch (instruction) {
                    case "LEFT":
                        position--;
                        pastInstructions.put(i, "LEFT");
                        break;
                    case "RIGHT":
                        position++;
                        pastInstructions.put(i, "RIGHT");
                        break;
                    default:
                        int pastIndex = Integer.parseInt(instruction.split(" ")[2]);
                        String solvedInstruction = pastInstructions.get(pastIndex);

                        if (solvedInstruction.equals("LEFT")) {
                            position--;
                        } else {
                            position++;
                        }
                        pastInstructions.put(i, solvedInstruction);
                        break;
                }
            }
            System.out.println(position);
        }
    }

}
