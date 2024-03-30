package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/03/24.
 */
public class IdentifyingConcurrentEvents {

    private static class Result {
        int concurrentEvents;
        List<ConcurrentEvent> events;

        public Result(int concurrentEvents, List<ConcurrentEvent> events) {
            this.concurrentEvents = concurrentEvents;
            this.events = events;
        }
    }

    private static class ConcurrentEvent {
        String eventName1;
        String eventName2;

        public ConcurrentEvent(String eventName1, String eventName2) {
            this.eventName1 = eventName1;
            this.eventName2 = eventName2;
        }
    }

    private static final int MAX_VERTICES = 100;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int computations = FastReader.nextInt();
        int caseID = 1;

        while (computations != 0) {
            Map<String, Integer> eventNameToID = new HashMap<>();
            String[] eventIDToName = new String[MAX_VERTICES];
            boolean[][] connected = new boolean[MAX_VERTICES][MAX_VERTICES];

            for (int i = 0; i < computations; i++) {
                int events = FastReader.nextInt();
                int previousEventID = -1;

                for (int e = 0; e < events; e++) {
                    int eventID = getEventID(eventNameToID, eventIDToName, FastReader.next());
                    if (e > 0) {
                        connected[previousEventID][eventID] = true;
                    }
                    previousEventID = eventID;
                }
            }

            int messages = FastReader.nextInt();
            for (int m = 0; m < messages; m++) {
                int eventSend = getEventID(eventNameToID, eventIDToName, FastReader.next());
                int eventReceive = getEventID(eventNameToID, eventIDToName, FastReader.next());
                connected[eventSend][eventReceive] = true;
            }

            Result result = computeConcurrentEvents(connected, eventIDToName, eventNameToID.size());
            outputWriter.print(String.format("Case %d, ", caseID));
            if (result.concurrentEvents == 0) {
                outputWriter.printLine("no concurrent events.");
            } else {
                outputWriter.printLine(String.format("%d concurrent events:", result.concurrentEvents));
                ConcurrentEvent concurrentEvent1 = result.events.get(0);
                outputWriter.print(String.format("(%s,%s) ", concurrentEvent1.eventName1, concurrentEvent1.eventName2));
                if (result.concurrentEvents > 1) {
                    ConcurrentEvent concurrentEvent2 = result.events.get(1);
                    outputWriter.print(String.format("(%s,%s) ", concurrentEvent2.eventName1, concurrentEvent2.eventName2));
                }
                outputWriter.printLine();
            }

            caseID++;
            computations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeConcurrentEvents(boolean[][] connected, String[] eventIDToName, int numberOfEvents) {
        int concurrentEvents = 0;
        List<ConcurrentEvent> events = new ArrayList<>();

        transitiveClosure(connected);

        for (int eventID1 = 0; eventID1 < numberOfEvents; eventID1++) {
            for (int eventID2 = eventID1 + 1; eventID2 < numberOfEvents; eventID2++) {
                if (!connected[eventID1][eventID2] && !connected[eventID2][eventID1]) {
                    concurrentEvents++;
                    if (events.size() < 2) {
                        events.add(new ConcurrentEvent(eventIDToName[eventID1], eventIDToName[eventID2]));
                    }
                }
            }
        }
        return new Result(concurrentEvents, events);
    }

    private static void transitiveClosure(boolean[][] connected) {
        for (int vertex1 = 0; vertex1 < connected.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < connected.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < connected.length; vertex3++) {
                    connected[vertex2][vertex3] |= connected[vertex2][vertex1] && connected[vertex1][vertex3];
                }
            }
        }
    }

    private static int getEventID(Map<String, Integer> eventNameToID, String[] eventIDToName, String eventName) {
        if (!eventNameToID.containsKey(eventName)) {
            int eventID = eventNameToID.size();
            eventNameToID.put(eventName, eventID);
            eventIDToName[eventID] = eventName;
        }
        return eventNameToID.get(eventName);
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
