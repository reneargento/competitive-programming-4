package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/07/21.
 */
public class AlphabetAnimals {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String previousAnimal = FastReader.next();
        char targetLetter = previousAnimal.charAt(previousAnimal.length() - 1);
        int unusedNames = FastReader.nextInt();

        List<String> options = new ArrayList<>();
        int[] animalsStartingWithLetter = new int[26];

        for (int i = 0; i < unusedNames; i++) {
            String name = FastReader.next();
            int firstLetter = name.charAt(0) - 'a';
            animalsStartingWithLetter[firstLetter]++;

            if (name.charAt(0) == targetLetter) {
                options.add(name);
            }
        }

        String result = chooseAnimal(options, animalsStartingWithLetter);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String chooseAnimal(List<String> options, int[] animalsStartingWithLetter) {
        if (options.isEmpty()) {
            return "?";
        }

        for (String name : options) {
            char lastChar = name.charAt(name.length() - 1);
            int lastCharIndex = lastChar - 'a';

            if ((name.charAt(0) != name.charAt(name.length() - 1)
                    && animalsStartingWithLetter[lastCharIndex] == 0)
                    || (name.charAt(0) == name.charAt(name.length() - 1)
                    && animalsStartingWithLetter[lastCharIndex] == 1) ) {
                return name + "!";
            }
        }
        return options.get(0);
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
