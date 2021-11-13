package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/11/21.
 */
public class Recycling {

    private static final int PLASTIC_INDEX = 0;
    private static final int GLASS_INDEX = 1;
    private static final int ALUMINIUM_INDEX = 2;
    private static final int STEEL_INDEX = 3;
    private static final int NEWSPAPER_INDEX = 4;

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.next();

        while (!line.equals("#")) {
            List<Map<Character, Integer>> citiesAllocation = new ArrayList<>();

            while (!line.startsWith("e")) {
                Map<Character, Integer> cityAllocation = new HashMap<>();

                String[] allocations = line.split(",");
                for (String allocation : allocations) {
                    char waste = allocation.charAt(0);
                    char bin = allocation.charAt(2);
                    int binIndex = getBinIndex(bin);

                    cityAllocation.put(waste, binIndex);
                }
                citiesAllocation.add(cityAllocation);

                line = FastReader.next();
            }

            int selectedCity = selectCity(citiesAllocation);
            outputWriter.printLine(selectedCity);

            line = FastReader.next();
        }
        outputWriter.flush();
    }

    private static int getBinIndex(char bin) {
        switch (bin) {
            case 'P': return PLASTIC_INDEX;
            case 'G': return GLASS_INDEX;
            case 'A': return ALUMINIUM_INDEX;
            case 'S': return STEEL_INDEX;
            default: return NEWSPAPER_INDEX;
        }
    }

    private static int selectCity(List<Map<Character, Integer>> citiesAllocation) {
        int bestNumberOfChanges = Integer.MAX_VALUE;
        int selectedCity = -1;

        for (int i = 0; i < citiesAllocation.size(); i++) {
            int numberOfChanges = 0;
            Map<Character, Integer> cityAllocation = citiesAllocation.get(i);

            for (int j = 0; j < citiesAllocation.size(); j++) {
                if (i != j) {
                    numberOfChanges += countChanges(cityAllocation, citiesAllocation.get(j));
                }
            }

            if (numberOfChanges < bestNumberOfChanges) {
                bestNumberOfChanges = numberOfChanges;
                selectedCity = i + 1;
            }
        }
        return selectedCity;
    }

    private static int countChanges(Map<Character, Integer> cityAllocation1,
                                    Map<Character, Integer> cityAllocation2) {
        int changes = 0;

        for (Character waste : cityAllocation1.keySet()) {
            if (!cityAllocation1.get(waste).equals(cityAllocation2.get(waste))) {
                changes++;
            }
        }
        return changes;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
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
