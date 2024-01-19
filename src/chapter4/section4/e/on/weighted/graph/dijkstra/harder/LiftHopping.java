package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/01/24.
 */
@SuppressWarnings("unchecked")
public class LiftHopping {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int elevators = Integer.parseInt(data[0]);
            int targetFloor = Integer.parseInt(data[1]);

            List<Edge>[] adjacencyList = new List[101];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int[] travelSeconds = new int[elevators];
            for (int i = 0; i < travelSeconds.length; i++) {
                travelSeconds[i] = FastReader.nextInt();
            }
            List<Integer> elevatorsIn0Floor = new ArrayList<>();

            for (int elevatorID = 0; elevatorID < elevators; elevatorID++) {
                String[] floorsReachable = FastReader.getLine().split(" ");
                for (int f = 0; f < floorsReachable.length - 1; f++) {
                    int currentFloor = Integer.parseInt(floorsReachable[f]);
                    int nextFloor = Integer.parseInt(floorsReachable[f + 1]);
                    int time = (nextFloor - currentFloor) * travelSeconds[elevatorID];
                    adjacencyList[currentFloor].add(new Edge(nextFloor, time, elevatorID));
                    adjacencyList[nextFloor].add(new Edge(currentFloor, time, elevatorID));

                    if (currentFloor == 0) {
                        elevatorsIn0Floor.add(elevatorID);
                    }
                }
            }

            Dijkstra dijkstra = new Dijkstra(adjacencyList, elevators, 0, targetFloor, elevatorsIn0Floor);
            if (dijkstra.hasPathTo(targetFloor)) {
                long minimumTime = Long.MAX_VALUE;

                for (int elevatorID = 0; elevatorID < dijkstra.distTo[0].length; elevatorID++) {
                    if (dijkstra.distTo[targetFloor][elevatorID] != Dijkstra.MAX_VALUE) {
                        minimumTime = Math.min(minimumTime, dijkstra.distTo[targetFloor][elevatorID]);
                    }
                }
                outputWriter.printLine(minimumTime);
            } else {
                outputWriter.printLine("IMPOSSIBLE");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static class Edge {
        private final int vertex2;
        private final long distance;
        private final int elevatorID;

        public Edge(int vertex2, long distance, int elevatorID) {
            this.vertex2 = vertex2;
            this.distance = distance;
            this.elevatorID = elevatorID;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            long distance;
            int elevatorID;

            public Vertex(int id, long distance, int elevatorID) {
                this.id = id;
                this.distance = distance;
                this.elevatorID = elevatorID;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final long[][] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private static final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int elevators, int source, int target,
                        List<Integer> elevatorsIn0Floor) {
            distTo = new long[adjacencyList.length][elevators];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            for (int floor = 0; floor < adjacencyList.length; floor++) {
                Arrays.fill(distTo[floor], MAX_VALUE);
            }
            for (int elevatorID : elevatorsIn0Floor) {
                distTo[source][elevatorID] = 0;
                priorityQueue.offer(new Vertex(source, 0, elevatorID));
            }

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(adjacencyList, vertex);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            if (distTo[vertex.id][vertex.elevatorID] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;
                long candidateDistance = distTo[vertex.id][vertex.elevatorID] + edge.distance;

                if (vertex.elevatorID != edge.elevatorID) {
                    candidateDistance += 60;
                }

                if (distTo[neighbor][edge.elevatorID] > candidateDistance) {
                    distTo[neighbor][edge.elevatorID] = candidateDistance;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor][edge.elevatorID], edge.elevatorID));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            for (int elevatorID = 0; elevatorID < distTo[0].length; elevatorID++) {
                if (distTo[vertex][elevatorID] != MAX_VALUE) {
                    return true;
                }
            }
            return false;
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

        public void flush() {
            writer.flush();
        }
    }
}
