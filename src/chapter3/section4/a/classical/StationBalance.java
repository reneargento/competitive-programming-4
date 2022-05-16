package chapter3.section4.a.classical;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/05/22.
 */
public class StationBalance {

    private static class Chamber {
        List<Integer> specimenList;
        long totalMass;
        int specimenNumber;

        public Chamber() {
            specimenList = new ArrayList<>();
        }
    }

    private static class Result {
        Chamber[] specimenPerChamber;
        double imbalance;

        public Result(Chamber[] specimenPerChamber, double imbalance) {
            this.specimenPerChamber = specimenPerChamber;
            this.imbalance = imbalance;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int setNumber = 1;

        while (line != null) {
            String[] data = line.split(" ");
            Chamber[] chambers = new Chamber[Integer.parseInt(data[0])];
            for (int i = 0; i < chambers.length; i++) {
                chambers[i] = new Chamber();
            }
            long totalMass = 0;
            int specimens = Integer.parseInt(data[1]);

            List<String> line2 = getWords(FastReader.getLine());
            Integer[] masses = new Integer[specimens];
            for (int i = 0; i < line2.size(); i++) {
                int mass = Integer.parseInt(line2.get(i));
                masses[i] = mass;
                totalMass += mass;
            }
            outputWriter.printLine(String.format("Set #%d", setNumber));
            Result result = assignSpecimenToChambers(chambers, masses, totalMass);

            for (int c = 0; c < result.specimenPerChamber.length; c++) {
                outputWriter.print(String.format(" %d:", c));
                List<Integer> specimenList = result.specimenPerChamber[c].specimenList;

                for (Integer mass : specimenList) {
                    outputWriter.print(String.format(" %d", mass));
                }
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("IMBALANCE = %.5f", result.imbalance));
            outputWriter.printLine();

            setNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result assignSpecimenToChambers(Chamber[] chambers, Integer[] masses, long totalMass) {
        Arrays.sort(masses, Collections.reverseOrder());

        for (Integer mass : masses) {
            int selectedChamber = -1;
            long currentMinChamberMass = Integer.MAX_VALUE;

            for (int c = 0; c < chambers.length; c++) {
                if (chambers[c].specimenNumber < 2
                        && chambers[c].totalMass < currentMinChamberMass) {
                    currentMinChamberMass = chambers[c].totalMass;
                    selectedChamber = c;
                }
            }

            chambers[selectedChamber].specimenList.add(mass);
            chambers[selectedChamber].totalMass += mass;
            chambers[selectedChamber].specimenNumber++;
        }

        double averageMass = totalMass / (double) chambers.length;
        double imbalance = computeImbalance(chambers, averageMass);
        return new Result(chambers, imbalance);
    }

    private static double computeImbalance(Chamber[] chambers, double averageMass) {
        double imbalance = 0;
        for (Chamber chamber : chambers) {
            imbalance += Math.abs(chamber.totalMass - averageMass);
        }
        return imbalance;
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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
