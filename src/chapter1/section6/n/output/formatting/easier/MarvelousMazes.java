package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 12/12/20.
 */
public class MarvelousMazes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        while (scanner.hasNext()) {
            String mazeDescription = scanner.nextLine();

            if (mazeDescription.isEmpty()) {
                outputWriter.printLine();
            } else {
                printMaze(mazeDescription, outputWriter);
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void printMaze(String mazeDescription, OutputWriter outputWriter) {
        int frequency = 0;

        for (int i = 0; i < mazeDescription.length(); i++) {
            char symbol = mazeDescription.charAt(i);

            if (Character.isDigit(symbol)) {
                frequency += Character.getNumericValue(symbol);

                for (int j = i + 1; j < mazeDescription.length(); j++) {
                    if (Character.isDigit(mazeDescription.charAt(j))) {
                        i++;
                        frequency += Character.getNumericValue(mazeDescription.charAt(j));
                    } else {
                        break;
                    }
                }
            } else if (symbol == '!') {
                outputWriter.printLine();
            } else {
                if (symbol == 'b') {
                    symbol = ' ';
                }
                for (int f = 0; f < frequency; f++) {
                    outputWriter.print(symbol);
                }
                frequency = 0;
            }
        }
        outputWriter.printLine();
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
