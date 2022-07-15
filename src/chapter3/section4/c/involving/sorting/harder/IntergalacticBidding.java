package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 12/07/22.
 */
public class IntergalacticBidding {

    private static class Participant implements Comparable<Participant> {
        String name;
        BigInteger number;

        public Participant(String name, BigInteger number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public int compareTo(Participant other) {
            return other.number.compareTo(number);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Participant[] participants = new Participant[FastReader.nextInt()];
        BigInteger numberChosen = new BigInteger(FastReader.next());

        for (int i = 0; i < participants.length; i++) {
            participants[i] = new Participant(FastReader.next(), new BigInteger(FastReader.next()));
        }
        Set<String> winners = computeWinners(participants, numberChosen);

        outputWriter.printLine(winners.size());
        for (String winner : winners) {
            outputWriter.printLine(winner);
        }
        outputWriter.flush();
    }

    private static Set<String> computeWinners(Participant[] participants, BigInteger numberChosen) {
        Set<String> winners = new HashSet<>();
        Arrays.sort(participants);

        for (Participant participant : participants) {
            if (numberChosen.compareTo(participant.number) >= 0) {
                numberChosen = numberChosen.subtract(participant.number);
                winners.add(participant.name);
            }
        }

        if (numberChosen.compareTo(BigInteger.ZERO) != 0) {
            return new HashSet<>();
        }
        return winners;
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
