package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/05/21.
 */
public class CompetitiveArcadeBasketball {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int participants = FastReader.nextInt();
        int pointsRequired = FastReader.nextInt();
        int pointsGained = FastReader.nextInt();

        for (int i = 0; i < participants; i++) {
            FastReader.next();
        }

        Map<String, Integer> participantPoints = new HashMap<>();
        List<String> winners = new ArrayList<>();

        for (int i = 0; i < pointsGained; i++) {
            String name = FastReader.next();
            int points = FastReader.nextInt();

            int currentPoints = participantPoints.getOrDefault(name, 0);
            int newPoints = currentPoints + points;
            if (currentPoints < pointsRequired && newPoints >= pointsRequired) {
                winners.add(name);
            }
            participantPoints.put(name, newPoints);
        }

        if (winners.isEmpty()) {
            outputWriter.printLine("No winner!");
        } else {
            for (String name : winners) {
                outputWriter.printLine(String.format("%s wins!", name));
            }
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
