package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 30/05/21.
 */
public class Metaprogramming {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Integer> integerMap = new HashMap<>();
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            if (data[0].equals("define")) {
                integerMap.put(data[2], Integer.parseInt(data[1]));
            } else {
                if (!integerMap.containsKey(data[1]) || !integerMap.containsKey(data[3])) {
                    outputWriter.printLine("undefined");
                } else {
                    int value1 = integerMap.get(data[1]);
                    int value2 = integerMap.get(data[3]);
                    boolean result;

                    switch (data[2]) {
                        case "<": result = value1 < value2; break;
                        case ">": result = value1 > value2; break;
                        default: result = value1 == value2;
                    }
                    outputWriter.printLine(result);
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
