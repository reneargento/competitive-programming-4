package chapter6.section2.f.really.ad.hoc;

import java.io.*;

/**
 * Created by Rene Argento on 01/07/2026.
 */
public class PeriodicStrings {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        int smallestK = computeSmallestK(string);
        outputWriter.printLine(smallestK);
        outputWriter.flush();
    }

    private static int computeSmallestK(String string) {
        for (int k = 1; k < string.length(); k++) {
            if (string.length() % k != 0) {
                continue;
            }
            boolean isPeriodic = true;
            String substring = string.substring(0, k);

            for (int i = k; i < string.length(); i += k) {
                String candidateSubstring = string.substring(i, i + k);
                String rotatedSubstring = substring.charAt(k - 1) + substring.substring(0, k - 1);
                if (!candidateSubstring.equals(rotatedSubstring)) {
                    isPeriodic = false;
                    break;
                }
                substring = candidateSubstring;
            }

            if (isPeriodic) {
                return k;
            }
        }
        return string.length();
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