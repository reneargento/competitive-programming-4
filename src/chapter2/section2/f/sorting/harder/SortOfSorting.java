package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/02/21.
 */
public class SortOfSorting {

    private static class Name implements Comparable<Name> {
        int id;
        String value;

        public Name(int id, String value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public int compareTo(Name other) {
            String startName = value.substring(0, 2);
            String otherStartName = other.value.substring(0, 2);

            if (!startName.equals(otherStartName)) {
                return startName.compareTo(otherStartName);
            }
            return id - other.id;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int namesNumber = FastReader.nextInt();
        int caseId = 0;

        while (namesNumber != 0) {
            if (caseId > 0) {
                outputWriter.printLine();
            }

            Name[] names = new Name[namesNumber];
            for (int i = 0; i < names.length; i++) {
                names[i] = new Name(i, FastReader.next());
            }

            Arrays.sort(names);
            for (Name name : names) {
                outputWriter.printLine(name.value);
            }

            namesNumber = FastReader.nextInt();
            caseId++;
        }
        outputWriter.flush();
        outputWriter.close();
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
