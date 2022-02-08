package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/02/22.
 */
public class BudgetTravel {

    private static class GasolineStation {
        double distanceFromOrigin;
        double pricePerGallon;

        public GasolineStation(double distanceFromOrigin, double pricePerGallon) {
            this.distanceFromOrigin = distanceFromOrigin;
            this.pricePerGallon = pricePerGallon;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        double distanceToDestination = FastReader.nextDouble();
        int dataSet = 1;

        while (distanceToDestination >= 0) {
            double tankCapacity = FastReader.nextDouble();
            double milesPerGallon = FastReader.nextDouble();
            double currentPriceSpent = FastReader.nextDouble();
            GasolineStation[] gasolineStations = new GasolineStation[FastReader.nextInt()];

            for (int i = 0; i < gasolineStations.length; i++) {
                gasolineStations[i] = new GasolineStation(FastReader.nextDouble(),
                        FastReader.nextDouble() / 100.0);
            }
            double currentGallons = tankCapacity - (gasolineStations[0].distanceFromOrigin / milesPerGallon);
            double minimumPrice = computeMinimumPrice(distanceToDestination, tankCapacity, milesPerGallon,
                    gasolineStations, currentPriceSpent, currentGallons,
                    gasolineStations[0].distanceFromOrigin, 0);
            outputWriter.printLine(String.format("Data Set #%d", dataSet));
            outputWriter.printLine(String.format("minimum cost = $%.2f", minimumPrice));

            dataSet++;
            distanceToDestination = FastReader.nextDouble();
        }
        outputWriter.flush();
    }

    private static double computeMinimumPrice(double distanceToDestination, double tankCapacity,
                                              double milesPerGallon, GasolineStation[] gasolineStations,
                                              double currentPriceSpent, double currentGallons,
                                              double distanceTravelled, int currentStation) {
        if (distanceTravelled >= distanceToDestination) {
            return currentPriceSpent;
        }

        double maxDistancePossible = currentGallons * milesPerGallon;
        double gallonsNeeded = tankCapacity - currentGallons;

        double newPriceSpent = currentPriceSpent + gasolineStations[currentStation].pricePerGallon * gallonsNeeded;
        newPriceSpent = roundValuePrecisionDigits(newPriceSpent) + 2;
        double gallonsUsed;
        double newDistanceTravelled;

        if (currentStation < gasolineStations.length - 1) {
            gallonsUsed = (gasolineStations[currentStation + 1].distanceFromOrigin - distanceTravelled)
                    / milesPerGallon;
            newDistanceTravelled = gasolineStations[currentStation + 1].distanceFromOrigin;
        } else {
            gallonsUsed = (distanceToDestination - distanceTravelled) / milesPerGallon;
            newDistanceTravelled = distanceToDestination;
        }

        if ((currentStation < gasolineStations.length - 1
                && distanceTravelled + maxDistancePossible < gasolineStations[currentStation + 1].distanceFromOrigin)
             || (currentStation == gasolineStations.length - 1
                && distanceTravelled + maxDistancePossible < distanceToDestination)) {
            // Fuel mandatory
            return computeMinimumPrice(distanceToDestination, tankCapacity, milesPerGallon,
                    gasolineStations, newPriceSpent, tankCapacity - gallonsUsed,
                    newDistanceTravelled, currentStation + 1);
        }

        if (currentGallons <= tankCapacity / 2) {
            // Fuel (optional)
            double priceSpentFueling = computeMinimumPrice(distanceToDestination, tankCapacity, milesPerGallon,
                    gasolineStations, newPriceSpent, tankCapacity - gallonsUsed,
                    newDistanceTravelled, currentStation + 1);

            // Do not fuel (optional)
            double priceSpentNotFueling = computeMinimumPrice(distanceToDestination, tankCapacity, milesPerGallon,
                    gasolineStations, currentPriceSpent, currentGallons - gallonsUsed,
                    newDistanceTravelled, currentStation + 1);
            return Math.min(priceSpentFueling, priceSpentNotFueling);
        } else {
            // No need to fuel, just go to the next station
            return computeMinimumPrice(distanceToDestination, tankCapacity, milesPerGallon,
                    gasolineStations, currentPriceSpent, currentGallons - gallonsUsed,
                    newDistanceTravelled, currentStation + 1);
        }
    }

    private static double roundValuePrecisionDigits(double value) {
        long valueToMultiply = (long) Math.pow(10, 2);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
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
