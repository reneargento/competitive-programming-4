package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 17/12/20.
 */
public class GlitchBot {

    private enum Direction {
        NORTH, SOUTH, EAST, WEST;

        private Direction turnLeft() {
            switch (this) {
                case NORTH: return WEST;
                case WEST: return SOUTH;
                case SOUTH: return EAST;
                default: return NORTH;
            }
        }

        private Direction turnRight() {
            switch (this) {
                case NORTH: return EAST;
                case EAST: return SOUTH;
                case SOUTH: return WEST;
                default: return NORTH;
            }
        }
    }

    private static class ReplacedInstruction {
        int index;
        String instruction;

        public ReplacedInstruction(int index, String instruction) {
            this.index = index;
            this.instruction = instruction;
        }
    }

    private static final String LEFT_INSTRUCTION = "Left";
    private static final String RIGHT_INSTRUCTION = "Right";
    private static final String FORWARD_INSTRUCTION = "Forward";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int targetColumn = scanner.nextInt();
        int targetRow = scanner.nextInt();
        int instructionsCount = scanner.nextInt();

        String[] instructions = new String[instructionsCount];

        for (int i = 0; i < instructionsCount; i++) {
            instructions[i] = scanner.next();
        }

        for (int i = 0; i < instructionsCount; i++) {
            String instruction = instructions[i];
            String newInstruction1;
            String newInstruction2;

            if (instruction.equals(LEFT_INSTRUCTION)) {
                newInstruction1 = RIGHT_INSTRUCTION;
                newInstruction2 = FORWARD_INSTRUCTION;
            } else if (instruction.equals(RIGHT_INSTRUCTION)) {
                newInstruction1 = LEFT_INSTRUCTION;
                newInstruction2 = FORWARD_INSTRUCTION;
            } else {
                newInstruction1 = LEFT_INSTRUCTION;
                newInstruction2 = RIGHT_INSTRUCTION;
            }

            ReplacedInstruction replacedInstruction1 = new ReplacedInstruction(i, newInstruction1);
            ReplacedInstruction replacedInstruction2 = new ReplacedInstruction(i, newInstruction2);
            boolean arrivesAtDestination1 = arrivesAtDestination(instructions, targetRow, targetColumn, replacedInstruction1);
            if (arrivesAtDestination1) {
                System.out.printf("%d %s\n", i + 1, replacedInstruction1.instruction);
                break;
            }
            boolean arrivesAtDestination2 = arrivesAtDestination(instructions, targetRow, targetColumn, replacedInstruction2);
            if (arrivesAtDestination2) {
                System.out.printf("%d %s\n", i + 1, replacedInstruction2.instruction);
                break;
            }
        }
    }

    private static boolean arrivesAtDestination(String[] instructions, int targetRow, int targetColumn,
                                                ReplacedInstruction replacedInstruction) {
        int row = 0;
        int column = 0;
        Direction direction = Direction.NORTH;

        for (int i = 0; i < instructions.length; i++) {
            String instruction = instructions[i];
            if (i == replacedInstruction.index) {
                instruction = replacedInstruction.instruction;
            }

            if (instruction.equals(LEFT_INSTRUCTION)) {
                direction = direction.turnLeft();
            } else if (instruction.equals(RIGHT_INSTRUCTION)) {
                direction = direction.turnRight();
            } else {
                switch (direction) {
                    case NORTH: row++; break;
                    case WEST: column--; break;
                    case SOUTH: row--; break;
                    case EAST: column++;
                }
            }
        }
        return row == targetRow && column == targetColumn;
    }
}
