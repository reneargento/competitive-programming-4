package chapter3.section4.f.non.classical.harder;

import java.io.*;

/**
 * Created by Rene Argento on 05/08/22.
 */
public class Packets {

    private static final String EOF = "0 0 0 0 0 0";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] packets = new int[6];
        String line = FastReader.getLine();

        while (!line.equals(EOF)) {
            String[] data = line.split(" ");
            for (int i = 0; i < packets.length; i++) {
                packets[i] = Integer.parseInt(data[i]);
            }

            int minimalParcels = computeMinimalParcels(packets);
            outputWriter.printLine(minimalParcels);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimalParcels(int[] packets) {
        int minimalParcels = 0;

        // 6x6 occupies one parcel each
        minimalParcels += packets[5];

        // 5x5 occupies one parcel each and leaves room for 11 1x1
        minimalParcels += packets[4];
        int oneByOnePacketsIn5x5 = packets[4] * 11;
        packets[0] = Math.max(0, packets[0] - oneByOnePacketsIn5x5);

        // 4x4 occupies one parcel each and leaves room for 5 2x2
        minimalParcels += packets[3];
        int twoByTwoPacketsPossible = packets[3] * 5;
        placePackages(packets, twoByTwoPacketsPossible, 0);

        // 3x3 occupies 1/4 per parcel
        // Three 3x3 leave room for 1 2x2, 5 1x1
        // Two 3x3 leave room for 3 2x2, 6 1x1
        // Only 1 3x3 leaves room for 5 2x2, 7 1x1
        minimalParcels += (packets[2] / 4);
        if (packets[2] % 4 != 0) {
            minimalParcels++;
            int threeByThreeSpaces = 4 - (packets[2] % 4);
            switch (threeByThreeSpaces) {
                case 1: placePackages(packets, 1, 5); break;
                case 2: placePackages(packets, 3, 6); break;
                case 3: placePackages(packets, 5, 7);
            }
        }

        // 2x2 occupies 1/9 parcel
        minimalParcels += (packets[1] / 9);
        if (packets[1] % 9 != 0) {
            minimalParcels++;
            int twoByTwoSpaces = 9 - (packets[1] % 9);
            int oneByOnePackets = twoByTwoSpaces * 4;
            packets[0] = Math.max(0, packets[0] - oneByOnePackets);
        }

        // 1x1 occupies 1/36 parcel
        minimalParcels += (packets[0] / 36);
        if (packets[0] % 36 != 0) {
            minimalParcels++;
        }
        return minimalParcels;
    }

    private static void placePackages(int[] packets, int twoByTwoPacketsPossible, int oneByOnePacketsPossible) {
        int twoByTwoPackets;
        int oneByOnePackets = oneByOnePacketsPossible;

        if (twoByTwoPacketsPossible > packets[1]) {
            int difference = twoByTwoPacketsPossible - packets[1];
            twoByTwoPackets = packets[1];
            oneByOnePackets += difference * 4;
        } else {
            twoByTwoPackets = twoByTwoPacketsPossible;
        }
        packets[1] = Math.max(0, packets[1] - twoByTwoPackets);
        packets[0] = Math.max(0, packets[0] - oneByOnePackets);
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
