package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/07/22.
 */
public class TheTrip2007 {

    private static class Group {
        List<Integer> bags;

        public Group() {
            bags = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int bagsNumber = FastReader.nextInt();
        int caseId = 1;

        while (bagsNumber != 0) {
            if (caseId > 1) {
                outputWriter.printLine();
            }

            int[] bags = new int[bagsNumber];
            int maxFrequency = 0;
            Map<Integer, Integer> bagsFrequency = new HashMap<>();
            for (int i = 0; i < bags.length; i++) {
                int dimension = FastReader.nextInt();
                int frequency = bagsFrequency.getOrDefault(dimension, 0) + 1;
                bagsFrequency.put(dimension, frequency);
                maxFrequency = Math.max(maxFrequency, frequency);
                bags[i] = dimension;
            }

            Group[] groups = packBags(bags, maxFrequency);
            outputWriter.printLine(groups.length);
            for (Group group : groups) {
                outputWriter.print(group.bags.get((0)));
                for (int i = 1; i < group.bags.size(); i++) {
                    outputWriter.print(" " + group.bags.get(i));
                }
                outputWriter.printLine();
            }
            bagsNumber = FastReader.nextInt();
            caseId++;
        }
        outputWriter.flush();
    }

    private static Group[] packBags(int[] bags, int maxFrequency) {
        Group[] groups = new Group[maxFrequency];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new Group();
        }
        int groupIndex = 0;

        Arrays.sort(bags);
        for (int bag : bags) {
            groups[groupIndex].bags.add(bag);
            groupIndex++;
            groupIndex %= groups.length;
        }
        return groups;
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
