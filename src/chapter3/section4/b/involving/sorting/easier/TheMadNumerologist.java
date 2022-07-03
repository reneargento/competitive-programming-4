package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/06/22.
 */
public class TheMadNumerologist {

    private static final char[] VOWELS = { 'A', 'U', 'E', 'O', 'I' };
    private static final char[] CONSONANTS = {
            'J', 'S', 'B', 'K', 'T', 'C', 'L', 'D', 'M', 'V', 'N', 'W',
            'F', 'X', 'G', 'P', 'Y', 'H', 'Q', 'Z', 'R'
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int nameLength = FastReader.nextInt();
            String name = suggestName(nameLength);
            outputWriter.printLine(String.format("Case %d: %s", t, name));
        }
        outputWriter.flush();
    }

    private static String suggestName(int nameLength) {
        StringBuilder name = new StringBuilder();
        int vowelIndex = 0;
        int consonantIndex = 0;
        int currentVowelCount = 0;
        int currentConsonantCount = 0;
        List<Character> vowelsUsed = new ArrayList<>();
        List<Character> consonantsUsed = new ArrayList<>();

        for (int i = 0; i < nameLength; i++) {
            if (i % 2 == 0) {
                vowelsUsed.add(VOWELS[vowelIndex]);
                currentVowelCount++;
                if (currentVowelCount == 21) {
                    currentVowelCount = 0;
                    vowelIndex++;
                }
            } else {
                consonantsUsed.add(CONSONANTS[consonantIndex]);
                currentConsonantCount++;
                if (currentConsonantCount == 5) {
                    currentConsonantCount = 0;
                    consonantIndex++;
                }
            }
        }

        Collections.sort(vowelsUsed);
        Collections.sort(consonantsUsed);

        for (int i = 0; i < nameLength; i++) {
            int index = i / 2;
            if (i % 2 == 0) {
                name.append(vowelsUsed.get(index));
            } else {
                name.append(consonantsUsed.get(index));
            }
        }
        return name.toString();
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
