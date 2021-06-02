package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/06/21.
 */
public class AwkwardParty {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int guests = FastReader.nextInt();
        Map<Integer, Integer> languageToLocationMap = new HashMap<>();
        Map<Integer, Integer> languageToAwkwardnessLevel = new HashMap<>();

        for (int i = 0; i < guests; i++) {
            int language = FastReader.nextInt();
            if (languageToLocationMap.containsKey(language)) {
                int currentAwkwardness = i - languageToLocationMap.get(language);
                if (!languageToAwkwardnessLevel.containsKey(language)
                        || languageToAwkwardnessLevel.get(language) > currentAwkwardness) {
                    languageToAwkwardnessLevel.put(language, currentAwkwardness);
                }
            }
            languageToLocationMap.put(language, i);
        }

        long awkwardnessLevel = computeAwkwardness(languageToAwkwardnessLevel, guests);
        outputWriter.printLine(awkwardnessLevel);
        outputWriter.flush();
    }

    private static long computeAwkwardness(Map<Integer, Integer> languageToAwkwardnessLevel, int guests) {
        long awkwardnessLevel = guests;
        for (int awkwardness : languageToAwkwardnessLevel.values()) {
            if (awkwardness < awkwardnessLevel) {
                awkwardnessLevel = awkwardness;
            }
        }
        return awkwardnessLevel;
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
