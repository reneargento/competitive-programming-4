package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/05/21.
 */
public class CD {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int jackCDsNumber = FastReader.nextInt();
        int jillCDsNumber = FastReader.nextInt();

        while (jackCDsNumber != 0 || jillCDsNumber != 0) {
            Set<Integer> jackCDs = new HashSet<>();
            for (int i = 0; i < jackCDsNumber; i++) {
                int cdNumber = FastReader.nextInt();
                jackCDs.add(cdNumber);
            }

            int cdsOwnedByBoth = 0;
            for (int i = 0; i < jillCDsNumber; i++) {
                int cdNumber = FastReader.nextInt();
                if (jackCDs.contains(cdNumber)) {
                    cdsOwnedByBoth++;
                }
            }
            outputWriter.printLine(cdsOwnedByBoth);

            jackCDsNumber = FastReader.nextInt();
            jillCDsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
