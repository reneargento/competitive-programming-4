package chapter6.section4.a.standard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 18/08/2026.
 */
public class Avion {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] codes = new String[5];
        for (int i = 0; i < 5; i++) {
            codes[i] = FastReader.getLine();
        }

        List<Integer> ciaBlimps = computeCIABlimps(codes);
        if (ciaBlimps.isEmpty()) {
            outputWriter.printLine("HE GOT AWAY!");
        } else {
            outputWriter.print(ciaBlimps.get(0));
            for (int i = 1; i < ciaBlimps.size(); i++) {
                outputWriter.print(" " + ciaBlimps.get(i));
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeCIABlimps(String[] codes) {
        List<Integer> ciaBlimps = new ArrayList<>();
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].contains("FBI")) {
                ciaBlimps.add(i + 1);
            }
        }
        return ciaBlimps;
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