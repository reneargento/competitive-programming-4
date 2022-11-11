package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/11/22.
 */
public class IsBiggerSmarter {

    private static class Elephant implements Comparable<Elephant> {
        int index;
        int size;
        int iq;

        public Elephant(int index, int size, int iq) {
            this.index = index;
            this.size = size;
            this.iq = iq;
        }

        @Override
        public int compareTo(Elephant other) {
            if (iq != other.iq) {
                return Integer.compare(other.iq, iq);
            }
            return Integer.compare(other.size, size);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Elephant> elephants = new ArrayList<>();
        int elephantIndex = 1;

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int size = Integer.parseInt(data[0]);
            int iq = Integer.parseInt(data[1]);
            elephants.add(new Elephant(elephantIndex, size, iq));
            elephantIndex++;
            line = FastReader.getLine();
        }

        List<Integer> selectedElephants = longestIncreasingSubsequence(elephants);
        outputWriter.printLine(selectedElephants.size());
        for (int elephant : selectedElephants) {
            outputWriter.printLine(elephant);
        }
        outputWriter.flush();
    }

    private static List<Integer> longestIncreasingSubsequence(List<Elephant> elephants) {
        if (elephants.isEmpty()) {
            return new ArrayList<>();
        }
        Collections.sort(elephants);

        int[] endIndexes = new int[elephants.size()];
        int[] previousIndices = new int[elephants.size()];

        Arrays.fill(previousIndices, -1);
        int length = 1;

        for (int i = 1; i < elephants.size(); i++) {
            // Case 1 - smallest end element
            if (elephants.get(i).size <= elephants.get(endIndexes[0]).size) {
                endIndexes[0] = i;
            } else if (elephants.get(i).size > elephants.get(endIndexes[length - 1]).size
                    && elephants.get(i).iq < elephants.get(endIndexes[length - 1]).iq) {
                // Case 2 - highest end element - extends longest increasing subsequence
                previousIndices[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(elephants, endIndexes, 0, length - 1, elephants.get(i).size);
                previousIndices[i] = endIndexes[indexToReplace - 1];
                endIndexes[indexToReplace] = i;
            }
        }
        return getSequence(elephants, endIndexes, previousIndices, length);
    }

    private static int ceilIndex(List<Elephant> elephants, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (elephants.get(endIndexes[middle]).size >= key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
    }

    private static List<Integer> getSequence(List<Elephant> elephants, int[] endIndexes, int[] previousIndices, int length) {
        LinkedList<Integer> sequence = new LinkedList<>();

        for (int i = endIndexes[length - 1]; i >= 0; i = previousIndices[i]) {
            sequence.addFirst(elephants.get(i).index);
        }
        return sequence;
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

        public void flush() {
            writer.flush();
        }
    }
}
