package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/04/23.
 */
@SuppressWarnings("unchecked")
public class TouristGuide {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int locations = FastReader.nextInt();
        int cityMapNumber = 1;

        while (locations != 0) {
            List<Integer>[] adjacencyList = new List[locations];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Map<String, Integer> locationNameToID = new HashMap<>();
            String[] locationIDToName = new String[locations];

            for (int locationID = 0; locationID < locations; locationID++) {
                String locationName = FastReader.next();
                locationNameToID.put(locationName, locationID);
                locationIDToName[locationID] = locationName;
            }

            int routes = FastReader.nextInt();
            for (int i = 0; i < routes; i++) {
                String locationName1 = FastReader.next();
                String locationName2 = FastReader.next();

                int locationID1 = locationNameToID.get(locationName1);
                int locationID2 = locationNameToID.get(locationName2);
                adjacencyList[locationID1].add(locationID2);
                adjacencyList[locationID2].add(locationID1);
            }

            ArticulationPoints.computeArticulationPoints(adjacencyList);
            List<Integer> cameraLocationIDs = new ArrayList<>(ArticulationPoints.articulationPoints);

            List<String> cameraLocationNames = new ArrayList<>();
            for (Integer cameraLocationID : cameraLocationIDs) {
                String cameraLocationName = locationIDToName[cameraLocationID];
                cameraLocationNames.add(cameraLocationName);
            }
            Collections.sort(cameraLocationNames);

            if (cityMapNumber > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("City map #%d: %d camera(s) found", cityMapNumber,
                    cameraLocationNames.size()));
            for (String cameraLocationName : cameraLocationNames) {
                outputWriter.printLine(cameraLocationName);
            }

            cityMapNumber++;
            locations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class ArticulationPoints {
        private static int[] time;
        private static int[] low;
        private static int[] parent;
        private static int count;
        public static Set<Integer> articulationPoints;

        public static void computeArticulationPoints(List<Integer>[] adjacencyList) {
            time = new int[adjacencyList.length];
            low = new int[adjacencyList.length];
            parent = new int[adjacencyList.length];
            count = 0;
            articulationPoints = new HashSet<>();

            Arrays.fill(time, -1);
            Arrays.fill(low, -1);
            Arrays.fill(parent, -1);

            for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
                if (time[vertex] == -1) {
                    dfs(adjacencyList, vertex);
                }
            }
        }

        private static void dfs(List<Integer>[] adjacencyList, int vertex) {
            time[vertex] = count;
            low[vertex] = count;
            count++;

            int children = 0;

            for (int neighbor : adjacencyList[vertex]) {
                if (time[neighbor] == -1) {
                    parent[neighbor] = vertex;
                    children++;

                    dfs(adjacencyList, neighbor);

                    low[vertex] = Math.min(low[vertex], low[neighbor]);

                    if (parent[vertex] == -1 && children > 1) {
                        articulationPoints.add(vertex);
                    } else if (parent[vertex] != -1 && low[neighbor] >= time[vertex]) {
                        articulationPoints.add(vertex);
                    }
                } else if (parent[vertex] != neighbor) {
                    low[vertex] = Math.min(low[vertex], time[neighbor]);
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
