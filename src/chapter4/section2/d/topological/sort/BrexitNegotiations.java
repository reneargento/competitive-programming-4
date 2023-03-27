package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/03/23.
 */
@SuppressWarnings("unchecked")
public class BrexitNegotiations {

    private static class Topic implements Comparable<Topic> {
        int id;
        long timeNeeded;

        public Topic(int id, long timeNeeded) {
            this.id = id;
            this.timeNeeded = timeNeeded;
        }

        @Override
        public int compareTo(Topic other) {
            return Long.compare(other.timeNeeded, timeNeeded);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] topicTimes = new int[FastReader.nextInt()];
        List<Integer>[] adjacencyList = new List[topicTimes.length];
        List<Integer>[] adjacencyListTransposed = new List[topicTimes.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
            adjacencyListTransposed[i] = new ArrayList<>();
        }
        int[] inDegrees = new int[adjacencyList.length];

        for (int topicID = 0; topicID < topicTimes.length; topicID++) {
            topicTimes[topicID] = FastReader.nextInt();
            int dependencies = FastReader.nextInt();

            for (int i = 0; i < dependencies; i++) {
                int dependencyID = FastReader.nextInt() - 1;
                adjacencyList[dependencyID].add(topicID);
                adjacencyListTransposed[topicID].add(dependencyID);
                inDegrees[topicID]++;
            }
        }

        long longestMeeting = computeLongestMeeting(topicTimes, adjacencyList, adjacencyListTransposed, inDegrees);
        outputWriter.printLine(longestMeeting);
        outputWriter.flush();
    }

    private static long computeLongestMeeting(int[] topicTimes, List<Integer>[] adjacencyList,
                                              List<Integer>[] adjacencyListTransposed, int[] inDegrees) {
        long longestMeeting = 0;
        int meetingsHeld = 0;
        PriorityQueue<Topic> priorityQueue = new PriorityQueue<>();

        int[] maxTimeReachable = computeMaxTimeReachable(adjacencyListTransposed, topicTimes);

        for (int topicID = 0; topicID < inDegrees.length; topicID++) {
            if (inDegrees[topicID] == 0) {
                priorityQueue.offer(new Topic(topicID, maxTimeReachable[topicID]));
            }
        }

        while (!priorityQueue.isEmpty()) {
            Topic topic = priorityQueue.poll();
            longestMeeting = Math.max(longestMeeting, meetingsHeld + topic.timeNeeded);
            meetingsHeld++;

            for (int neighborID : adjacencyList[topic.id]) {
                inDegrees[neighborID]--;
                if (inDegrees[neighborID] == 0) {
                    priorityQueue.offer(new Topic(neighborID, maxTimeReachable[neighborID]));
                }
            }
        }
        return longestMeeting;
    }

    private static int[] computeMaxTimeReachable(List<Integer>[] adjacencyListTransposed, int[] topicTimes) {
        int[] maxTimeReachable = new int[topicTimes.length];
        boolean[] visited = new boolean[topicTimes.length];
        Topic[] topics = new Topic[topicTimes.length];
        for (int topicID = 0; topicID < topicTimes.length; topicID++) {
            topics[topicID] = new Topic(topicID, topicTimes[topicID]);
        }

        Arrays.sort(topics);
        for (Topic topic : topics) {
            if (!visited[topic.id]) {
                depthFirstSearch(adjacencyListTransposed, visited, maxTimeReachable, topicTimes[topic.id], topic.id);
            }
        }
        return maxTimeReachable;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int[] maxTimeReachable,
                                         int maxTimeNeeded, int topicID) {
        visited[topicID] = true;
        maxTimeReachable[topicID] = maxTimeNeeded;

        for (int neighborID : adjacencyList[topicID]) {
            if (!visited[neighborID]) {
                depthFirstSearch(adjacencyList, visited, maxTimeReachable, maxTimeNeeded, neighborID);
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
