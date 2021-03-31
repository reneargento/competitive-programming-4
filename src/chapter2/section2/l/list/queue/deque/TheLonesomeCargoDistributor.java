package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/03/21.
 */
@SuppressWarnings("unchecked")
public class TheLonesomeCargoDistributor {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int stations = FastReader.nextInt();
            int cargoCapacity = FastReader.nextInt();
            int queuesCapacity = FastReader.nextInt();
            int totalCargoesToDeliver = 0;

            LinkedList<Integer>[] stationQueues = new LinkedList[stations];
            for (int i = 0; i < stationQueues.length; i++) {
                stationQueues[i] = new LinkedList<>();
            }

            for (int i = 0; i < stationQueues.length; i++) {
                int currentCargoes = FastReader.nextInt();
                totalCargoesToDeliver += currentCargoes;

                for (int c = 0; c < currentCargoes; c++) {
                    int destination = FastReader.nextInt() - 1;
                    stationQueues[i].addLast(destination);
                }
            }

            long time = computeTimeToFinishJob(stations, cargoCapacity, queuesCapacity, stationQueues,
                    totalCargoesToDeliver);
            outputWriter.printLine(time);
        }
        outputWriter.flush();
    }

    private static long computeTimeToFinishJob(int stations, int cargoCapacity, int queuesCapacity,
                                               Queue<Integer>[] stationQueues, int totalCargoesToDeliver) {
        long time = 0;
        int deliveries = 0;

        int currentStation = 0;
        Deque<Integer> carrierCargoes = new ArrayDeque<>();

        while (deliveries < totalCargoesToDeliver) {
            // Unload
            while ((!carrierCargoes.isEmpty() && stationQueues[currentStation].size() < queuesCapacity)
            || (!carrierCargoes.isEmpty() && carrierCargoes.peek() == currentStation)) {
                int unloadedCargo = carrierCargoes.pop();

                if (unloadedCargo != currentStation) {
                    stationQueues[currentStation].offer(unloadedCargo);
                } else {
                    deliveries++;
                }
                time++;
            }

            // Load
            while (carrierCargoes.size() < cargoCapacity && !stationQueues[currentStation].isEmpty()) {
                int cargoToLoad = stationQueues[currentStation].poll();
                carrierCargoes.push(cargoToLoad);
                time++;
            }

            if (deliveries == totalCargoesToDeliver) {
                break;
            }
            time += 2;

            currentStation = (currentStation + 1) % stations;
        }
        return time;
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
