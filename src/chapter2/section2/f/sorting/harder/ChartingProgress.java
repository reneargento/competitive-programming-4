package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/02/21.
 */
public class ChartingProgress {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int caseNumber = 1;

        while (line != null) {
            if (caseNumber > 1) {
                outputWriter.printLine();
            }
            Map<Integer, Integer> logFrequency = new HashMap<>();
            int columnSize = line.length();
            int lineNumber = 0;

            while (line != null && !line.isEmpty()) {
                int frequency = countLogFrequency(line);
                logFrequency.put(lineNumber, frequency);

                line = FastReader.getLine();
                lineNumber++;
            }
            printLogs(logFrequency, columnSize, lineNumber, outputWriter);
            caseNumber++;

            if (line != null) {
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int countLogFrequency(String line) {
        int logs = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '*') {
                logs++;
            }
        }
        return logs;
    }

    private static void printLogs(Map<Integer, Integer> logFrequency, int columnSize, int lines,
                                  OutputWriter outputWriter) {
        int logged = 0;
        for (int l = 0; l < lines; l++) {
            int frequency = logFrequency.get(l);

            char[] line = getEmptyLine(columnSize);
            int startColumn = columnSize - 1 - logged;
            for (int i = startColumn; i > startColumn - frequency; i--) {
                line[i] = '*';
                logged++;
            }

            outputWriter.printLine(new String(line));
        }
    }

    private static char[] getEmptyLine(int columnSize) {
        char[] line = new char[columnSize];
        for (int i = 0; i < columnSize; i++) {
            line[i] = '.';
        }
        return line;
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
