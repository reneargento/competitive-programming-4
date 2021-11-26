package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/11/21.
 */
public class Medals {

    private static class Country {
        String name;
        int[] medals;

        public Country(String name, int[] medals) {
            this.name = name;
            this.medals = medals;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int countriesNumber = FastReader.nextInt();

        while (countriesNumber != 0) {
            int totalMedals = 0;
            Country[] countries = new Country[countriesNumber];

            for (int i = 0; i < countries.length; i++) {
                String name = FastReader.next();
                int goldMedals = FastReader.nextInt();
                int silverMedals = FastReader.nextInt();
                int bronzeMedals = FastReader.nextInt();
                int[] medals = new int[] { goldMedals, silverMedals, bronzeMedals };

                countries[i] = new Country(name, medals);
                totalMedals += goldMedals;
                totalMedals += silverMedals;
                totalMedals += bronzeMedals;
            }

            if (canCanadaWin(countries, totalMedals)) {
                outputWriter.printLine("Canada wins!");
            } else {
                outputWriter.printLine("Canada cannot win.");
            }
            countriesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean canCanadaWin(Country[] countries, int totalMedals) {
        for (int j = 0; j <= 10; j++) {
            for (int k = 0; k <= 10; k++) {
                for (int l = 0; l <= 10; l++) {
                    double weight1 = 1 / Math.pow(totalMedals, j);
                    double weight2 = 1 / Math.pow(totalMedals, k);
                    double weight3 = 1 / Math.pow(totalMedals, l);

                    Arrays.sort(countries, new Comparator<Country>() {
                        @Override
                        public int compare(Country country1, Country country2) {
                            double value1 = weight1 * country1.medals[0] +
                                    weight2 * country1.medals[1] + weight3 * country1.medals[2];
                            double value2 = weight1 * country2.medals[0] +
                                    weight2 * country2.medals[1] + weight3 * country2.medals[2];

                            if (value1 != value2) {
                                return Double.compare(value2, value1);
                            }
                            if (!country1.name.equals(country2.name)) {
                                if (country1.name.equals("Canada")) {
                                    return -1;
                                } else if (country2.name.equals("Canada")) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                            return 0;
                        }
                    });

                    if (countries[0].name.equals("Canada")) {
                        return true;
                    }
                }
            }
        }
        return false;
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
