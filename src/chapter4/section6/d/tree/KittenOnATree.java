package chapter4.section6.d.tree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/07/24.
 */
public class KittenOnATree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int kittenBranchId = FastReader.nextInt();
        int[] nextBranch = new int[101];

        String line = FastReader.getLine();
        while (!line.equals("-1")) {
            String[] data = line.split(" ");
            int nextBranchId = Integer.parseInt(data[0]);
            for (int i = 1; i < data.length; i++) {
                int branchId = Integer.parseInt(data[i]);
                nextBranch[branchId] = nextBranchId;
            }
            line = FastReader.getLine();
        }

        List<Integer> pathToGround = computePathToGround(nextBranch, kittenBranchId);
        outputWriter.print(pathToGround.get(0));
        for (int i = 1; i < pathToGround.size(); i++) {
            outputWriter.print(" " + pathToGround.get(i));
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static List<Integer> computePathToGround(int[] nextBranch, int branchId) {
        List<Integer> pathToGround = new ArrayList<>();

        while (branchId != 0) {
            pathToGround.add(branchId);
            branchId = nextBranch[branchId];
        }
        return pathToGround;
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
