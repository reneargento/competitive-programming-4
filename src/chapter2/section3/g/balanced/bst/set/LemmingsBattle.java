package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/06/21.
 */
public class LemmingsBattle {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            int battlefields = FastReader.nextInt();
            int greenArmySize = FastReader.nextInt();
            int blueArmySize = FastReader.nextInt();

            PriorityQueue<Integer> greenArmy = new PriorityQueue<>(Collections.reverseOrder());
            PriorityQueue<Integer> blueArmy = new PriorityQueue<>(Collections.reverseOrder());

            for (int i = 0; i < greenArmySize; i++) {
                greenArmy.offer(FastReader.nextInt());
            }
            for (int i = 0; i < blueArmySize; i++) {
                blueArmy.offer(FastReader.nextInt());
            }
            fightWar(greenArmy, blueArmy, battlefields, outputWriter);
        }
        outputWriter.flush();
    }

    private static void fightWar(PriorityQueue<Integer> greenArmy, PriorityQueue<Integer> blueArmy,
                                 int battlefields, OutputWriter outputWriter) {
        int[] greenArmySoldiers = new int[battlefields];
        int[] blueArmySoldiers = new int[battlefields];

        while (!greenArmy.isEmpty() && !blueArmy.isEmpty()) {
            int battles = Math.min(battlefields, greenArmy.size());
            battles = Math.min(battles, blueArmy.size());

            for (int i = 0; i < battles; i++) {
                greenArmySoldiers[i] = greenArmy.poll();
            }
            for (int i = 0; i < battles; i++) {
                blueArmySoldiers[i] = blueArmy.poll();
            }

            for (int b = 0; b < battles; b++) {
                if (greenArmySoldiers[b] != blueArmySoldiers[b]) {
                    int remainingPower = Math.abs(greenArmySoldiers[b] - blueArmySoldiers[b]);
                    if (greenArmySoldiers[b] > blueArmySoldiers[b]) {
                        greenArmy.offer(remainingPower);
                    } else {
                        blueArmy.offer(remainingPower);
                    }
                }
            }
        }

        if (greenArmy.isEmpty() && blueArmy.isEmpty()) {
            outputWriter.printLine("green and blue died");
        } else {
            if (!greenArmy.isEmpty()) {
                outputWriter.printLine("green wins");
                printSoldiers(greenArmy, outputWriter);
            } else {
                outputWriter.printLine("blue wins");
                printSoldiers(blueArmy, outputWriter);
            }
        }
    }

    private static void printSoldiers(PriorityQueue<Integer> army, OutputWriter outputWriter) {
        while (!army.isEmpty()) {
            outputWriter.printLine(army.poll());
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
