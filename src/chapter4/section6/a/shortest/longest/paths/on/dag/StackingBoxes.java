package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/05/24.
 */
@SuppressWarnings("unchecked")
public class StackingBoxes {

    private static class Box {
        List<Integer> values;

        public Box(List<Integer> values) {
            this.values = values;
        }
    }

    private static class Edge {
        int vertex1;
        int vertex2;
        int weight;

        Edge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int numberOfBoxes = Integer.parseInt(data[0]);

            Box[] boxes = new Box[numberOfBoxes];
            for (int i = 0; i < boxes.length; i++) {
                List<Integer> values = new ArrayList<>();
                List<String> boxData = getWords(FastReader.getLine());
                for (int d = 0; d < boxData.size(); d++) {
                    values.add(Integer.parseInt(boxData.get(d)));
                }
                Collections.sort(values);
                boxes[i] = new Box(values);
            }

            List<Integer> longestNestingString = computeLongestNestingString(boxes);
            outputWriter.printLine(longestNestingString.size());
            if (!longestNestingString.isEmpty()) {
                outputWriter.print(longestNestingString.get(0));
                for (int i = 1; i < longestNestingString.size(); i++) {
                    outputWriter.print(" " + longestNestingString.get(i));
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeLongestNestingString(Box[] boxes) {
        if (boxes.length == 0) {
            return new ArrayList<>();
        }

        List<Edge>[] adjacent = new List[boxes.length];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int boxID1 = 0; boxID1 < boxes.length; boxID1++) {
            List<Integer> box1Values = boxes[boxID1].values;

            for (int boxID2 = 0; boxID2 < boxes.length; boxID2++) {
                if (boxID1 == boxID2) {
                    continue;
                }
                boolean canFit = true;

                for (int d = 0; d < box1Values.size(); d++) {
                    if (box1Values.get(d) >= boxes[boxID2].values.get(d)) {
                        canFit = false;
                        break;
                    }
                }

                if (canFit) {
                    adjacent[boxID1].add(new Edge(boxID1, boxID2, -1));
                }
            }
        }

        DAGLongestPathWeighted longestPath = new DAGLongestPathWeighted(adjacent);
        return longestPath.getLongestPath();
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
    }

    private static class DAGLongestPathWeighted {
        private static int[] distTo;
        private static Edge[] edgeTo;

        public DAGLongestPathWeighted(List<Edge>[] adjacent) {
            distTo = new int[adjacent.length];
            edgeTo = new Edge[adjacent.length];

            int[] topological = topologicalSort(adjacent);
            for (int vertex : topological) {
                relax(adjacent, vertex);
            }
        }

        private void relax(List<Edge>[] adjacent, int vertex) {
            for (Edge edge : adjacent[vertex]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                    distTo[neighbor] = distTo[vertex] + edge.weight;
                    edgeTo[neighbor] = edge;
                }
            }
        }

        public static int distTo(int vertex) {
            return distTo[vertex];
        }

        public List<Integer> pathTo(int vertex) {
            Stack<Integer> path = new Stack<>();
            for (Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
                path.push(edge.vertex2 + 1);

                if (edgeTo[edge.vertex1] == null) {
                    path.push(edge.vertex1 + 1);
                }
            }

            List<Integer> sequence = new ArrayList<>();
            while (!path.isEmpty()) {
                sequence.add(path.pop());
            }
            return sequence;
        }

        public List<Integer> getLongestPath() {
            int furthestVertex = -1;
            int longestDistance = 0;

            for (int vertex = 0; vertex < distTo.length; vertex++) {
                if (distTo(vertex) < longestDistance) {
                    longestDistance = distTo(vertex);
                    furthestVertex = vertex;
                }
            }

            if (furthestVertex == -1) {
                List<Integer> sequence = new ArrayList<>();
                sequence.add(1);
                return sequence;
            }
            return pathTo(furthestVertex);
        }

        private static int[] topologicalSort(List<Edge>[] adjacencyList) {
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < adjacencyList.length; i++) {
                for (Edge edge : adjacencyList[i]) {
                    inDegrees[edge.vertex2]++;
                }
            }

            Queue<Integer> queue = new LinkedList<>();
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (inDegrees[vertexID] == 0) {
                    queue.add(vertexID);
                }
            }

            int[] topologicalOrder = new int[adjacencyList.length];
            int topologicalOrderIndex = 0;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                topologicalOrder[topologicalOrderIndex++] = currentVertex;

                for (Edge edge : adjacencyList[currentVertex]) {
                    int neighbor = edge.vertex2;
                    inDegrees[neighbor]--;

                    if (inDegrees[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            return topologicalOrder;
        }
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

        public void flush() {
            writer.flush();
        }
    }
}
