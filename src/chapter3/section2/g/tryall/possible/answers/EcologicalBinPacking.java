package chapter3.section2.g.tryall.possible.answers;

import java.io.*;

/**
 * Created by Rene Argento on 11/12/21.
 */
public class EcologicalBinPacking {

    private static class Bin {
        int[] bottles;

        public Bin(int brownBottles, int greenBottles, int clearBottles) {
            bottles = new int[]{ brownBottles, greenBottles, clearBottles };
        }
    }

    private static class BinOrder implements Comparable<BinOrder> {
        String order;
        int movements;

        public BinOrder(String order, int movements) {
            this.order = order;
            this.movements = movements;
        }

        @Override
        public int compareTo(BinOrder other) {
            if (movements != other.movements) {
                return Integer.compare(movements, other.movements);
            }
            return order.compareTo(other.order);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            Bin[] bins = new Bin[3];
            String[] bottles = line.split(" ");
            int bottlesIndex = 0;

            for (int i = 0; i < 3; i++) {
                int brownBottles = Integer.parseInt(bottles[bottlesIndex++]);
                int greenBottles = Integer.parseInt(bottles[bottlesIndex++]);
                int clearBottles = Integer.parseInt(bottles[bottlesIndex++]);
                bins[i] = new Bin(brownBottles, greenBottles, clearBottles);
            }
            BinOrder binOrder = getBestBinOrder(bins);
            outputWriter.printLine(String.format("%s %d", binOrder.order, binOrder.movements));

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BinOrder getBestBinOrder(Bin[] bins) {
        char[] binTypes = { 'B', 'G', 'C' };
        char[] order = new char[3];
        return getBestBinOrder(bins, binTypes, order, 0, 0);
    }

    private static BinOrder getBestBinOrder(Bin[] bins, char[] binTypes, char[] order, int index, int mask) {
        if (index == order.length) {
            return countMovements(bins, order);
        }

        BinOrder bestOrder = null;
        for (int i = 0; i < order.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                order[index] = binTypes[i];

                BinOrder binOrder = getBestBinOrder(bins, binTypes, order, index + 1, newMask);
                if (bestOrder == null || bestOrder.compareTo(binOrder) > 0) {
                    bestOrder = binOrder;
                }
            }
        }
        return bestOrder;
    }

    private static BinOrder countMovements(Bin[] bins, char[] order) {
        int movements = 0;

        for (int binIndex = 0; binIndex < 3; binIndex++) {
            int otherBinIndex1 = (binIndex + 1) % 3;
            int otherBinIndex2 = (otherBinIndex1 + 1) % 3;

            switch (order[binIndex]) {
                case 'B':
                    movements += countBottlesMovement(bins, 0, otherBinIndex1, otherBinIndex2);
                    break;
                case 'G':
                    movements += countBottlesMovement(bins, 1, otherBinIndex1, otherBinIndex2);
                    break;
                case 'C':
                    movements += countBottlesMovement(bins, 2, otherBinIndex1, otherBinIndex2);
                    break;
            }
        }
        return new BinOrder(new String(order), movements);
    }

    private static int countBottlesMovement(Bin[] bins, int colorIndex, int binIndex1, int binIndex2) {
        return bins[binIndex1].bottles[colorIndex] + bins[binIndex2].bottles[colorIndex];
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
