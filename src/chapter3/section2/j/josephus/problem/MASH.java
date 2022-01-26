package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rene Argento on 26/01/22.
 */
public class MASH {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int selection = 1;

        while (line != null) {
            String[] data = line.split(" ");
            int people = Integer.parseInt(data[0]);
            int luckyPositionsNumber = Integer.parseInt(data[1]);
            int[] cards = new int[20];

            for (int i = 0; i < cards.length; i++) {
                cards[i] = Integer.parseInt(data[2 + i]);
            }

            List<Integer> luckyPositions = computeLuckyPositions(people, luckyPositionsNumber, cards);
            outputWriter.printLine(String.format("Selection #%d", selection));
            for (int i = 0; i < luckyPositions.size(); i++) {
                outputWriter.print(luckyPositions.get(i));

                if (i != luckyPositions.size() - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
            outputWriter.printLine();

            selection++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeLuckyPositions(int people, int luckyPositionsNumber, int[] cards) {
        List<Integer> luckyPositions = new LinkedList<>();
        for (int i = 1; i <= people; i++) {
            luckyPositions.add(i);
        }

        int skipLength = cards[0];
        int deckIndex = 1;
        int currentPosition = 0;

        while (luckyPositions.size() > luckyPositionsNumber) {
            int selectedPerson = currentPosition + skipLength - 1;

            if (selectedPerson < luckyPositions.size()) {
                luckyPositions.remove(selectedPerson);
                currentPosition = selectedPerson;
            } else {
                if (deckIndex == cards.length) {
                    break;
                }
                skipLength = cards[deckIndex];
                deckIndex++;
                currentPosition = 0;
            }
        }
        return luckyPositions;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
