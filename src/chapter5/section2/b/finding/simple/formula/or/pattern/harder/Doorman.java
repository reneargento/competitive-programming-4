package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/01/25.
 */
public class Doorman {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int maxDifference = FastReader.nextInt();
        String queue = FastReader.next();

        int maxPeopleAllowed = computeMaxPeopleAllowed(maxDifference, queue);
        outputWriter.printLine(maxPeopleAllowed);
        outputWriter.flush();
    }

    private static int computeMaxPeopleAllowed(int maxDifference, String queue) {
        int maxPeopleAllowed = 0;
        int currentDifference = 0;

        for (int i = 0; i < queue.length(); i++) {
            if (queue.charAt(i) == 'M') {
                currentDifference++;
            } else {
                currentDifference--;
            }

            if (currentDifference < -maxDifference || currentDifference > maxDifference) {
                if (i == queue.length() - 1) {
                    break;
                }
                if (queue.charAt(i + 1) == 'M') {
                    currentDifference++;
                } else {
                    currentDifference--;
                }

                if (currentDifference < -maxDifference || currentDifference > maxDifference) {
                    break;
                } else {
                    maxPeopleAllowed++;
                    i++;
                }
            }
            maxPeopleAllowed++;
        }
        return maxPeopleAllowed;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
