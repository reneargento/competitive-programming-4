package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/01/24.
 */
@SuppressWarnings("unchecked")
public class BackpackBuddies {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cabins = FastReader.nextInt();
        int trails = FastReader.nextInt();

        List<Edge>[] adjacencyList = new List[cabins];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int t = 0; t < trails; t++) {
            int cabinID1 = FastReader.nextInt();
            int cabinID2 = FastReader.nextInt();
            int hours = FastReader.nextInt();
            adjacencyList[cabinID1].add(new Edge(cabinID2, hours));
            adjacencyList[cabinID2].add(new Edge(cabinID1, hours));
        }

        int waitingHours = computeWaitingHours(adjacencyList);
        outputWriter.printLine(waitingHours);
        outputWriter.flush();
    }

    private static int computeWaitingHours(List<Edge>[] adjacencyList) {
        int startCabin = 0;
        int destinationCabin = adjacencyList.length - 1;

        Dijkstra mrDayDijkstra = new Dijkstra(adjacencyList, startCabin, destinationCabin, true);
        Dijkstra drKnightDijkstra = new Dijkstra(adjacencyList, startCabin, destinationCabin, false);
        return mrDayDijkstra.bestTotalTime[destinationCabin] - drKnightDijkstra.bestTotalTime[destinationCabin];
    }

    private static class Edge {
        private final int vertex2;
        private final int hours;

        public Edge(int vertex2, int hours) {
            this.vertex2 = vertex2;
            this.hours = hours;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            int totalTime;
            int currentTime;

            public Vertex(int id, int totalTime, int currentTime) {
                this.id = id;
                this.totalTime = totalTime;
                this.currentTime = currentTime;
            }

            @Override
            public int compareTo(Vertex other) {
                return Integer.compare(totalTime, other.totalTime);
            }
        }

        private final int[] bestTotalTime;
        private final PriorityQueue<Vertex> priorityQueue;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target, boolean isMrDay) {
            bestTotalTime = new int[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(bestTotalTime, Integer.MAX_VALUE);
            bestTotalTime[source] = 0;
            priorityQueue.offer(new Vertex(source, 0, 8));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(adjacencyList, vertex, isMrDay);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, boolean isMrDay) {
            if (bestTotalTime[vertex.id] < vertex.totalTime) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int nextHours;
                int nextTotalTime;
                if (vertex.currentTime + edge.hours > 20) {
                    int hoursUntilNextMorning = (24 - vertex.currentTime + 8);

                    if (isMrDay) {
                        nextHours = 8 + edge.hours;
                        nextTotalTime = bestTotalTime[vertex.id] + hoursUntilNextMorning + edge.hours;
                    } else {
                        int hoursWalkedCurrentDay = 20 - vertex.currentTime;
                        int hoursRemaining = edge.hours - hoursWalkedCurrentDay;
                        nextHours = 8 + hoursRemaining;
                        nextTotalTime = bestTotalTime[vertex.id] + hoursUntilNextMorning + hoursRemaining;
                    }
                } else {
                    nextHours = vertex.currentTime + edge.hours;
                    nextTotalTime = bestTotalTime[vertex.id] + edge.hours;
                }

                int neighbor = edge.vertex2;

                if (bestTotalTime[neighbor] > nextTotalTime) {
                    bestTotalTime[neighbor] = nextTotalTime;
                    priorityQueue.offer(new Vertex(neighbor, nextTotalTime, nextHours));
                }
            }
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

        public void flush() {
            writer.flush();
        }
    }
}
