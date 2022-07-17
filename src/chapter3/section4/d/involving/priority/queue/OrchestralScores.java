package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/07/22.
 */
public class OrchestralScores {

    private static class Stand implements Comparable<Stand> {
        int musicians;
        double scores;

        public Stand(int musicians) {
            this.musicians = musicians;
            scores = 1;
        }

        public double getRatio() {
            return musicians / scores;
        }

        @Override
        public int compareTo(Stand other) {
            return Double.compare(other.getRatio(), getRatio());
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int scores = Integer.parseInt(data[0]);
            int differentInstruments = Integer.parseInt(data[1]);
            PriorityQueue<Stand> priorityQueue = new PriorityQueue<>();

            for (int i = 0; i < differentInstruments; i++) {
                priorityQueue.add(new Stand(FastReader.nextInt()));
            }
            int maxMusiciansSharingStand = computeMaxMusiciansSharingStand(priorityQueue, scores);
            outputWriter.printLine(maxMusiciansSharingStand);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaxMusiciansSharingStand(PriorityQueue<Stand> priorityQueue, int scores) {
        scores -= priorityQueue.size();
        while (scores > 0) {
            Stand mostCrowdedStand = priorityQueue.poll();
            mostCrowdedStand.scores++;
            priorityQueue.offer(mostCrowdedStand);
            scores--;
        }
        return (int) Math.ceil(priorityQueue.peek().getRatio());
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
