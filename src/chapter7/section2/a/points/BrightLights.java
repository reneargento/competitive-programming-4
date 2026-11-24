package chapter7.section2.a.points;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/11/2026.
 */
public class BrightLights {

    private static class Pole implements Comparable<Pole> {
        long x;
        long y;
        int height;
        NormalizedDirection normalizedDirection;
        long distanceFromOrigin;

        public Pole(long x, long y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
            normalizedDirection = computeNormalizedDirection(x, y);
            distanceFromOrigin = (x * x) + (y * y);
        }

        @Override
        public int compareTo(Pole other) {
            if (x != other.x) {
                return Long.compare(x, other.x);
            }
            return Long.compare(y, other.y);
        }
    }

    private static class NormalizedDirection {
        long x;
        long y;

        public NormalizedDirection(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            NormalizedDirection that = (NormalizedDirection) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int polesNumber = FastReader.nextInt();
        int dataSetId = 1;

        while (polesNumber != 0) {
            Pole[] poles = new Pole[polesNumber];
            for (int i = 0; i < polesNumber; i++) {
                poles[i] = new Pole(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }

            List<Pole> invisiblePoles = computeInvisiblePoles(poles);
            outputWriter.printLine(String.format("Data set %d:", dataSetId));
            if (invisiblePoles.isEmpty()) {
                outputWriter.printLine("All the lights are visible.");
            } else {
                outputWriter.printLine("Some lights are not visible:");
                for (int i = 0; i < invisiblePoles.size(); i++) {
                    Pole invisiblePole = invisiblePoles.get(i);
                    outputWriter.print(String.format("x = %d, y = %d", invisiblePole.x, invisiblePole.y));
                    if (i != invisiblePoles.size() - 1) {
                        outputWriter.printLine(";");
                    } else {
                        outputWriter.printLine(".");
                    }
                }
            }

            dataSetId++;
            polesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Pole> computeInvisiblePoles(Pole[] poles) {
        List<Pole> invisiblePoles = new ArrayList<>();
        Arrays.sort(poles, new Comparator<Pole>() {
            @Override
            public int compare(Pole pole1, Pole pole2) {
                return Double.compare(pole1.distanceFromOrigin, pole2.distanceFromOrigin);
            }
        });

        Map<NormalizedDirection, Integer> maxHeightsMap = new HashMap<>();
        for (Pole pole : poles) {
            NormalizedDirection normalizedDirection = pole.normalizedDirection;
            if (!maxHeightsMap.containsKey(normalizedDirection)) {
                maxHeightsMap.put(normalizedDirection, pole.height);
            } else {
                int highestHeight = maxHeightsMap.get(normalizedDirection);
                if (highestHeight >= pole.height) {
                    invisiblePoles.add(pole);
                } else {
                    maxHeightsMap.put(normalizedDirection, pole.height);
                }
            }
        }
        Collections.sort(invisiblePoles);
        return invisiblePoles;
    }

    private static NormalizedDirection computeNormalizedDirection(long x, long y) {
        long gcd = gcd(Math.abs(x), Math.abs(y));
        long normalizedX = x / gcd;
        long normalizedY = y / gcd;
        return new NormalizedDirection(normalizedX, normalizedY);
    }

    private static long gcd(long number1, long number2) {
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);

        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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