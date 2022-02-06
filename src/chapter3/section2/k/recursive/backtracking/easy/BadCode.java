package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/02/22.
 */
public class BadCode {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int characters = FastReader.nextInt();
        int caseId = 1;

        while (characters != 0) {
            Map<String, Character> codeToCharacterMap = new HashMap<>();

            for (int i = 0; i < characters; i++) {
                Character character = FastReader.next().charAt(0);
                String code = FastReader.next();
                String characterWithPrefix = "0" + code;
                codeToCharacterMap.put(code, character);
                codeToCharacterMap.put(characterWithPrefix, character);
            }
            String encryptedText = FastReader.next();

            List<String> plainTexts = new ArrayList<>();
            getPlainTexts(encryptedText, codeToCharacterMap, plainTexts, 0, new StringBuilder());
            Collections.sort(plainTexts);

            outputWriter.printLine(String.format("Case #%d", caseId));
            for (int i = 0; i < plainTexts.size() && i < 100; i++) {
                outputWriter.printLine(plainTexts.get(i));
            }
            outputWriter.printLine();

            caseId++;
            characters = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void getPlainTexts(String encryptedText, Map<String, Character> codeToCharacterMap,
                                      List<String> plainTexts, int index, StringBuilder plainText) {
        if (index == encryptedText.length()) {
            plainTexts.add(plainText.toString());
            return;
        }

        for (int i = index + 1; i <= encryptedText.length(); i++) {
            String code = encryptedText.substring(index, i);

            if (codeToCharacterMap.containsKey(code)) {
                int nextIndex = index + (i - index);
                char character = codeToCharacterMap.get(code);
                plainText.append(character);
                getPlainTexts(encryptedText, codeToCharacterMap, plainTexts, nextIndex, plainText);
                plainText.deleteCharAt(plainText.length() - 1);
            }
        }
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
