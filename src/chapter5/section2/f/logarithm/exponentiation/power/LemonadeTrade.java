package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/03/25.
 */
public class LemonadeTrade {

    private static final double EPSILON = .00000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int children = FastReader.nextInt();
        Map<String, Double> nameToQuantityMap = new HashMap<>();
        nameToQuantityMap.put("pink", 0.0);

        for (int i = 0; i < children; i++) {
            String offerName = FastReader.next();
            String wantName = FastReader.next();
            double rate = FastReader.nextDouble();

            double currentValue = -100000;
            double candidateValue = -100000;

            if (nameToQuantityMap.containsKey(offerName)) {
                currentValue = nameToQuantityMap.get(offerName);
            }
            if (nameToQuantityMap.containsKey(wantName)) {
                candidateValue = nameToQuantityMap.get(wantName) + logBase2(rate);
            }

            if (Math.abs(currentValue - candidateValue) >= EPSILON) {
                double maxValue = Math.max(currentValue, candidateValue);
                nameToQuantityMap.put(offerName, maxValue);
            }
        }

        if (!nameToQuantityMap.containsKey("blue")) {
            outputWriter.printLine(0);
        } else {
            double exponentOf2 = Math.min(nameToQuantityMap.get("blue"), 4.0);
            double blueLemonade = Math.pow(2, exponentOf2);
            double maximumLemonade = Math.min(blueLemonade, 10);
            outputWriter.printLine(maximumLemonade);
        }
        outputWriter.flush();
    }

    private static double logBase2(double number) {
        return Math.log(number) / Math.log(2);
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

        public void flush() {
            writer.flush();
        }
    }
}
