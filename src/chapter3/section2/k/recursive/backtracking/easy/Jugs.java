package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Rene Argento on 02/02/22.
 */
public class Jugs {

    private static class State {
        int jugAContent;
        int jugBContent;

        public State(int jugAContent, int jugBContent) {
            this.jugAContent = jugAContent;
            this.jugBContent = jugBContent;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            State state = (State) other;
            return jugAContent == state.jugAContent && jugBContent == state.jugBContent;
        }

        @Override
        public int hashCode() {
            return Objects.hash(jugAContent, jugBContent);
        }
    }

    private enum Instruction {
        FILL_A, FILL_B, EMPTY_A, EMPTY_B, POUR_A_INTO_B, POUR_B_INTO_A
    }
    private static final Instruction[] instructionTypes = { Instruction.FILL_A, Instruction.FILL_B,
            Instruction.EMPTY_A, Instruction.EMPTY_B, Instruction.POUR_A_INTO_B,
            Instruction.POUR_B_INTO_A };
    private static int instructionsNumber;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Instruction[] instructions = new Instruction[10001];

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int jugACapacity = Integer.parseInt(data[0]);
            int jugBCapacity = Integer.parseInt(data[1]);
            int target = Integer.parseInt(data[2]);
            Set<State> statesVisited = new HashSet<>();

            instructionsNumber = 0;
            computeInstructions(jugACapacity, jugBCapacity, target, 0, 0,
                    instructions, statesVisited);

            for (int i = 0; i < instructionsNumber; i++) {
                String instruction;
                switch (instructions[i]) {
                    case FILL_A: instruction = "fill A"; break;
                    case FILL_B: instruction = "fill B"; break;
                    case EMPTY_A: instruction = "empty A"; break;
                    case EMPTY_B: instruction = "empty B"; break;
                    case POUR_A_INTO_B: instruction = "pour A B"; break;
                    default: instruction = "pour B A"; break;
                }
                outputWriter.printLine(instruction);
            }
            outputWriter.printLine("success");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean computeInstructions(int jugACapacity, int jugBCapacity, int target,
                                               int currentAVolume, int currentBVolume,
                                               Instruction[] instructions, Set<State> statesVisited) {
        State state = new State(currentAVolume, currentBVolume);
        if (statesVisited.contains(state)) {
            return false;
        }
        statesVisited.add(state);

        if (currentAVolume == target || currentBVolume == target) {
            return true;
        }

        for (Instruction instruction : instructionTypes) {
            instructions[instructionsNumber] = instruction;
            instructionsNumber++;
            int previousAVolume = currentAVolume;
            int previousBVolume = currentBVolume;

            switch (instruction) {
                case FILL_A: currentAVolume = jugACapacity; break;
                case FILL_B: currentBVolume = jugBCapacity; break;
                case EMPTY_A: currentAVolume = 0; break;
                case EMPTY_B: currentBVolume = 0; break;
                case POUR_A_INTO_B: int movedWater = Math.min(currentAVolume, jugBCapacity - currentBVolume);
                    currentBVolume += movedWater;
                    currentAVolume -= movedWater;
                    break;
                case POUR_B_INTO_A: movedWater = Math.min(currentBVolume, jugACapacity - currentAVolume);
                    currentAVolume += movedWater;
                    currentBVolume -= movedWater;
                    break;
            }

            boolean targetReached = computeInstructions(jugACapacity, jugBCapacity, target,
                    currentAVolume, currentBVolume, instructions, statesVisited);
            if (targetReached) {
                return true;
            } else {
                instructionsNumber--;
                currentAVolume = previousAVolume;
                currentBVolume = previousBVolume;
            }
        }
        return false;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
