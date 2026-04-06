package chapter4.section6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/07/24.
 */
// Based on https://www.dropbox.com/scl/fi/ome3m546wlwmyzxgvdfax/scusa-2019-outlines.pdf?rlkey=egnvvw8fazodhrjd9stx7ijdm&e=1&dl=0
@SuppressWarnings("unchecked")
public class FullDepthMorningShow {

    private static class Edge {
        int nextCityId;
        int toll;

        public Edge(int nextCityId, int toll) {
            this.nextCityId = nextCityId;
            this.toll = toll;
        }
    }

    private static class Tolls {
        long totalToll;
        long totalTollTimesTax;
    }

    private static class CityInfo {
        long subtreeSize;
        long subtreeTax;

        public CityInfo(long subtreeSize, long subtreeTax) {
            this.subtreeSize = subtreeSize;
            this.subtreeTax = subtreeTax;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReaderInteger = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = fastReaderInteger.nextInt();
        int[] taxes = new int[cities];

        for (int i = 0; i < taxes.length; i++) {
            taxes[i] = fastReaderInteger.nextInt();
        }

        List<Edge>[] adjacencyList = new List[cities];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < cities - 1; i++) {
            int cityId1 = fastReaderInteger.nextInt() - 1;
            int cityId2 = fastReaderInteger.nextInt() - 1;
            int toll = fastReaderInteger.nextInt();
            adjacencyList[cityId1].add(new Edge(cityId2, toll));
            adjacencyList[cityId2].add(new Edge(cityId1, toll));
        }

        long[] ticketCosts = computeTicketsCosts(adjacencyList, taxes);
        for (long ticketCost : ticketCosts) {
            outputWriter.printLine(ticketCost);
        }
        outputWriter.flush();
    }

    private static long[] computeTicketsCosts(List<Edge>[] adjacencyList, int[] taxes) {
        Tolls[] tolls = new Tolls[adjacencyList.length];
        tolls[0] = new Tolls();
        CityInfo[] cityInfos = new CityInfo[adjacencyList.length];
        boolean[] visited = new boolean[adjacencyList.length];
        visited[0] = true;

        computeTollsAndCityInfo(adjacencyList, visited, taxes, tolls, cityInfos, 0, 0);

        visited = new boolean[adjacencyList.length];
        visited[0] = true;
        computeCosts(adjacencyList, visited, tolls, cityInfos, 0);

        long[] ticketCosts = new long[adjacencyList.length];
        for (int cityId = 0; cityId < ticketCosts.length; cityId++) {
            ticketCosts[cityId] = taxes[cityId] * tolls[cityId].totalToll + tolls[cityId].totalTollTimesTax;
        }
        return ticketCosts;
    }

    private static void computeTollsAndCityInfo(List<Edge>[] adjacencyList, boolean[] visited, int[] taxes,
                                                Tolls[] tolls, CityInfo[] cityInfos, int cityId, long currentToll) {
        tolls[0].totalToll += currentToll;
        tolls[0].totalTollTimesTax += currentToll * taxes[cityId];
        cityInfos[cityId] = new CityInfo(1, taxes[cityId]);

        for (Edge edge : adjacencyList[cityId]) {
            if (!visited[edge.nextCityId]) {
                visited[edge.nextCityId] = true;
                computeTollsAndCityInfo(adjacencyList, visited, taxes, tolls, cityInfos, edge.nextCityId,
                        currentToll + edge.toll);

                cityInfos[cityId].subtreeSize += cityInfos[edge.nextCityId].subtreeSize;
                cityInfos[cityId].subtreeTax += cityInfos[edge.nextCityId].subtreeTax;
            }
        }
    }

    private static void computeCosts(List<Edge>[] adjacencyList, boolean[] visited, Tolls[] tolls, CityInfo[] cityInfos,
                                     int cityId) {
        for (Edge edge : adjacencyList[cityId]) {
            if (!visited[edge.nextCityId]) {
                visited[edge.nextCityId] = true;

                tolls[edge.nextCityId] = new Tolls();
                tolls[edge.nextCityId].totalToll = tolls[cityId].totalToll
                        + edge.toll * (adjacencyList.length - cityInfos[edge.nextCityId].subtreeSize)
                        - edge.toll * cityInfos[edge.nextCityId].subtreeSize;
                tolls[edge.nextCityId].totalTollTimesTax = tolls[cityId].totalTollTimesTax
                        + edge.toll * (cityInfos[0].subtreeTax - cityInfos[edge.nextCityId].subtreeTax)
                        - edge.toll * cityInfos[edge.nextCityId].subtreeTax;

                computeCosts(adjacencyList, visited, tolls, cityInfos, edge.nextCityId);
            }
        }
    }

    private static class FastReaderInteger {
        private static final InputStream in = System.in;
        private static final int bufferSize = 30000;
        private static final byte[] buffer = new byte[bufferSize];
        private static int position = 0;
        private static int byteCount = bufferSize;
        private static byte character;

        FastReaderInteger() throws IOException {
            fill();
        }

        private void fill() throws IOException {
            byteCount = in.read(buffer, 0, bufferSize);
        }

        private int nextInt() throws IOException {
            while (character < '-') {
                character = readByte();
            }
            boolean isNegative = (character == '-');
            if (isNegative) {
                character = readByte();
            }
            int value = character - '0';
            while ((character = readByte()) >= '0' && character <= '9') {
                value = value * 10 + character - '0';
            }
            return isNegative ? -value : value;
        }

        private byte readByte() throws IOException {
            if (position == byteCount) {
                fill();
                position = 0;
            }
            return buffer[position++];
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

        public void flush() {
            writer.flush();
        }
    }
}
