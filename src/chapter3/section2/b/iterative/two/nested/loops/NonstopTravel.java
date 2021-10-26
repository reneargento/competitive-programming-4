package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/10/21.
 */
public class NonstopTravel {

    private enum Color {
        GREEN, YELLOW, RED
    }

    private static class Signal {
        double location;
        int greenDuration;
        int yellowDuration;
        int redDuration;

        public Signal(double location, int greenDuration, int yellowDuration, int redDuration) {
            this.location = location;
            this.greenDuration = greenDuration;
            this.yellowDuration = yellowDuration;
            this.redDuration = redDuration;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int caseNumber = 1;
        int signalsNumber = FastReader.nextInt();

        while (signalsNumber != -1) {
            Signal[] signals = new Signal[signalsNumber];

            for (int i = 0; i < signals.length; i++) {
                signals[i] = new Signal(FastReader.nextDouble(), FastReader.nextInt(), FastReader.nextInt(),
                        FastReader.nextInt());
            }
            List<String> speeds = computeSpeedsToAvoidRedSignals(signals);
            StringJoiner speedFormatted = new StringJoiner(", ");

            if (speeds.isEmpty()) {
                speedFormatted.add("No acceptable speeds.");
            } else {
                for (String speed : speeds) {
                    speedFormatted.add(speed);
                }
            }
            outputWriter.printLine(String.format("Case %d: %s", caseNumber, speedFormatted));

            caseNumber++;
            signalsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<String> computeSpeedsToAvoidRedSignals(Signal[] signals) {
        List<String> speeds = new ArrayList<>();
        int speedRangeStart = -1;
        int speedRangeEnd = -1;

        for (int speed = 30; speed <= 60; speed++) {
            boolean possible = true;

            for (Signal signal : signals) {
                double timeReached = (3600 * signal.location) / speed;
                Color signalColor = getColorAtTime(signal, timeReached);

                if (signalColor.equals(Color.RED)) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                if (speedRangeStart == -1) {
                    speedRangeStart = speed;
                } else {
                    if (speedRangeEnd == -1) {
                        if (speed == speedRangeStart + 1) {
                            speedRangeEnd = speed;
                        } else {
                            speeds.add(String.valueOf(speedRangeStart));
                            speedRangeStart = speed;
                        }
                    } else {
                        if (speed == speedRangeEnd + 1) {
                            speedRangeEnd = speed;
                        } else {
                            speeds.add(String.format("%d-%d", speedRangeStart, speedRangeEnd));
                            speedRangeStart = speed;
                            speedRangeEnd = -1;
                        }
                    }
                }
            }
        }

        if (speedRangeEnd == -1) {
            if (speedRangeStart != -1) {
                speeds.add(String.valueOf(speedRangeStart));
            }
        } else {
            speeds.add(String.format("%d-%d", speedRangeStart, speedRangeEnd));
        }
        return speeds;
    }

    private static Color getColorAtTime(Signal signal, double time) {
        Color currentColor = Color.GREEN;
        int currentTime = signal.greenDuration;

        while (currentTime < time) {
            switch (currentColor) {
                case GREEN: currentColor = Color.YELLOW;
                    currentTime += signal.yellowDuration;
                    break;
                case YELLOW: currentColor = Color.RED;
                    currentTime += signal.redDuration;
                    if (currentTime == time) {
                        return Color.YELLOW;
                    }
                    break;
                case RED: currentColor = Color.GREEN;
                    currentTime += signal.greenDuration;
            }
        }
        return currentColor;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
