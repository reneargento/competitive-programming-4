package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.BitSet;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 20/07/21.
 */
public class AbInitio {
    private static final int MAX_VERTICES = 4001;

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        int vertices = inputReader.readInt();
        int edges = inputReader.readInt();
        int queries = inputReader.readInt();

        BitSet[] bitSet = new BitSet[MAX_VERTICES];

        for (int vertex = 0; vertex < vertices; vertex++) {
            bitSet[vertex] = new BitSet(MAX_VERTICES);
        }

        for (int edge = 0; edge < edges; edge++) {
            int vertex1 = inputReader.readInt();
            int vertex2 = inputReader.readInt();
            addEdge(bitSet, vertex1, vertex2);
        }

        boolean transposed = false;

        for (int query = 0; query < queries; query++) {
            int type = inputReader.readInt();

            switch (type) {
                case 1:
                    bitSet[vertices] = new BitSet(MAX_VERTICES);
                    vertices++;
                    break;
                case 2:
                    int vertex1 = inputReader.readInt();
                    int vertex2 = inputReader.readInt();

                    if (!transposed) {
                        addEdge(bitSet, vertex1, vertex2);
                    } else {
                        addEdge(bitSet, vertex2, vertex1);
                    }
                    break;
                case 3:
                    int vertex = inputReader.readInt();
                    removeEdgesFromVertex(bitSet, vertex, vertices);
                    break;
                case 4:
                    int vertex3 = inputReader.readInt();
                    int vertex4 = inputReader.readInt();
                    if (!transposed) {
                        bitSet[vertex3].clear(vertex4);
                    } else {
                        bitSet[vertex4].clear(vertex3);
                    }
                    break;
                case 5: transposed = !transposed; break;
                case 6: complementGraph(bitSet, vertices); break;
            }
        }

        if (transposed) {
            bitSet = transposeGraph(bitSet, vertices);
        }
        printFinalGraph(bitSet, vertices);
    }

    private static void addEdge(BitSet[] bitSet, int vertex1, int vertex2) {
        bitSet[vertex1].set(vertex2);
    }

    private static void removeEdgesFromVertex(BitSet[] bitSet, int vertex, int vertices) {
        bitSet[vertex].clear();

        for (int i = 0; i < vertices; i++) {
            bitSet[i].clear(vertex);
        }
    }

    private static void complementGraph(BitSet[] bitSet, int vertices) {
        for (int i = 0; i < vertices; i++) {
            bitSet[i].flip(0, vertices);
            bitSet[i].clear(i);
        }
    }

    private static BitSet[] transposeGraph(BitSet[] bitSet, int vertices) {
        BitSet[] transposedBitSet = new BitSet[MAX_VERTICES];
        for (int i = 0; i < vertices; i++) {
            transposedBitSet[i] = new BitSet(MAX_VERTICES);
        }

        for (int vertex = 0; vertex < vertices; vertex++) {
            int vertex2 = 0;
            while (true) {
                vertex2 = bitSet[vertex].nextSetBit(vertex2);

                if (vertex2 == -1) {
                    break;
                }
                transposedBitSet[vertex2].set(vertex);
                vertex2++;
            }
        }
        return transposedBitSet;
    }

    private static void printFinalGraph(BitSet[] bitSet, int vertices) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        outputWriter.printLine(vertices);
        int mod = 1000000007;

        for (int vertex = 0; vertex < vertices; vertex++) {
            outputWriter.print(bitSet[vertex].cardinality() + " ");
            long sum = 0;

            int vertex2 = 0;
            long multiplier = 1;
            while (vertex2 != -1) {
                vertex2 = bitSet[vertex].nextSetBit(vertex2);

                if (vertex2 == -1) {
                    break;
                }
                sum += (vertex2 * multiplier);
                vertex2++;
                multiplier *= 7;
                multiplier %= mod;
                sum %= mod;
            }
            outputWriter.printLine(sum);
        }
        outputWriter.flush();
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        // Returns null on EOF
        public String readLine() {
            int c = read();
            if (c == -1) {
                return null;
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == -1;
        }

        public String next() {
            return readString();
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
