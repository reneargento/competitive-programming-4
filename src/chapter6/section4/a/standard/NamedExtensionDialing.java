package chapter6.section4.a.standard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 17/08/2026.
 */
public class NamedExtensionDialing {

    private static class DirectoryEntry {
        String name;
        String lastName;
        String extension;

        public DirectoryEntry(String name, String lastName, String extension) {
            this.name = name;
            this.lastName = lastName;
            this.extension = extension;
        }
    }

    private static final String[] DIGIT_MAP = {
            "", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<DirectoryEntry> directoryEntries = new ArrayList<>();
        String line = FastReader.getLine();
        while (line != null) {
            if (Character.isLetter(line.charAt(0))) {
                String[] data = line.split(" ");
                directoryEntries.add(new DirectoryEntry(data[0].toUpperCase(), data[1].toUpperCase(), data[2]));
            } else {
                List<String> extensions = findExtensions(directoryEntries, line);
                if (extensions.isEmpty()) {
                    outputWriter.printLine("0");
                } else {
                    outputWriter.print(extensions.get(0));
                    for (int i = 1; i < extensions.size(); i++) {
                        outputWriter.print(" " + extensions.get(i));
                    }
                    outputWriter.printLine();
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> findExtensions(List<DirectoryEntry> directoryEntries, String digits) {
        List<String> extensions = new ArrayList<>();

        for (DirectoryEntry directoryEntry : directoryEntries) {
            if (directoryEntry.extension.equals(digits)) {
                extensions.add(directoryEntry.extension);
                return extensions;
            }
        }

        if (digits.contains("0") || digits.contains("1")) {
            return extensions;
        }

        for (DirectoryEntry directoryEntry : directoryEntries) {
            if (isMatch(directoryEntry, digits)) {
                extensions.add(directoryEntry.extension);
            }
        }
        return extensions;
    }

    private static boolean isMatch(DirectoryEntry directoryEntry, String digits) {
        if (digits.isEmpty() || (digits.length() - 1) > directoryEntry.lastName.length()) {
            return false;
        }

        String nameChars = DIGIT_MAP[Character.getNumericValue(digits.charAt(0))];
        String firstLetter = String.valueOf(directoryEntry.name.charAt(0));
        if (!nameChars.contains(firstLetter)) {
            return false;
        }

        for (int i = 1; i < digits.length(); i++) {
            int digit = Character.getNumericValue(digits.charAt(i));
            String validChars = DIGIT_MAP[digit];
            String letter = String.valueOf(directoryEntry.lastName.charAt(i - 1));
            if (!validChars.contains(letter)) {
                return false;
            }
        }
        return true;
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