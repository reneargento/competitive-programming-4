package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/04/25.
 */
public class Humidex {

    private static class Result {
        double temperature;
        double dewpoint;
        double humidex;

        public Result(double temperature, double dewpoint, double humidex) {
            this.temperature = temperature;
            this.dewpoint = dewpoint;
            this.humidex = humidex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char type1 = FastReader.next().charAt(0);
        while (type1 != 'E') {
            double number1 = FastReader.nextDouble();
            char type2 = FastReader.next().charAt(0);
            double number2 = FastReader.nextDouble();

            Result result = computeValues(type1, number1, type2, number2);
            outputWriter.printLine(String.format("T %.1f D %.1f H %.1f", result.temperature, result.dewpoint,
                    result.humidex));
            type1 = FastReader.next().charAt(0);
        }
        outputWriter.flush();
    }

    private static Result computeValues(char type1, double number1, char type2, double number2) {
        double temperature = Double.POSITIVE_INFINITY;
        double dewpoint = Double.POSITIVE_INFINITY;
        double humidex = Double.POSITIVE_INFINITY;

        if (type1 == 'T') {
            temperature = number1;
        } else if (type1 == 'D') {
            dewpoint = number1;
        } else {
            humidex = number1;
        }
        if (type2 == 'T') {
            temperature = number2;
        } else if (type2 == 'D') {
            dewpoint = number2;
        } else {
            humidex = number2;
        }

        if (dewpoint != Double.POSITIVE_INFINITY) {
            double e = 6.11 * Math.pow(Math.E, 5417.7530 * ((1 / 273.16) - (1 / (dewpoint + 273.16))));
            double h = (0.5555) * (e - 10.0);

            if (temperature == Double.POSITIVE_INFINITY) {
                temperature = humidex - h;
            } else {
                humidex = temperature + h;
            }
        } else {
            double HTBy05Plus10 = ((humidex - temperature) / 0.5555) + 10;
            double logHTBy05Plus10By611 = Math.log(HTBy05Plus10 / 6.11);
            double logHTByRest = logHTBy05Plus10By611 / (5417.7530 * Math.log(Math.E));
            double logHTByRestMinus1Over273 = logHTByRest - 1 / 273.16;
            dewpoint = -1 / logHTByRestMinus1Over273 - 273.16;
        }
        return new Result(temperature, dewpoint, humidex);
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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

        public void flush() {
            writer.flush();
        }
    }
}
