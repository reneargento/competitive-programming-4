package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/07/21.
 */
public class Quadtrees {

    private enum Color {
        WHITE, BLACK, GRAY
    }

    private static class Node {
        Color color;
        List<Node> children;
        int depth;

        public Node(int depth) {
            this.depth = depth;
        }
    }

    private static int preorderIndex;
    private static int maxDepth;
    private static int blackPixelSize;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String quadtreePreorder1 = FastReader.next();
            String quadtreePreorder2 = FastReader.next();
            maxDepth = 0;

            Node quadtree1 = createQuadtree(quadtreePreorder1);
            Node quadtree2 = createQuadtree(quadtreePreorder2);
            blackPixelSize = (int) (1024 / Math.pow(4, maxDepth));

            long blackPixels = countBlackPixels(quadtree1, quadtree2);
            outputWriter.printLine(String.format("There are %d black pixels.", blackPixels));
        }
        outputWriter.flush();
    }

    private static Node createQuadtree(String quadtreePreorder) {
        preorderIndex = 0;
        return createQuadtree(quadtreePreorder, new Node(0));
    }

    private static Node createQuadtree(String quadtreePreorder, Node node) {
        if (preorderIndex == quadtreePreorder.length()) {
            return null;
        }

        char symbol = quadtreePreorder.charAt(preorderIndex);
        preorderIndex++;

        if (symbol == 'p') {
            node.color = Color.GRAY;
            node.children = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                int nextDepth = node.depth + 1;
                maxDepth = Math.max(maxDepth, nextDepth);
                node.children.add(createQuadtree(quadtreePreorder, new Node(nextDepth)));
            }
        } else if (symbol == 'e') {
            node.color = Color.WHITE;
        } else {
            node.color = Color.BLACK;
        }
        return node;
    }

    private static long countBlackPixels(Node quadtree1, Node quadtree2) {
        long blackPixelUnits = 0;

        if (quadtree1 != null && quadtree2 != null) {
            if (quadtree1.color == Color.GRAY && quadtree2.color == Color.GRAY) {
                for (int i = 0; i < 4; i++) {
                    blackPixelUnits += countBlackPixels(quadtree1.children.get(i),
                            quadtree2.children.get(i));
                }
            } else if (quadtree1.color != Color.GRAY && quadtree2.color != Color.GRAY) {
                if (quadtree1.color == Color.BLACK || quadtree2.color == Color.BLACK) {
                    blackPixelUnits += computeNumberOfBlackPixels(quadtree1);
                }
            } else if (quadtree1.color != Color.GRAY) {
                blackPixelUnits += recurseOnOneTree(quadtree1, quadtree2);
            } else {
                blackPixelUnits += recurseOnOneTree(quadtree2, quadtree1);
            }
        } else if (quadtree1 == null && quadtree2 != null) {
            blackPixelUnits += recurseOnOneTree(null, quadtree2);
        } else if (quadtree1 != null) {
            blackPixelUnits += recurseOnOneTree(null, quadtree1);
        }
        return blackPixelUnits;
    }

    private static long recurseOnOneTree(Node quadtree1, Node quadtree2) {
        long blackPixelUnits = 0;

        if (quadtree1 == null || quadtree1.color == Color.WHITE) {
            if (quadtree2.color == Color.GRAY) {
                for (int i = 0; i < 4; i++) {
                    blackPixelUnits += countBlackPixels(null, quadtree2.children.get(i));
                }
            } else if (quadtree2.color == Color.BLACK) {
                blackPixelUnits += computeNumberOfBlackPixels(quadtree2);
            }
        } else {
            blackPixelUnits += computeNumberOfBlackPixels(quadtree1);
        }
        return blackPixelUnits;
    }

    private static long computeNumberOfBlackPixels(Node quadtree) {
        int depthDifference = maxDepth - quadtree.depth;
        return (long) Math.pow(4, depthDifference) * blackPixelSize;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
