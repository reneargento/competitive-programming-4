package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 05/07/2026.
 */
public class OrderlyClass {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String sequence1 = FastReader.getLine();
        String sequence2 = FastReader.getLine();
        int waysToOrder = countWaysToOrder(sequence1, sequence2);
        outputWriter.printLine(waysToOrder);
        outputWriter.flush();
    }

    private static int countWaysToOrder(String sequence1, String sequence2) {
        char[] characters1 = getCharacters(sequence1);
        char[] characters2 = getCharacters(sequence2);

        List<Integer> differentIndexes = new ArrayList<>();
        for (int i = 0; i < characters1.length; i++) {
            if (characters1[i] != characters2[i]) {
                differentIndexes.add(i);
            }
        }
        if (differentIndexes.size() % 2 == 1 || differentIndexes.size() < 2) {
            return 0;
        }

        int firstDifferentIndex = differentIndexes.get(0);
        int lastDifferentIndex = differentIndexes.get(differentIndexes.size() - 1);
        int center = (firstDifferentIndex + lastDifferentIndex) / 2;
        int left = center;
        int right = center;

        while (true) {
            if (characters1[left] == characters2[right]
                    && characters1[right] == characters2[left]) {
                left--;
                right++;
            } else {
                break;
            }

            if (left < 0 || right >= characters1.length) {
                break;
            }
        }
        left++;
        right--;

        if (left > firstDifferentIndex || right < lastDifferentIndex) {
            return 0;
        }

        int waysToOrder = 0;
        for (int i = left; i <= firstDifferentIndex; i++) {
            if (characters1[i] != '.') {
                waysToOrder++;
            }
        }
        return waysToOrder;
    }

    private static char[] getCharacters(String sequence) {
        char[] characters = new char[sequence.length() * 2 - 1];
        int charactersIndex = 0;

        for (int i = 0; i < sequence.length(); i++) {
            characters[charactersIndex++] = sequence.charAt(i);

            if (i != sequence.length() - 1) {
                characters[charactersIndex++] = '.';
            }
        }
        return characters;
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