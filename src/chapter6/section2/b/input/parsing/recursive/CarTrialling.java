package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 03/05/26.
 */
public class CarTrialling {

    private static class SWordResult {
        int endIndex;
        boolean signFinished;

        public SWordResult(int endIndex, boolean signFinished) {
            this.endIndex = endIndex;
            this.signFinished = signFinished;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int lineId = 1;
        while (!line.equals("#")) {
            List<String> result = validateInstructions(line);
            outputWriter.print(String.format("%3d.", lineId));
            if (result == null) {
                outputWriter.printLine(" Trap!");
            } else {
                boolean isInsideSign = false;
                for (int i = 0; i < result.size(); i++) {
                    String instruction = result.get(i);
                    if (i == 0
                            || (!result.get(i - 1).equals("\"")
                                 && !(isInsideSign && result.get(i).equals("\"")))) {
                        outputWriter.print(" ");
                    }
                    outputWriter.print(instruction);
                    if (instruction.contains("\"")) {
                        isInsideSign = !isInsideSign;
                    }
                }
                outputWriter.printLine();
            }
            lineId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> validateInstructions(String line) {
        List<String> words = getWords(line);
        if (hasEmptyString(words)) {
            return null;
        }
        String[] instructions = words.toArray(new String[words.size()]);
        boolean valid = validateInstruction(instructions);
        return valid ? words : null;
    }

    private static boolean validateInstruction(String[] instructions) {
        int timeKeepingResult = parseTimeKeeping(instructions, 0);
        if (timeKeepingResult == instructions.length) {
            return true;
        }
        int navigationalResult = parseNavigational(instructions, 0);
        if (navigationalResult == instructions.length) {
            return true;
        }
        if (navigationalResult == -1) {
            return false;
        }
        if (!instructions[navigationalResult].equals("AND")) {
            return false;
        }
        int timeKeepingResult2 = parseTimeKeeping(instructions, navigationalResult + 1);
        return (timeKeepingResult2 == instructions.length);
    }

    private static int parseNavigational(String[] instructions, int index) {
        int directionalResult = parseDirectional(instructions, index);
        if (directionalResult == -1) {
            return -1;
        }

        while (directionalResult < instructions.length - 1
                && instructions[directionalResult].equals("AND")
                && instructions[directionalResult + 1].equals("THEN")) {
            directionalResult = parseDirectional(instructions, directionalResult + 2);
            if (directionalResult == -1) {
                return -1;
            }
        }
        return directionalResult;
    }

    private static int parseDirectional(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        int howResult = parseHow(instructions, index);
        if (howResult == -1) {
            return -1;
        }
        int directionResult = parseDirection(instructions, howResult);
        if (directionResult == -1) {
            return -1;
        }
        int whereResult = parseWhere(instructions, directionResult);
        if (whereResult != -1) {
            return whereResult;
        }
        return directionResult;
    }

    private static int parseHow(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        if (instructions[index].equals("KEEP")) {
            return index + 1;
        } else if (instructions[index].equals("GO")) {
            int whenResult = parseWhen(instructions, index + 1);
            if (whenResult != -1) {
                return whenResult;
            }
            return index + 1;
        }
        return -1;
    }

    private static int parseDirection(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        if (instructions[index].equals("RIGHT")
                || instructions[index].equals("LEFT")) {
            return index + 1;
        }
        return -1;
    }

    private static int parseWhen(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        if (instructions[index].equals("FIRST")
                || instructions[index].equals("SECOND")
                || instructions[index].equals("THIRD")) {
            return index + 1;
        }
        return -1;
    }

    private static int parseWhere(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        if (!instructions[index].equals("AT")) {
            return -1;
        }
        return parseSign(instructions, index + 1);
    }

    private static int parseSign(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        SWordResult signResult = parseSignWords(instructions, index, true);
        if (signResult.endIndex != -1 && signResult.signFinished) {
            return signResult.endIndex;
        }
        return -1;
    }

    private static SWordResult parseSignWords(String[] instructions, int index, boolean isStart) {
        if (index >= instructions.length) {
            return new SWordResult(-1, true);
        }
        SWordResult sWordResult = parseSWord(instructions, index, isStart);
        if (sWordResult.endIndex == -1) {
            return new SWordResult(-1, true);
        }
        if (sWordResult.signFinished) {
            return sWordResult;
        }

        SWordResult sWordNextResult = parseSignWords(instructions, index + 1, false);
        if (sWordNextResult.endIndex != -1) {
            return sWordNextResult;
        }
        return new SWordResult(index + 1, false);
    }

    private static SWordResult parseSWord(String[] instructions, int index, boolean quotesStartOk) {
        if (index >= instructions.length || instructions[index].equals("\"\"")) {
            return new SWordResult(-1, false);
        }
        for (int i = 0; i < instructions[index].length(); i++) {
            char symbol = instructions[index].charAt(i);
            if (i == 0 && symbol ==  '"' && quotesStartOk) {
                continue;
            }
            if (i == instructions[index].length() - 1 && symbol == '"') {
                return new SWordResult(index + 1, true);
            }
            if (symbol != '.'
                    && (Character.isLowerCase(symbol)
                    || !Character.isLetter(symbol))) {
                return new SWordResult(-1, false);
            }
        }
        return new SWordResult(index + 1, false);
    }

    private static int parseTimeKeeping(String[] instructions, int index) {
        int recordResult = parseRecord(instructions, index);
        if (recordResult != -1) {
            return recordResult;
        }
        return parseChange(instructions, index);
    }

    private static int parseRecord(String[] instructions, int index) {
        if (index >= instructions.length - 1) {
            return -1;
        }
        if (instructions[index].equals("RECORD")
                && instructions[index + 1].equals("TIME")) {
            return index + 2;
        }
        return -1;
    }

    private static int parseChange(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        int casResult = parseCas(instructions, index);
        if (casResult == -1) {
            return -1;
        }
        if (casResult >= instructions.length || !instructions[casResult].equals("TO")) {
            return -1;
        }
        int nnnResult = parseNnn(instructions, casResult + 1);
        if (nnnResult == -1) {
            return -1;
        }
        if (nnnResult >= instructions.length || !instructions[nnnResult].equals("KMH")) {
            return -1;
        }
        return nnnResult + 1;
    }

    private static int parseCas(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        if (instructions[index].equals("CAS")) {
            return index + 1;
        }
        if (index <= instructions.length - 3
                && instructions[index].equals("CHANGE")
                && instructions[index + 1].equals("AVERAGE")
                && instructions[index + 2].equals("SPEED")) {
            return index + 3;
        }
        return -1;
    }

    private static int parseNnn(String[] instructions, int index) {
        if (index >= instructions.length) {
            return -1;
        }
        for (char symbol : instructions[index].toCharArray()) {
            if (!Character.isDigit(symbol)) {
                return -1;
            }
        }
        return index + 1;
    }

    private static boolean hasEmptyString(List<String> instructions) {
        for (int i = 1; i < instructions.size(); i++) {
            if (instructions.get(i - 1).equals("\"")
                    && instructions.get(i).equals("\"")) {
                return true;
            }
        }
        return false;
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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

        public void flush() {
            writer.flush();
        }
    }
}
