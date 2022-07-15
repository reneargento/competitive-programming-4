package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/07/22.
 */
public class GovernmentHelp {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int financialPackages = FastReader.nextInt();

        while (financialPackages != 0) {
            int[] money = new int[financialPackages];
            for (int i = 0; i < money.length; i++) {
                money[i] = FastReader.nextInt();
            }
            computeBestPackageOrder(money, outputWriter);
            financialPackages = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void computeBestPackageOrder(int[] money, OutputWriter outputWriter) {
        Arrays.sort(money);
        outputWriter.print(money[0] + "-A");
        char nextBank = 'B';

        for (int i = money.length - 1; i >= 1; i--) {
            outputWriter.print(" " + money[i] + "-" + nextBank);
            if (nextBank == 'A') {
                nextBank = 'B';
            } else {
                nextBank = 'A';
            }
        }
        outputWriter.printLine();
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
