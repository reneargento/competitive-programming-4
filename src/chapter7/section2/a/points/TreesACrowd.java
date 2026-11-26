package chapter7.section2.a.points;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/11/2026.
 */
public class TreesACrowd {

    private static class Tree {
        int x;
        int y;
        int z;

        public Tree(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int x = FastReader.nextInt();
        int y = FastReader.nextInt();
        int z = FastReader.nextInt();
        List<Tree> trees = new ArrayList<>();

        while (x != 0 || y != 0 || z != 0) {
            trees.add(new Tree(x, y, z));
            x = FastReader.nextInt();
            y = FastReader.nextInt();
            z = FastReader.nextInt();
        }

        int[] histogram = buildHistogram(trees);
        for (int count : histogram) {
            outputWriter.print(String.format("%4d", count));
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static int[] buildHistogram(List<Tree> trees) {
        int[] histogram = new int[10];
        if (trees.size() <= 1) {
            return histogram;
        }

        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            double minimumDistance = Double.POSITIVE_INFINITY;

            for (int j = 0; j < trees.size(); j++) {
                if (i == j) {
                    continue;
                }
                double distance = distanceBetweenPoints(tree, trees.get(j));
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                }
            }

            int neighborsMinDistance = (int) Math.floor(minimumDistance);
            if (minimumDistance < histogram.length) {
                histogram[neighborsMinDistance]++;
            }
        }
        return histogram;
    }

    private static double distanceBetweenPoints(Tree tree1, Tree tree2) {
        return Math.sqrt(Math.pow(tree1.x - tree2.x, 2)
                + Math.pow(tree1.y - tree2.y, 2)
                + Math.pow(tree1.z - tree2.z, 2));
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

        public void flush() {
            writer.flush();
        }
    }
}