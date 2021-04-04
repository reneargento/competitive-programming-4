package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/04/21.
 */
@SuppressWarnings("unchecked")
public class DrutojanExpress {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Map<String, Integer> nameToIndexMap = createNameToIndexMap();
        String[] indexToNameMap = {"Ja", "Sam", "Sha", "Sid", "Tan"};

        for (int t = 1; t <= tests; t++) {
            int timeSeating = FastReader.nextInt();
            int travelTime = FastReader.nextInt();
            String currentPerson = FastReader.next();

            Queue<String>[] queues = createQueues();
            for (int i = 0; i < queues.length; i++) {
                int names = FastReader.nextInt();
                for (int n = 0; n < names; n++) {
                    queues[i].offer(FastReader.next());
                }
            }

            int[] timePerPerson = computeTimePerPerson(timeSeating, travelTime, currentPerson, queues,
                    nameToIndexMap);

            outputWriter.printLine(String.format("Case %d:", t));
            for (int i = 0; i < indexToNameMap.length; i++) {
                String name = indexToNameMap[i];
                outputWriter.printLine(String.format("%s %d", name, timePerPerson[i]));
            }
        }
        outputWriter.flush();
    }

    private static int[] computeTimePerPerson(int timeSeating, int travelTime, String currentPerson,
                                              Queue<String>[] queues, Map<String, Integer> nameToIndexMap) {
        int[] timePerPerson = new int[5];
        int currentTime = 0;

        while (currentTime < travelTime) {
            int personIndex = nameToIndexMap.get(currentPerson);
            int currentSeatingTime = Math.min(timeSeating, travelTime - currentTime);

            timePerPerson[personIndex] += currentSeatingTime;
            currentPerson = queues[personIndex].poll();
            queues[personIndex].offer(currentPerson);

            currentTime += 2 + currentSeatingTime;
        }
        return timePerPerson;
    }

    private static Map<String, Integer> createNameToIndexMap() {
        Map<String, Integer> nameToIndexMap = new HashMap<>();
        nameToIndexMap.put("Ja", 0);
        nameToIndexMap.put("Sam", 1);
        nameToIndexMap.put("Sha", 2);
        nameToIndexMap.put("Sid", 3);
        nameToIndexMap.put("Tan", 4);
        return nameToIndexMap;
    }

    private static Queue<String>[] createQueues() {
        Queue<String>[] queues = new LinkedList[5];
        for (int i = 0; i < queues.length; i++) {
            queues[i] = new LinkedList<>();
        }
        return queues;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
