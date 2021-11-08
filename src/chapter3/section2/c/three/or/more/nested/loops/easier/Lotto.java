package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/11/21.
 */
public class Lotto {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numbers = FastReader.nextInt();
        int caseNumber = 0;

        while (numbers != 0) {
            if (caseNumber > 0) {
                outputWriter.printLine();
            }

            int[] numbersArray = new int[numbers];
            for (int i = 0; i < numbersArray.length; i++) {
                numbersArray[i] = FastReader.nextInt();
            }
            printNumbers(numbersArray, 0, new ArrayList<>(), outputWriter);

            caseNumber++;
            numbers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void printNumbers(int[] numbersArray, int index, List<Integer> selected,
                                     OutputWriter outputWriter) {
        if (selected.size() == 6) {
            for (int i = 0; i < selected.size(); i++) {
                outputWriter.print(selected.get(i));

                if (i != selected.size() - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
            return;
        }

        if (index == numbersArray.length) {
            return;
        }

        for (int i = index; i < numbersArray.length; i++) {
            selected.add(numbersArray[i]);
            printNumbers(numbersArray, i + 1, selected, outputWriter);
            selected.remove(Integer.valueOf(numbersArray[i]));
        }
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
