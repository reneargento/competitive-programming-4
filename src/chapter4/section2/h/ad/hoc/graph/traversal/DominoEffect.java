package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/05/23.
 */
@SuppressWarnings("unchecked")
public class DominoEffect {

    private static class Edge {
        int dominoID1;
        int dominoID2;
        int time;

        public Edge(int dominoID1, int dominoID2, int time) {
            this.dominoID1 = dominoID1;
            this.dominoID2 = dominoID2;
            this.time = time;
        }
    }

    private static class Domino implements Comparable<Domino> {
        int id;
        double totalTime;

        public Domino(int id, double totalTime) {
            this.id = id;
            this.totalTime = totalTime;
        }

        @Override
        public int compareTo(Domino other) {
            return Double.compare(totalTime, other.totalTime);
        }
    }

    private static class Result {
        List<Integer> finalDominoIDs;
        double totalTime;

        public Result(List<Integer> finalDominoIDs, double totalTime) {
            this.finalDominoIDs = finalDominoIDs;
            this.totalTime = totalTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int keyDominoesNumber = FastReader.nextInt();
        int rows = FastReader.nextInt();
        int systemID = 1;

        while (keyDominoesNumber != 0 || rows != 0) {
            List<Edge>[] adjacencyList = new List[keyDominoesNumber];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Edge[] edges = new Edge[rows];

            for (int i = 0; i < rows; i++) {
                int keyDomino1 = FastReader.nextInt() - 1;
                int keyDomino2 = FastReader.nextInt() - 1;
                int time = FastReader.nextInt();

                adjacencyList[keyDomino1].add(new Edge(keyDomino1, keyDomino2, time));
                adjacencyList[keyDomino2].add(new Edge(keyDomino2, keyDomino1, time));
                edges[i] = new Edge(keyDomino1, keyDomino2, time);
            }

            Result result = computeDominoesFall(adjacencyList, edges);
            outputWriter.printLine(String.format("System #%d", systemID));
            outputWriter.print(String.format("The last domino falls after %.1f seconds, ", result.totalTime));
            if (result.finalDominoIDs.size() == 1) {
                outputWriter.printLine(String.format("at key domino %d.", result.finalDominoIDs.get(0) + 1));
            } else {
                outputWriter.printLine(String.format("between key dominoes %d and %d.", result.finalDominoIDs.get(0) + 1,
                        result.finalDominoIDs.get(1) + 1));
            }
            outputWriter.printLine();

            systemID++;
            keyDominoesNumber = FastReader.nextInt();
            rows = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeDominoesFall(List<Edge>[] adjacencyList, Edge[] edges) {
        int finalDominoID1 = 0;
        int finalDominoID2 = -1;
        int[] parentIDs = new int[adjacencyList.length];
        double[] bestTimes = new double[adjacencyList.length];
        Arrays.fill(bestTimes, Double.POSITIVE_INFINITY);
        bestTimes[0] = 0;

        PriorityQueue<Domino> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Domino(0, 0));

        while (!priorityQueue.isEmpty()) {
            Domino domino = priorityQueue.poll();
            if (domino.totalTime > bestTimes[domino.id]) {
                continue;
            }

            for (Edge edge : adjacencyList[domino.id]) {
                double currentTime = bestTimes[edge.dominoID2];
                double candidateTime = bestTimes[domino.id] + edge.time;

                if (candidateTime < currentTime) {
                    bestTimes[edge.dominoID2] = candidateTime;
                    parentIDs[edge.dominoID2] = domino.id;
                    priorityQueue.offer(new Domino(edge.dominoID2, candidateTime));
                }
            }
        }

        double totalTime = 0;
        for (int dominoID = 0; dominoID < bestTimes.length; dominoID++) {
            if (bestTimes[dominoID] > totalTime) {
                finalDominoID1 = dominoID;
                totalTime = bestTimes[dominoID];
            }
        }

        for (Edge edge : edges) {
            int dominoID1 = edge.dominoID1;
            int dominoID2 = edge.dominoID2;

            if (parentIDs[dominoID1] != dominoID2 && parentIDs[dominoID2] != dominoID1) {
                double highestTime = Math.max(bestTimes[dominoID1], bestTimes[dominoID2]);
                double lowestTime = Math.min(bestTimes[dominoID1], bestTimes[dominoID2]);
                double difference = highestTime - lowestTime;

                double bestMeetTime = highestTime + (edge.time - difference) / 2.0;
                if (bestMeetTime > totalTime) {
                    int minDominoID = Math.min(dominoID1, dominoID2);
                    int maxDominoID = Math.max(dominoID1, dominoID2);
                    finalDominoID1 = minDominoID;
                    finalDominoID2 = maxDominoID;
                    totalTime = bestMeetTime;
                }
            }
        }

        List<Integer> finalDominoIDs = new ArrayList<>();
        finalDominoIDs.add(finalDominoID1);
        if (finalDominoID2 != -1) {
            finalDominoIDs.add(finalDominoID2);
        }
        return new Result(finalDominoIDs, totalTime);
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

        public void flush() {
            writer.flush();
        }
    }
}
