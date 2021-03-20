package chapter2.section2.j.stack;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/03/21.
 */
public class Rails {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int coaches = FastReader.nextInt();

        while (coaches != 0) {
            int number = FastReader.nextInt();

            while (number != 0) {
                Deque<Integer> stack = new ArrayDeque<>();
                boolean possible = true;
                int currentCoach = 1;

                for (int i = 1; i < coaches; i++) {
                    boolean found = false;

                    if (!stack.isEmpty() && stack.peek() == number) {
                        stack.pop();
                        found = true;
                    } else {
                        while (currentCoach <= coaches) {
                            if (currentCoach == number) {
                                currentCoach++;
                                found = true;
                                break;
                            }

                            stack.push(currentCoach);
                            currentCoach++;
                        }
                    }

                    if (!found) {
                        possible = false;
                    }
                    number = FastReader.nextInt();
                }
                outputWriter.printLine(possible ? "Yes" : "No");
                number = FastReader.nextInt();
            }

            coaches = FastReader.nextInt();
            outputWriter.printLine();
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
