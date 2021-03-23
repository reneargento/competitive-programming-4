package chapter2.section2.j.stack;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/03/21.
 */
public class AllJustADream {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int lines = FastReader.nextInt();
        Map<String, Integer> eventsMap = new HashMap<>();
        Deque<String> eventsStack = new ArrayDeque<>();
        int eventDepth = 0;

        for (int l = 0; l < lines; l++) {
            String type = FastReader.next();
            switch (type) {
                case "E":
                    String event = FastReader.next();
                    eventsMap.put(event, eventDepth);
                    eventDepth++;
                    eventsStack.push(event);
                    break;
                case "D":
                    int eventsDreamed = FastReader.nextInt();
                    for (int i = 0; i < eventsDreamed; i++) {
                        String eventDreamed = eventsStack.pop();
                        eventsMap.remove(eventDreamed);
                        eventDepth--;
                    }
                    break;
                case "S":
                    String result = checkScenario(eventsMap, eventsStack);
                    outputWriter.printLine(result);
            }
        }
        outputWriter.flush();
    }

    private static String checkScenario(Map<String, Integer> eventsMap, Deque<String> eventsStack) throws IOException {
        int eventsInScenario = FastReader.nextInt();
        int maximumDepthRequired = Integer.MIN_VALUE;
        int minimumDepthToDream = Integer.MAX_VALUE;
        boolean plotError = false;

        for (int i = 0; i < eventsInScenario; i++) {
            String event = FastReader.next();
            boolean cannotHappen = event.charAt(0) == '!';

            if (cannotHappen) {
                String eventDescription = event.substring(1);
                if (eventsMap.containsKey(eventDescription)) {
                    int eventDepth = eventsMap.get(eventDescription);
                    minimumDepthToDream = Math.min(minimumDepthToDream, eventDepth);
                }
            } else {
                if (eventsMap.containsKey(event)) {
                    int eventDepth = eventsMap.get(event);
                    maximumDepthRequired = Math.max(maximumDepthRequired, eventDepth);
                } else {
                    plotError = true;
                }
            }
        }

        if (plotError || minimumDepthToDream <= maximumDepthRequired) {
            return "Plot Error";
        }

        if (minimumDepthToDream == Integer.MAX_VALUE) {
            return "Yes";
        }
        int dreamDepth = eventsStack.size() - minimumDepthToDream;
        return dreamDepth + " Just A Dream";
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
