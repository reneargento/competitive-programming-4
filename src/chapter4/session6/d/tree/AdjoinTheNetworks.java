package chapter4.session6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/07/24.
 */
@SuppressWarnings("unchecked")
public class AdjoinTheNetworks {

    private static class VertexData {
        int vertexId;
        int distance;

        public VertexData(int vertexId, int distance) {
            this.vertexId = vertexId;
            this.distance = distance;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReaderInteger = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer>[] adjacencyList = new List[fastReaderInteger.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        UnionFind unionFind = new UnionFind(adjacencyList.length);

        int cables = fastReaderInteger.nextInt();
        for (int c = 0; c < cables; c++) {
            int computer1 = fastReaderInteger.nextInt();
            int computer2 = fastReaderInteger.nextInt();
            adjacencyList[computer1].add(computer2);
            adjacencyList[computer2].add(computer1);
            unionFind.union(computer1, computer2);
        }

        int maximumNumberOfHops = computeMaximumNumberOfHops(adjacencyList, unionFind);
        outputWriter.printLine(maximumNumberOfHops);
        outputWriter.flush();
    }

    private static int computeMaximumNumberOfHops(List<Integer>[] adjacencyList, UnionFind unionFind) {
        List<Integer> diametersList = new ArrayList<>();

        for (int computer = 0; computer < adjacencyList.length; computer++) {
            if (unionFind.leaders[computer] == computer) {
                int diameter = computeTreeDiameter(adjacencyList, computer);
                diametersList.add(diameter);
            }
        }
        Collections.sort(diametersList);

        Deque<Integer> diametersDeque = new ArrayDeque<>(diametersList);
        while (diametersDeque.size() > 1) {
            Integer center1 = diametersDeque.peekLast() / 2;
            Integer center2 = center1;
            if (diametersDeque.getLast() % 2 == 1) {
                center1++;
            }
            diametersDeque.removeLast();

            Integer center3 = diametersDeque.peekLast() / 2;
            if (diametersDeque.getLast() % 2 == 1) {
                center3++;
            }
            diametersDeque.removeLast();

            int diameterCandidate1 = center1 + center2;
            int diameterCandidate2 = center1 + center3 + 1;
            int maxDiameter = Math.max(diameterCandidate1, diameterCandidate2);

            diametersDeque.addLast(maxDiameter);
        }
        return diametersDeque.getFirst();
    }

    private static int computeTreeDiameter(List<Integer>[] adjacencyList, int sourceVertex) {
        VertexData furthestVertex = getFurthestVertex(adjacencyList, sourceVertex);
        VertexData furthestVertexFromFurthest = getFurthestVertex(adjacencyList, furthestVertex.vertexId);
        return furthestVertexFromFurthest.distance;
    }

    private static VertexData getFurthestVertex(List<Integer>[] adjacencyList, int sourceVertexID) {
        Queue<VertexData> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        VertexData source = new VertexData(sourceVertexID, 0);
        queue.offer(source);
        visited.add(sourceVertexID);

        while (!queue.isEmpty()) {
            VertexData vertexData = queue.poll();

            for (int neighbor : adjacencyList[vertexData.vertexId]) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(new VertexData(neighbor, vertexData.distance + 1));
                }
            }

            if (queue.isEmpty()) {
                return vertexData;
            }
        }
        return source;
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
            }
        }

        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
            } else {
                leaders[leader1] = leader2;
                ranks[leader2]++;
            }
        }
    }

    private static class FastReaderInteger {
        private static final InputStream in = System.in;
        private static final int bufferSize = 30000;
        private static final byte[] buffer = new byte[bufferSize];
        private static int position = 0;
        private static int byteCount = bufferSize;
        private static byte character;

        FastReaderInteger() throws IOException {
            fill();
        }

        private void fill() throws IOException {
            byteCount = in.read(buffer, 0, bufferSize);
        }

        private int nextInt() throws IOException {
            while (character < '-') {
                character = readByte();
            }
            boolean isNegative = (character == '-');
            if (isNegative) {
                character = readByte();
            }
            int value = character - '0';
            while ((character = readByte()) >= '0' && character <= '9') {
                value = value * 10 + character - '0';
            }
            return isNegative ? -value : value;
        }

        private byte readByte() throws IOException {
            if (position == byteCount) {
                fill();
                position = 0;
            }
            return buffer[position++];
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
