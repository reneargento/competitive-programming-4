package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/01/24.
 */
@SuppressWarnings("unchecked")
public class FromDuskTillDawn {

    private static class State implements Comparable<State> {
        int cityID;
        int currentTime;
        int bloodLitresUsed;

        public State(int cityID, int currentTime, int bloodLitresUsed) {
            this.cityID = cityID;
            this.currentTime = currentTime;
            this.bloodLitresUsed = bloodLitresUsed;
        }

        @Override
        public int compareTo(State other) {
            if (bloodLitresUsed != other.bloodLitresUsed) {
                return Integer.compare(bloodLitresUsed, other.bloodLitresUsed);
            }
            if ((currentTime <= 12 && other.currentTime <= 12)
                    || (currentTime >= 18 && other.currentTime >= 18) ) {
                return Integer.compare(currentTime, other.currentTime);
            }
            return 0;
        }
    }

    private static class Edge {
        int cityID2;
        int departureTime;
        int arrivalTime;

        public Edge(int cityID2, int departureTime, int arrivalTime) {
            this.cityID2 = cityID2;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            Map<String, Integer> cityNameToIDMap = new HashMap<>();
            List<Edge>[] adjacencyList = new List[101];
            List<Edge>[] edgesPerCityID = new List[adjacencyList.length];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
                edgesPerCityID[i] = new ArrayList<>();
            }

            int routes = FastReader.nextInt();
            for (int r = 0; r < routes; r++) {
                int cityID1 = getCityID(cityNameToIDMap, FastReader.next());
                int cityID2 = getCityID(cityNameToIDMap, FastReader.next());
                int departureTime = FastReader.nextInt();
                int duration = FastReader.nextInt();
                int arrivalTime = (departureTime + duration) % 24;

                if ((departureTime >= 18 || departureTime <= 6)
                        && (arrivalTime <= 6 || arrivalTime >= 18)
                        && duration <= 12) {
                    Edge edge = new Edge(cityID2, departureTime, arrivalTime);
                    adjacencyList[cityID1].add(edge);
                    edgesPerCityID[cityID1].add(edge);
                }
            }
            int startCityID = getCityID(cityNameToIDMap, FastReader.next());
            int destinationCityID = getCityID(cityNameToIDMap, FastReader.next());

            long litresOfBloodUsed = computeLitresOfBloodUsed(adjacencyList, edgesPerCityID[startCityID],
                    startCityID, destinationCityID);
            outputWriter.printLine(String.format("Test Case %d.", t));
            if (litresOfBloodUsed == -1) {
                outputWriter.printLine("There is no route Vladimir can take.");
            } else {
                outputWriter.printLine(String.format("Vladimir needs %d litre(s) of blood.", litresOfBloodUsed));
            }
        }
        outputWriter.flush();
    }

    private static long computeLitresOfBloodUsed(List<Edge>[] adjacencyList, List<Edge> startEdges,
                                                 int startCityID, int destinationCityID) {
        if (startCityID == destinationCityID) {
            return 0;
        }
        PriorityQueue<State> priorityQueue = new PriorityQueue<>();
        int[] bestStatePerCity = new int[adjacencyList.length];
        Arrays.fill(bestStatePerCity, Integer.MAX_VALUE);

        for (Edge edge : startEdges) {
            State state = new State(startCityID, edge.departureTime, 0);
            priorityQueue.offer(state);
        }
        bestStatePerCity[startCityID] = 0;

        while (!priorityQueue.isEmpty()) {
            State state = priorityQueue.poll();
            if (state.cityID == destinationCityID) {
                break;
            }

            for (Edge edge : adjacencyList[state.cityID]) {
                int nextBloodLitresUsed = state.bloodLitresUsed;
                if ((state.currentTime < 12 && edge.departureTime > 12)
                        || (edge.departureTime < state.currentTime
                            && (state.currentTime < 12 || edge.departureTime >= 18))) {
                    nextBloodLitresUsed++;
                }

                State nextState = new State(edge.cityID2, edge.arrivalTime, nextBloodLitresUsed);
                if (bestStatePerCity[edge.cityID2] >= nextState.bloodLitresUsed) {
                    bestStatePerCity[edge.cityID2] = nextState.bloodLitresUsed;
                    priorityQueue.offer(nextState);
                }
            }
        }

        if (bestStatePerCity[destinationCityID] != Integer.MAX_VALUE) {
            return bestStatePerCity[destinationCityID];
        } else {
            return -1;
        }
    }

    private static int getCityID(Map<String, Integer> cityNameToIDMap, String cityName) {
        if (!cityNameToIDMap.containsKey(cityName)) {
            cityNameToIDMap.put(cityName, cityNameToIDMap.size());
        }
        return cityNameToIDMap.get(cityName);
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
