package chapter6.section2.d.output.formatting.harder;

import java.io.*;

/**
 * Created by Rene Argento on 21/06/2026.
 */
public class RomulanSpelling {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String correctedLine = correctLine(line);
            outputWriter.printLine(correctedLine);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String correctLine(String line) {
        StringBuilder correctedLine = new StringBuilder();
        String lastCorrectedLine = null;

        while (true) {
            for (int i = 0; i < line.length(); i++) {
                char current = line.charAt(i);

                if (i < line.length() - 1) {
                    char next = line.charAt(i + 1);

                    if ((Character.toLowerCase(current) == 'p' && Character.toLowerCase(next) == 'g')
                            || (Character.toLowerCase(current) == 'g' && Character.toLowerCase(next) == 'p') ) {
                        if (Character.toLowerCase(current) == 'p' && Character.toLowerCase(next) == 'g') {
                            if (!(i > 0 && Character.toLowerCase(line.charAt(i - 1)) == 'e')
                                    && !isPronouncedX(line, i)) {
                                correctedLine.append(next);
                                correctedLine.append(current);
                                i++;
                                continue;
                            }
                        } else {
                            if ((i > 0
                                    && Character.toLowerCase(line.charAt(i - 1)) == 'e')
                                    || isPronouncedX(line, i)) {
                                correctedLine.append(next);
                                correctedLine.append(current);
                                i++;
                                continue;
                            }
                        }
                    }
                }
                correctedLine.append(current);
            }

            String correctedLineString = correctedLine.toString();
            if (lastCorrectedLine != null && lastCorrectedLine.equals(correctedLineString)) {
                return correctedLineString;
            }
            lastCorrectedLine = correctedLineString;
            correctedLine = new  StringBuilder();
            line = correctedLineString;
        }
    }

    private static boolean isPronouncedX(String line, int index) {
        return (index < line.length() - 3
                && Character.toLowerCase(line.charAt(index + 2)) == 'u'
                && Character.toLowerCase(line.charAt(index + 3)) == 'k');
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