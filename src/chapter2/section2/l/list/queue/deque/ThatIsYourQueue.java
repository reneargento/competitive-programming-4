package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/04/21.
 */
public class ThatIsYourQueue {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int population = FastReader.nextInt();
        int commands = FastReader.nextInt();
        int caseNumber = 1;

        while (population != 0 || commands != 0) {
            Deque<Integer> peopleDeque = createDeque(population, commands);
            outputWriter.printLine(String.format("Case %d:", caseNumber));

            for (int c = 0; c < commands; c++) {
                String commandType = FastReader.next();

                if (commandType.equals("N")) {
                    int nextPerson = peopleDeque.removeFirst();
                    outputWriter.printLine(nextPerson);
                    peopleDeque.addLast(nextPerson);
                } else {
                    int personToExpedite = FastReader.nextInt();
                    peopleDeque.remove(personToExpedite);
                    peopleDeque.addFirst(personToExpedite);
                }
            }

            caseNumber++;
            population = FastReader.nextInt();
            commands = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Deque<Integer> createDeque(int population, int commands) {
        Deque<Integer> peopleDeque = new ArrayDeque<>();
        int size = Math.min(population, commands);

        for (int i = 1; i <= size; i++) {
            peopleDeque.addLast(i);
        }
        return peopleDeque;
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
