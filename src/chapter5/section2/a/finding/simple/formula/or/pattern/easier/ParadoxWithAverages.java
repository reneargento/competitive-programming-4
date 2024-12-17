package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/12/24.
 */
public class ParadoxWithAverages {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] computerScienceIqs = new int[FastReader.nextInt()];
            int[] economicsIqs = new int[FastReader.nextInt()];
            readInput(computerScienceIqs);
            readInput(economicsIqs);

            int validStudents = countValidStudents(computerScienceIqs, economicsIqs);
            outputWriter.printLine(validStudents);
        }
        outputWriter.flush();
    }

    private static void readInput(int[] iqs) throws IOException {
        for (int i = 0; i < iqs.length; i++) {
            iqs[i] = FastReader.nextInt();
        }
    }

    private static int countValidStudents(int[] computerScienceIqs, int[] economicsIqs) {
        int validStudents = 0;
        double averageComputerScience = getAverage(computerScienceIqs);
        double averageEconomicsIqs = getAverage(economicsIqs);

        for (int iq : computerScienceIqs) {
            if (iq > averageEconomicsIqs && iq < averageComputerScience) {
                validStudents++;
            }
        }
        return validStudents;
    }

    private static double getAverage(int[] iqs) {
        double sum = 0;
        for (int iq : iqs) {
            sum += iq;
        }
        return sum / iqs.length;
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
