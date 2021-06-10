package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/06/21.
 */
public class CakeyMcCakeFace {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int entryEventsNumber = FastReader.nextInt();
        int exitEventsNumber = FastReader.nextInt();
        int[] entryEvents = readEvents(entryEventsNumber);
        int[] exitEvents = readEvents(exitEventsNumber);

        Map<Integer, Integer> cookingTimeFrequencyMap = computeCookingTimeFrequencyMap(entryEvents, exitEvents);
        int bestCookingTimeGuess = makeBestCookingTimeGuess(cookingTimeFrequencyMap);
        outputWriter.printLine(bestCookingTimeGuess);
        outputWriter.flush();
    }

    private static int[] readEvents(int eventsNumber) throws IOException {
        int[] events = new int[eventsNumber];
        for (int i = 0; i < events.length; i++) {
            events[i] = FastReader.nextInt();
        }
        return events;
    }

    private static Map<Integer, Integer> computeCookingTimeFrequencyMap(int[] entryEvents, int[] exitEvents) {
        Map<Integer, Integer> cookingTimeFrequencyMap = new HashMap<>();
        for (int entryTime : entryEvents) {
            for (int exitTime : exitEvents) {
                if (entryTime <= exitTime) {
                    int cookingTime = exitTime - entryTime;
                    int frequency = cookingTimeFrequencyMap.getOrDefault(cookingTime, 0);
                    cookingTimeFrequencyMap.put(cookingTime, frequency + 1);
                }
            }
        }
        return cookingTimeFrequencyMap;
    }

    private static int makeBestCookingTimeGuess(Map<Integer, Integer> cookingTimeFrequencyMap) {
        int highestFrequencyCookingTime = Integer.MAX_VALUE;
        int highestFrequency = 0;

        for (Map.Entry<Integer, Integer> cookingTimeFrequency : cookingTimeFrequencyMap.entrySet()) {
            int cookingTime = cookingTimeFrequency.getKey();
            int frequency = cookingTimeFrequency.getValue();
            if (frequency > highestFrequency
                    || (frequency == highestFrequency && cookingTime < highestFrequencyCookingTime)) {
                highestFrequencyCookingTime = cookingTime;
                highestFrequency = frequency;
            }
        }

        if (highestFrequency == 0) {
            return 0;
        }
        return highestFrequencyCookingTime;
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
