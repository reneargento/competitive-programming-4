package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/01/22.
 */
public class HouseLawn {

    private static class Lawnmower {
        String name;
        int price;
        long cuttingRate;
        long cuttingTime;
        int rechargeTime;

        public Lawnmower(String name, int price, int cuttingRate, int cuttingTime, int rechargeTime) {
            this.name = name;
            this.price = price;
            this.cuttingRate = cuttingRate;
            this.cuttingTime = cuttingTime;
            this.rechargeTime = rechargeTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lawnSize = FastReader.nextInt();
        Lawnmower[] lawnmowers = new Lawnmower[FastReader.nextInt()];

        for (int i = 0; i < lawnmowers.length; i++) {
            String[] data = FastReader.getLine().split(",");
            lawnmowers[i] = new Lawnmower(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]), Integer.parseInt(data[4]));
        }

        List<Lawnmower> lawnmowerSelected = selectLawnmowers(lawnSize, lawnmowers);
        if (lawnmowerSelected.isEmpty()) {
            outputWriter.printLine("no such mower");
        } else {
            for (Lawnmower lawnmower : lawnmowerSelected) {
                outputWriter.printLine(lawnmower.name);
            }
        }
        outputWriter.flush();
    }

    private static List<Lawnmower> selectLawnmowers(int lawnSize, Lawnmower[] lawnmowers) {
        List<Lawnmower> selectedLawnmowers = new ArrayList<>();
        int lowestPrice = Integer.MAX_VALUE;

        for (Lawnmower lawnmower : lawnmowers) {
            if (canCut(lawnSize, lawnmower)) {
                if (lawnmower.price < lowestPrice) {
                    selectedLawnmowers = new ArrayList<>();
                    lowestPrice = lawnmower.price;
                }

                if (lawnmower.price <= lowestPrice) {
                    selectedLawnmowers.add(lawnmower);
                }
            }
        }
        return selectedLawnmowers;
    }

    private static boolean canCut(int lawnSize, Lawnmower lawnmower) {
        long timeSpent = lawnmower.cuttingTime + lawnmower.rechargeTime;
        long averageLawnCut = lawnmower.cuttingRate * lawnmower.cuttingTime * 10080 / timeSpent;
        return averageLawnCut >= lawnSize;
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
