package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 31/03/22.
 */
public class ThroughTheDesert {

    private static final double EPSILON = 0.000000000001;

    private enum EventType {
        Consumption, Leak, Gas, Mechanic, Goal
    }

    private static class Event {
        int kilometer;
        EventType eventType;
        int consumption;

        public Event(int kilometer, EventType eventType, int consumption) {
            this.kilometer = kilometer;
            this.eventType = eventType;
            this.consumption = consumption;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (true) {
            String[] data = line.split(" ");
            if (data[3].equals("0")) {
                break;
            }

            List<Event> events = new ArrayList<>();
            events.add(new Event(0, EventType.Consumption, Integer.parseInt(data[3])));

            while (true) {
                line = FastReader.getLine();
                data = line.split(" ");

                int kilometer = Integer.parseInt(data[0]);
                int consumption = 0;
                EventType eventType;
                switch (data[1]) {
                    case "Fuel":
                        eventType = EventType.Consumption;
                        consumption = Integer.parseInt(data[3]);
                        break;
                    case "Leak":
                        eventType = EventType.Leak;
                        break;
                    case "Gas":
                        eventType = EventType.Gas;
                        break;
                    case "Mechanic":
                        eventType = EventType.Mechanic;
                        break;
                    default:
                        eventType = EventType.Goal;
                }

                events.add(new Event(kilometer, eventType, consumption));
                if (eventType.equals(EventType.Goal)) {
                    break;
                }
            }

            double tankVolume = getSmallestPossibleTankVolume(events);
            outputWriter.printLine(String.format("%.3f", tankVolume));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double getSmallestPossibleTankVolume(List<Event> events) {
        double low = 0;
        double high = 10000000;

        while (low + EPSILON < high) {
            double tankVolume = low + (high - low) / 2;
            double volume = volumeLeftAfterJourney(events, tankVolume);

            if (Math.abs(volume) <= EPSILON) {
                return tankVolume;
            } else if (volume < EPSILON) {
                low = tankVolume;
            } else {
                high = tankVolume;
            }
        }
        return high;
    }

    private static double volumeLeftAfterJourney(List<Event> events, double tankVolume) {
        double litresPer100Km = 0;
        int kilometer = 0;
        int leaks = 0;
        double currentVolume = tankVolume;

        for (Event event : events) {
            int distanceTravelled = event.kilometer - kilometer;
            double volumeUsed = (distanceTravelled / 100.0) * litresPer100Km + (distanceTravelled * leaks);
            currentVolume -= volumeUsed;

            if (currentVolume < EPSILON) {
                return -1;
            }

            switch (event.eventType) {
                case Consumption:
                    litresPer100Km = event.consumption;
                    break;
                case Leak:
                    leaks++;
                    break;
                case Gas:
                    currentVolume = tankVolume;
                    break;
                case Mechanic:
                    leaks = 0;
            }
            kilometer = event.kilometer;
        }
        return currentVolume;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
