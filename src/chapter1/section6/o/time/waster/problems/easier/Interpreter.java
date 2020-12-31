package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/12/20.
 */
public class Interpreter {

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

            int instructionsExecuted = 0;
            int[] registers = new int[10];
            String[] ram = new String[1000];
            int ramIndex = 0;
            boolean halted = false;

            while (!instruction.isEmpty()) {
                ram[ramIndex++] = instruction;
                if (scanner.hasNext()) {
                    instruction = scanner.nextLine();
                } else {
                    break;
                }
            }

            int instructionIdToExecute = 0;
            while (!halted) {
                String instructionToExecute = getRamValue(ram, instructionIdToExecute);
                char operation = instructionToExecute.charAt(0);
                instructionsExecuted++;
                int digit2 = Character.getNumericValue(instructionToExecute.charAt(1));
                int digit3 = Character.getNumericValue(instructionToExecute.charAt(2));
                boolean moved = false;

                switch (operation) {
                    case '1': halted = true; break;
                    case '2': registers[digit2] = (digit3) % 1000; break;
                    case '3': registers[digit2] = (registers[digit2] + digit3) % 1000; break;
                    case '4': registers[digit2] = (registers[digit2] * digit3) % 1000; break;
                    case '5': registers[digit2] = registers[digit3]; break;
                    case '6': registers[digit2] = (registers[digit2] + registers[digit3]) % 1000; break;
                    case '7': registers[digit2] = (registers[digit2] * registers[digit3]) % 1000; break;
                    case '8': registers[digit2] = (Integer.parseInt(getRamValue(ram, registers[digit3]))) % 1000; break;
                    case '9': ram[registers[digit3]] = appendZeros(registers[digit2]); break;
                    default:
                        if (registers[digit3] != 0) {
                            instructionIdToExecute = registers[digit2];
                            moved = true;
                        }
                }
                if (!moved) {
                    instructionIdToExecute++;
                }
            }
            System.out.println(instructionsExecuted);

            if (scanner.hasNext()) {
                instruction = scanner.nextLine();
            }
        }
    }

    private static String getRamValue(String[] ram, int instructionIdToExecute) {
        String instructionToExecute = ram[instructionIdToExecute];
        if (instructionToExecute == null) {
            instructionToExecute = "000";
        }
        return instructionToExecute;
    }

    private static String appendZeros(int value) {
        String stringValue = String.valueOf(value);
        while (stringValue.length() != 3) {
            stringValue = "0" + stringValue;
        }
        return stringValue;
    }
}
