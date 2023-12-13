package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/12/23.
 */
@SuppressWarnings("unchecked")
public class SpreadingTheNews {

    private static class Result {
        int maxDailyBoomSize;
        int firstBoomDay;

        public Result(int maxDailyBoomSize, int firstBoomDay) {
            this.maxDailyBoomSize = maxDailyBoomSize;
            this.firstBoomDay = firstBoomDay;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int employees = FastReader.nextInt();

        List<Integer>[] adjacencyList = new List[employees];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int employeeID = 0; employeeID < employees; employeeID++) {
            int friendsNumber = FastReader.nextInt();

            for (int f = 0; f < friendsNumber; f++) {
                int friendID = FastReader.nextInt();
                adjacencyList[employeeID].add(friendID);
            }
        }

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            int sourceID = FastReader.nextInt();
            Result result = spreadNews(adjacencyList, sourceID);
            if (result != null) {
                outputWriter.printLine(result.maxDailyBoomSize + " " + result.firstBoomDay);
            } else {
                outputWriter.printLine("0");
            }
        }
        outputWriter.flush();
    }

    private static Result spreadNews(List<Integer>[] adjacencyList, int sourceID) {
        int maxDailyBoomSize = 0;
        int firstBoomDay = 0;

        int[] totalNewsInDay = new int[adjacencyList.length];
        int[] dayNewsReceived = new int[adjacencyList.length];
        Arrays.fill(dayNewsReceived, -1);
        Queue<Integer> queue = new LinkedList<>();

        dayNewsReceived[sourceID] = 0;
        queue.offer(sourceID);

        while (!queue.isEmpty()) {
            int employeeID = queue.poll();

            for (int friendID : adjacencyList[employeeID]) {
                if (dayNewsReceived[friendID] == -1) {
                    int friendDayNewsReceived = dayNewsReceived[employeeID] + 1;
                    dayNewsReceived[friendID] = friendDayNewsReceived;
                    queue.offer(friendID);

                    totalNewsInDay[friendDayNewsReceived]++;
                    if (totalNewsInDay[friendDayNewsReceived] > maxDailyBoomSize) {
                        maxDailyBoomSize = totalNewsInDay[friendDayNewsReceived];
                    }
                }
            }
        }

        if (maxDailyBoomSize == 0) {
            return null;
        }
        for (int day = 0; day < totalNewsInDay.length; day++) {
            if (totalNewsInDay[day] == maxDailyBoomSize) {
                firstBoomDay = day;
                break;
            }
        }
        return new Result(maxDailyBoomSize, firstBoomDay);
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
