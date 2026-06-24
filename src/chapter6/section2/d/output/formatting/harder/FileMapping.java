package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 22/06/2026.
 */
public class FileMapping {

    private static class Directory {
        String name;
        List<Directory> subdirectories;
        List<String> files;

        public Directory(String name) {
            this.name = name;
            subdirectories = new ArrayList<>();
            files = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int dataSetNumber = 1;
        while (!line.equals("#")) {
            if (dataSetNumber > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("DATA SET %d:", dataSetNumber));
            Directory root = getDirectoryContent(line, "ROOT");
            printDirectoryContent(root, 0, outputWriter);

            dataSetNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Directory getDirectoryContent(String line, String name) throws IOException {
        Directory directory = new Directory(name);

        while (!line.equals("*")) {
            if (line.charAt(0) == 'f') {
                directory.files.add(line);
            } else if (line.charAt(0) == 'd') {
                String nextLine = FastReader.getLine();
                Directory nextDirectory = getDirectoryContent(nextLine, line);
                directory.subdirectories.add(nextDirectory);
            } else if (line.equals("]")) {
                break;
            }
            line = FastReader.getLine();
        }
        return directory;
    }

    private static void printDirectoryContent(Directory directory, int level, OutputWriter outputWriter) {
        printIndentation(level, outputWriter);
        outputWriter.printLine(directory.name);
        for (Directory subdirectory : directory.subdirectories) {
            printDirectoryContent(subdirectory, level + 1, outputWriter);
        }
        Collections.sort(directory.files);

        for (String file : directory.files) {
            printIndentation(level, outputWriter);
            outputWriter.printLine(file);
        }
    }

    private static void printIndentation(int level, OutputWriter outputWriter) {
        for (int i = 0; i < level; i++) {
            outputWriter.print("|");
            for (int s = 0; s < 5; s++) {
                outputWriter.print(" ");
            }
        }
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