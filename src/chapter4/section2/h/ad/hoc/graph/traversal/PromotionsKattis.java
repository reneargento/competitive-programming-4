package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/04/23.
 */
@SuppressWarnings("unchecked")
public class PromotionsKattis {

    private static class Result {
        int certainlyPromotedLow;
        int certainlyPromotedHigh;
        int noPossibilityPromotion;

        public Result(int certainlyPromotedLow, int certainlyPromotedHigh, int noPossibilityPromotion) {
            this.certainlyPromotedLow = certainlyPromotedLow;
            this.certainlyPromotedHigh = certainlyPromotedHigh;
            this.noPossibilityPromotion = noPossibilityPromotion;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lowerPromotions = inputReader.nextInt();
        int higherPromotions = inputReader.nextInt();
        List<Integer>[] adjacencyList = new List[inputReader.nextInt()];
        int precedenceRules = inputReader.nextInt();
        int[] inDegrees = new int[adjacencyList.length];

        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < precedenceRules; i++) {
            int employeeID1 = inputReader.nextInt();
            int employeeID2 = inputReader.nextInt();
            adjacencyList[employeeID1].add(employeeID2);
            inDegrees[employeeID2]++;
        }

        Result result = computePossiblePromotions(adjacencyList, inDegrees, lowerPromotions, higherPromotions);
        outputWriter.printLine(result.certainlyPromotedLow);
        outputWriter.printLine(result.certainlyPromotedHigh);
        outputWriter.printLine(result.noPossibilityPromotion);
        outputWriter.flush();
    }

    private static Result computePossiblePromotions(List<Integer>[] adjacencyList, int[] inDegrees, int lowerPromotions,
                                                    int higherPromotions) {
        int certainlyPromotedLow = 0;
        int certainlyPromotedHigh = 0;
        int noPossibilityPromotion = 0;

        int employees = adjacencyList.length;
        int[] predecessorsCount = new int[employees];
        int[] successorsCount = new int[employees];

        boolean[][] predecessors = computePredecessors(adjacencyList, inDegrees);
        computePredecessorsSuccessorsCount(predecessors, predecessorsCount, successorsCount);

        for (int employeeID = 0; employeeID < adjacencyList.length; employeeID++) {
            int promotionIndex = employees - successorsCount[employeeID];

            if (promotionIndex < lowerPromotions) {
                certainlyPromotedLow++;
            } else if (promotionIndex < higherPromotions) {
                certainlyPromotedHigh++;
            } else if (higherPromotions < predecessorsCount[employeeID]) {
                noPossibilityPromotion++;
            }
        }
        certainlyPromotedHigh += certainlyPromotedLow;
        return new Result(certainlyPromotedLow, certainlyPromotedHigh, noPossibilityPromotion);
    }

    private static boolean[][] computePredecessors(List<Integer>[] adjacencyList, int[] inDegrees) {
        boolean[][] predecessors = new boolean[adjacencyList.length][adjacencyList.length];

        Queue<Integer> queue = new LinkedList<>();
        for (int employeeID = 0; employeeID < adjacencyList.length; employeeID++) {
            predecessors[employeeID][employeeID] = true;

            if (inDegrees[employeeID] == 0) {
                queue.offer(employeeID);
            }
        }

        while (!queue.isEmpty()) {
            int employeeID = queue.poll();

            for (int neighborID : adjacencyList[employeeID]) {
                addAllPredecessors(predecessors[employeeID], predecessors[neighborID]);

                inDegrees[neighborID]--;
                if (inDegrees[neighborID] == 0) {
                    queue.offer(neighborID);
                }
            }
        }
        return predecessors;
    }

    private static void addAllPredecessors(boolean[] predecessors1, boolean[] predecessors2) {
        for (int i = 0; i < predecessors1.length; i++) {
            if (predecessors1[i]) {
                predecessors2[i] = true;
            }
        }
    }

    private static void computePredecessorsSuccessorsCount(boolean[][] predecessors, int[] predecessorsCount,
                                                           int[] successorsCount) {
        for (int employeeID1 = 0; employeeID1 < predecessors.length; employeeID1++) {
            for (int employeeID2 = 0; employeeID2 < predecessors.length; employeeID2++) {
                if (predecessors[employeeID1][employeeID2]) {
                    predecessorsCount[employeeID1]++;
                    successorsCount[employeeID2]++;
                }
            }
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
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

        private int nextInt() throws IOException {
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

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
