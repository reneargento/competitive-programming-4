package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/09/21.
 */
public class Stamps {

    private static int maxValue;
    private static int[] selectedStamps;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberOfStamps = FastReader.nextInt();
        int denominations = FastReader.nextInt();

        while (numberOfStamps != 0 && denominations != 0) {
            maxValue = 0;
            selectedStamps = new int[denominations];
            processStamps(numberOfStamps, denominations, outputWriter);

            numberOfStamps = FastReader.nextInt();
            denominations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void processStamps(int numberOfStamps, int denominations, OutputWriter outputWriter) {
        int[] aux = new int[denominations];
        dfs(1, 0, numberOfStamps, denominations, aux);

        for (int stamp : selectedStamps) {
            outputWriter.print(String.format("%3d", stamp));
        }
        outputWriter.printLine(String.format(" ->%3d", maxValue));
    }

    private static void dfs(int current, int last, int numberOfStamps, int denominations, int[] aux) {
        if (isValid(current, 0, 0, 0, last, numberOfStamps, aux)) {
            dfs(current + 1, last, numberOfStamps, denominations, aux);
        }

        if (last < denominations) {
            aux[last] = current;
            dfs(current + 1, last + 1, numberOfStamps, denominations, aux);
            aux[last] = 0;
        }

        if (current - 1 > maxValue) {
            maxValue = current - 1;
            System.arraycopy(aux, 0, selectedStamps, 0, aux.length);
        }
    }

    private static boolean isValid(int current, int index, int stampsUsed, int sum, int last,
                                   int numberOfStamps, int[] aux) {
        if (current == sum) {
            return true;
        }
        if (index == last || stampsUsed == numberOfStamps) {
            return false;
        }
        return isValid(current, index + 1, stampsUsed, sum, last, numberOfStamps, aux)
                || isValid(current, index, stampsUsed + 1, sum + aux[index], last, numberOfStamps, aux);
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
