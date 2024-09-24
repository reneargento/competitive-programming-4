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
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = inputReader.nextInt();
        int[] taxes = new int[cities];

        for (int i = 0; i < taxes.length; i++) {
            taxes[i] = inputReader.nextInt();
        }

        List<Edge>[] adjacencyList = new List[cities];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < cities - 1; i++) {
            int cityId1 = inputReader.nextInt() - 1;
            int cityId2 = inputReader.nextInt() - 1;
            int toll = inputReader.nextInt();
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

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String next() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String nextLine() throws IOException {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
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
