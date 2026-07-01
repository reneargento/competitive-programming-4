package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/06/2026.
 */
public class CodeCreator {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int linesNumber = FastReader.nextInt();
        int caseId = 1;
        while (linesNumber != 0) {
            String[] lines = new String[linesNumber];
            for (int i = 0; i < lines.length; i++) {
                lines[i] = FastReader.getLine();
            }

            outputWriter.printLine(String.format("Case %d:", caseId));
            createCode(lines, outputWriter);
            caseId++;
            linesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void createCode(String[] lines, OutputWriter outputWriter) {
        outputWriter.printLine("#include<string.h>\n" +
                "#include<stdio.h>\n" +
                "int main()\n" +
                "{");

        for (String line : lines) {
            String updatedLine = line.replace("\\", "\\\\");
            updatedLine = updatedLine.replace("\"", "\\\"");
            outputWriter.printLine(String.format("printf(\"%s\\n\");", updatedLine));
        }

        outputWriter.printLine("printf(\"\\n\");\n" +
                "return 0;\n" +
                "}");
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