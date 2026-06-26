package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 25/06/2026.
 */
public class BrokenKeyboard {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        printHeader(outputWriter);
        int keyboardNumber = 1;
        while (!line.equals("finish")) {
            String brokenKeys = line.toLowerCase();
            List<String> text = new ArrayList<>();
            line = FastReader.getLine();

            while (!line.equals("END")) {
                text.add(line.toLowerCase());
                line = FastReader.getLine();
            }
            text.add(line.toLowerCase());
            printTable(brokenKeys, text, keyboardNumber, outputWriter);

            keyboardNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printHeader(OutputWriter outputWriter) {
        printTableDivider(outputWriter);
        outputWriter.printLine("| Keyboard | # of printable | Additionally, the following |");
        outputWriter.printLine("|          |      lines     |  letter keys can be broken  |");
        printTableDivider(outputWriter);
    }

    private static void printTableDivider(OutputWriter outputWriter) {
        outputWriter.printLine("+----------+----------------+-----------------------------+");
    }

    private static void printTable(String brokenKeys, List<String> text, int keyboardNumber, OutputWriter outputWriter) {
        Set<Character> keysUsedInLines = new HashSet<>();
        Set<Character> brokenKeySet = new HashSet<>();
        for (char key : brokenKeys.toCharArray()) {
            brokenKeySet.add(key);
        }

        int printableLines = computePrintableLines(brokenKeySet, text, keysUsedInLines);
        String lettersThatCanBeBroken = computeLettersThatCanBeBroken(brokenKeySet, keysUsedInLines);

        outputWriter.print("|");
        outputWriter.print(String.format("%6d    |", keyboardNumber));
        outputWriter.print(String.format("%9d       |", printableLines));
        outputWriter.printLine(String.format(" %-28s|", lettersThatCanBeBroken));
        printTableDivider(outputWriter);
    }

    private static int computePrintableLines(Set<Character> brokenKeySet, List<String> text,
                                             Set<Character> keysUsedInLines) {
        int printableLines = 0;

        for (String line : text) {
            boolean canPrint = true;
            Set<Character> keysInLine = new HashSet<>();

            for (char key : line.toCharArray()) {
                if (brokenKeySet.contains(key)) {
                    canPrint = false;
                    break;
                }
                keysInLine.add(key);
            }

            if (canPrint) {
                printableLines++;
                keysUsedInLines.addAll(keysInLine);
            }
        }
        return printableLines;
    }

    private static String computeLettersThatCanBeBroken(Set<Character> brokenKeySet, Set<Character> keysUsedInLines) {
        StringBuilder lettersThatCanBeBroken = new StringBuilder();
        for (char key = 'a'; key <= 'z'; key++) {
            if (!keysUsedInLines.contains(key) && !brokenKeySet.contains(key)) {
                lettersThatCanBeBroken.append(key);
            }
        }
        return lettersThatCanBeBroken.toString();
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