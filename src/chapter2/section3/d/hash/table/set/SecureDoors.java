package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/05/21.
 */
public class SecureDoors {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int logs = FastReader.nextInt();
        Set<String> insideBuilding = new HashSet<>();

        for (int i = 0; i < logs; i++) {
            String action = FastReader.next();
            String name = FastReader.next();

            if (action.equals("entry")) {
                outputWriter.print(name + " entered");
                if (insideBuilding.contains(name)) {
                    outputWriter.printLine(" (ANOMALY)");
                } else {
                    outputWriter.printLine();
                }
                insideBuilding.add(name);
            } else {
                outputWriter.print(name + " exited");
                if (!insideBuilding.contains(name)) {
                    outputWriter.printLine(" (ANOMALY)");
                } else {
                    outputWriter.printLine();
                }
                insideBuilding.remove(name);
            }
        }
        outputWriter.flush();
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
