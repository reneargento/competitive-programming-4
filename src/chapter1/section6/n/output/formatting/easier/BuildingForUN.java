package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 10/12/20.
 */
public class BuildingForUN {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int caseId = 0;

        while (scanner.hasNext()) {
            if (caseId > 0) {
                outputWriter.printLine();
            }

            int countries = scanner.nextInt();
            designBuilding(countries, outputWriter);
            caseId++;
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void designBuilding(int countries, OutputWriter outputWriter) {
        int height = 2;
        int width = countries;
        int length = countries;

        outputWriter.printLine(String.valueOf(height) + " " + width + " " + length);

        for (int i = 0; i < countries; i++) {
            char character = getCharacter(i);

            for (int j = 0; j < countries; j++) {
                outputWriter.print(character);
            }
            outputWriter.printLine();
        }

        outputWriter.printLine();

        for (int i = 0; i < countries; i++) {
            for (int j = 0; j < countries; j++) {
                char character = getCharacter(j);
                outputWriter.print(character);
            }
            outputWriter.printLine();
        }
    }

    private static char getCharacter(int index) {
        if (index <= 25) {
            return (char) ('a' + index);
        }
        index -= 26;
        return (char) ('A' + index);
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
