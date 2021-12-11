package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/12/21.
 */
public class FlexibleSpaces {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int totalWidth = FastReader.nextInt();
        int[] partitions = new int[FastReader.nextInt() + 2];
        partitions[0] = 0;
        partitions[1] = totalWidth;

        for (int p = 2; p < partitions.length; p++) {
            partitions[p] = FastReader.nextInt();
        }

        List<Integer> widthList = computeAllWidths(partitions);
        for (int width : widthList) {
            outputWriter.print(" " + width);
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static List<Integer> computeAllWidths(int[] partitions) {
        Set<Integer> widthSet = new HashSet<>();

        for (int i = 0; i < partitions.length; i++) {
            for (int j = i + 1; j < partitions.length; j++) {
                int width = Math.abs(partitions[i] - partitions[j]);
                widthSet.add(width);
            }
        }

        List<Integer> widthList = new ArrayList<>(widthSet);
        Collections.sort(widthList);
        return widthList;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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

        public void flush() {
            writer.flush();
        }
    }
}