package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 29/06/2026.
 */
@SuppressWarnings("unchecked")
public class ExcusesExcuses {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int excuseSet = 1;
        while (line != null) {
            String[] data = line.split(" ");
            int keywordsNumber = Integer.parseInt(data[0]);
            int excusesNumber = Integer.parseInt(data[1]);

            Set<String> keywords = new HashSet<>();
            String[] originalExcuses = new String[excusesNumber];
            List<String>[] excusesSearch = new List[excusesNumber];

            for (int i = 0; i < keywordsNumber; i++) {
                keywords.add(FastReader.getLine());
            }
            for (int i = 0; i < excusesSearch.length; i++) {
                excusesSearch[i] = new ArrayList<>();
                String excuse = FastReader.getLine();

                originalExcuses[i] = excuse;
                excuse = excuse.toLowerCase().replaceAll("[^a-z]", " ").trim();
                String[] excuseTokens = excuse.split("\\s+");
                for (String excuseToken : excuseTokens) {
                    excusesSearch[i].add(excuseToken);
                }
            }

            List<String> worstExcuses = computeWorstExcuses(keywords, originalExcuses, excusesSearch);
            outputWriter.printLine(String.format("Excuse Set #%d", excuseSet));
            for (String worstExcuse : worstExcuses) {
                outputWriter.printLine(worstExcuse);
            }
            outputWriter.printLine();

            excuseSet++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computeWorstExcuses(Set<String> keywords, String[] originalExcuses,
                                                    List<String>[] excusesSearch) {
        List<String> worstExcuses = new ArrayList<>();
        int maxKeywordsFound = 0;

        for (int i = 0; i < excusesSearch.length; i++) {
            List<String> excuse = excusesSearch[i];
            int keyWordsFound = 0;
            for (String excuseToken : excuse) {
                if (keywords.contains(excuseToken)) {
                    keyWordsFound++;
                }
            }

            if (keyWordsFound > 0) {
                if (keyWordsFound == maxKeywordsFound) {
                    worstExcuses.add(originalExcuses[i]);
                } else if (keyWordsFound > maxKeywordsFound) {
                    worstExcuses = new ArrayList<>();
                    worstExcuses.add(originalExcuses[i]);
                    maxKeywordsFound = keyWordsFound;
                }
            }
        }
        return worstExcuses;
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