package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/11/24.
 */
public class ParadoxWithAveragesHard {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] computerScienceIqs = new int[FastReader.nextInt()];
            int[] economicsScienceIqs = new int[FastReader.nextInt()];
            readIqs(computerScienceIqs);
            readIqs(economicsScienceIqs);

            int studentsNumber = computeStudentsNumber(computerScienceIqs, economicsScienceIqs);
            outputWriter.printLine(studentsNumber);
        }
        outputWriter.flush();
    }

    private static void readIqs(int[] iqs) throws IOException{
        for (int i = 0; i < iqs.length; i++) {
            iqs[i] = FastReader.nextInt();
        }
    }

    private static int computeStudentsNumber(int[] computerScienceIqs, int[] economicsScienceIqs) {
        double averageCsIq = Math.ceil(computeAverage(computerScienceIqs));
        double averageEconomicsIq = Math.floor(computeAverage(economicsScienceIqs));

        int studentsNumber = 0;
        for (int iq : computerScienceIqs) {
            if (iq > averageEconomicsIq && iq < averageCsIq) {
                studentsNumber++;
            }
        }
        return studentsNumber;
    }

    private static double computeAverage(int[] values) {
        double sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum / values.length;
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
