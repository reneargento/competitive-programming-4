package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/04/21.
 */
public class ThrowingCardsAwayI {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cards = FastReader.nextInt();

        while (cards != 0) {
            throwCards(cards, outputWriter);
            cards = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void throwCards(int cards, OutputWriter outputWriter) {
        Queue<Integer> queue = new LinkedList<>();
        for (int card = 1; card <= cards; card++) {
            queue.offer(card);
        }

        outputWriter.print("Discarded cards:");
        if (cards > 1) {
            outputWriter.print(" ");
        }

        while (queue.size() >= 2) {
            outputWriter.print(queue.poll());
            if (queue.size() >= 2) {
                outputWriter.print(", ");
            }

            int cardToMoveToBottom = queue.poll();
            queue.offer(cardToMoveToBottom);
        }
        outputWriter.printLine();

        outputWriter.printLine("Remaining card: " + queue.poll());
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
