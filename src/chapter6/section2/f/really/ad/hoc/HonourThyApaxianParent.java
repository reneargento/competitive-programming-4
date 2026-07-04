package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/07/2026.
 */
public class HonourThyApaxianParent {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String name = FastReader.next();
        String parentName = FastReader.next();
        String extendedName = computeExtendedName(name, parentName);
        outputWriter.printLine(extendedName);

        outputWriter.flush();
    }

    private static String computeExtendedName(String name, String parentName) {
        char lastChar = name.charAt(name.length() - 1);
        if (isVowel(lastChar)) {
            if (lastChar == 'e') {
                return name + "x" + parentName;
            }
            return name.substring(0, name.length() - 1) + "ex" + parentName;
        }
        if (name.endsWith("ex")) {
            return name + parentName;
        }
        return name + "ex" + parentName;
    }

    private static boolean isVowel(char character) {
        return  character == 'a' || character == 'e' || character == 'i'  || character == 'o' || character == 'u';
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