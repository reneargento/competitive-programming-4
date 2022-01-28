package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/01/22.
 */
public class CoconutSplat {

    enum HandState {
        FOLDED, FIST, PALM_DOWN
    }

    private static class Hand {
        int id;
        HandState handState;

        public Hand(int id, HandState handState) {
            this.id = id;
            this.handState = handState;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int syllables = FastReader.nextInt();
        int players = FastReader.nextInt();

        int lastPlayer = computeLastPlayer(syllables, players);
        outputWriter.printLine(lastPlayer);

        outputWriter.flush();
    }

    private static int computeLastPlayer(int syllables, int hands) {
        LinkedList<Hand> handsList = new LinkedList<>();
        for (int i = 1; i <= hands; i++) {
            handsList.add(new Hand(i, HandState.FOLDED));
        }

        int handIndex = 0;
        while (handsList.size() > 1) {
            handIndex = (handIndex + syllables - 1) % handsList.size();

            Hand currentHand = handsList.get(handIndex);
            switch (currentHand.handState) {
                case FOLDED:
                    currentHand.handState = HandState.FIST;
                    handsList.add(handIndex, new Hand(currentHand.id, HandState.FIST));
                    break;
                case FIST:
                    currentHand.handState = HandState.PALM_DOWN;
                    handIndex = (handIndex + 1) % handsList.size();
                    break;
                case PALM_DOWN:
                    handsList.remove(handIndex);
            }

            if (handsList.size() == 2 && handsList.getFirst().id == handsList.get(1).id) {
                break;
            }
        }
        return handsList.getFirst().id;
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
