package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 12/12/20.
 */
public class RotatingSentences {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<String> sentences = new ArrayList<>();

        while (scanner.hasNext()) {
            sentences.add(scanner.nextLine());
        }

        int[] maxLengths = getMaxLengths(sentences);

        for (int i = 0; i < maxLengths[sentences.size() - 1]; i++) {
            for (int s = sentences.size() - 1; s >= 0; s--) {
                String sentence = sentences.get(s);

                if (i >= sentence.length()) {
                    if (i >= maxLengths[s]) {
                        break;
                    } else {
                        outputWriter.print(" ");
                        continue;
                    }
                }
                outputWriter.print(sentence.charAt(i));
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int[] getMaxLengths(List<String> sentences) {
        int[] maxLengths = new int[sentences.size()];
        int maxLength = 0;

        for (int i = 0; i < sentences.size(); i++) {
            maxLength = Math.max(sentences.get(i).length(), maxLength);
            maxLengths[i] = maxLength;
        }
        return maxLengths;
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
