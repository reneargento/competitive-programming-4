package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/05/21.
 */
public class AGiveaway {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Set<Integer> specialNumbers = getSpecialNumbers();

        int number = FastReader.nextInt();
        while (number != 0) {
            if (specialNumbers.contains(number)) {
                outputWriter.printLine("Special");
            } else {
                outputWriter.printLine("Ordinary");
            }
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Set<Integer> getSpecialNumbers() {
        Set<Integer> specialNumbers = new HashSet<>();
        specialNumbers.add(1);
        specialNumbers.add(64);
        specialNumbers.add(729);
        specialNumbers.add(4096);
        specialNumbers.add(15625);
        specialNumbers.add(46656);
        specialNumbers.add(117649);
        specialNumbers.add(262144);
        specialNumbers.add(531441);
        specialNumbers.add(1000000);
        specialNumbers.add(1771561);
        specialNumbers.add(2985984);
        specialNumbers.add(4826809);
        specialNumbers.add(7529536);
        specialNumbers.add(11390625);
        specialNumbers.add(16777216);
        specialNumbers.add(24137569);
        specialNumbers.add(34012224);
        specialNumbers.add(47045881);
        specialNumbers.add(64000000);
        specialNumbers.add(85766121);
        return specialNumbers;
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
