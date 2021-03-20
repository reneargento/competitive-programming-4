package chapter2.section2.j.stack;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/03/21.
 */
public class WorkingAtTheRestaurant {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int operations = FastReader.nextInt();
        int caseNumber = 1;

        while (operations != 0) {
            if (caseNumber > 1) {
                outputWriter.printLine();
            }

            int platesOnStack2 = 0;
            int platesOnStack1 = 0;

            for (int i = 0; i < operations; i++) {
                String operation = FastReader.next();
                int plates = FastReader.nextInt();

                if (operation.equals("DROP")) {
                    outputWriter.printLine("DROP 2 " + plates);
                    platesOnStack2 += plates;
                } else {
                    if (plates <= platesOnStack1) {
                        outputWriter.printLine("TAKE 1 " + plates);
                        platesOnStack1 -= plates;
                    } else {
                        int platesFromStack2ToTake = plates - platesOnStack1;

                        if (platesOnStack1 > 0) {
                            outputWriter.printLine("TAKE 1 " + platesOnStack1);
                        }
                        outputWriter.printLine("MOVE 2->1 " + platesOnStack2);
                        outputWriter.printLine("TAKE 1 " + platesFromStack2ToTake);

                        platesOnStack1 = platesOnStack2 - platesFromStack2ToTake;
                        platesOnStack2 = 0;
                    }
                }
            }

            caseNumber++;
            operations = FastReader.nextInt();
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
