package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

public class TwoFourSixGreaaat {

    private static final int INFINITE = 100000000;

    private static class Cheer {
        int deltaEnthusiasm;
        int difficulty;

        Cheer(int deltaEnthusiasm, int difficulty) {
            this.deltaEnthusiasm = deltaEnthusiasm;
            this.difficulty = difficulty;
        }
    }

    private static class IntQueue {
        private int[] data = new int[16];
        private int head;
        private int tail;

        void add(int value) {
            if (tail == data.length) {
                if (head > 0) {
                    int size = tail - head;
                    System.arraycopy(data, head, data, 0, size);
                    head = 0;
                    tail = size;
                } else {
                    data = Arrays.copyOf(data, data.length << 1);
                }
            }
            data[tail++] = value;
        }

        int poll() {
            return data[head++];
        }

        boolean isEmpty() {
            return head == tail;
        }
    }

    public static void main(String[] args) throws IOException {
        FastInputStream fastInputStream = new FastInputStream(System.in);
        int nonStandardCheers = fastInputStream.nextInt();
        int targetEnthusiasm = fastInputStream.nextInt();
        int minimumCheer = 0;

        Cheer[] cheers = new Cheer[nonStandardCheers + 1];
        cheers[0] = new Cheer(1, 1);

        for (int i = 1; i <= nonStandardCheers; i++) {
            int deltaEnthusiasm = fastInputStream.nextInt();
            int difficulty = fastInputStream.nextInt();

            if (deltaEnthusiasm == 0) {
                difficulty = INFINITE;
            }
            cheers[i] = new Cheer(deltaEnthusiasm, difficulty);
            minimumCheer = Math.min(minimumCheer, deltaEnthusiasm);
        }

        computeCheersSequence(cheers, targetEnthusiasm, minimumCheer);
        FastWriter.println();
        FastWriter.flush();
    }

    private static void computeCheersSequence(Cheer[] cheers, int targetEnthusiasm, int minimumCheer) throws IOException {
        int maxNode = targetEnthusiasm - minimumCheer;
        int[] distance = new int[maxNode + 1];
        int[] distanceFromEnd = new int[maxNode + 1];
        int[] parent = new int[maxNode + 1];

        Arrays.fill(distance, INFINITE);
        Arrays.fill(distanceFromEnd, INFINITE);
        Arrays.fill(parent, -1);

        // The queue is indexed by difficulty, not by enthusiasm.
        IntQueue[] queue = new IntQueue[targetEnthusiasm + 9];

        distance[0] = 0;
        parent[0] = -1;
        queue[0] = new IntQueue();
        queue[0].add(0);

        int meetInTheMiddleNode = 0;
        int minimumDistance = targetEnthusiasm + 1;

        int enthusiasm = 0;
        while (enthusiasm <= targetEnthusiasm) {
            while (enthusiasm <= targetEnthusiasm
                    && (queue[enthusiasm] == null
                        || queue[enthusiasm].isEmpty())) {
                enthusiasm++;
            }
            if (enthusiasm > targetEnthusiasm) {
                break;
            }

            int currentNode = queue[enthusiasm].poll();

            if (distance[currentNode] >= minimumDistance
                    || currentNode == targetEnthusiasm) {
                break;
            }

            // Ignore stale queue entries.
            if (enthusiasm > distance[currentNode]) {
                continue;
            }

            // Store the shortest distance to this enthusiasm value as the other half of the solution.
            if (targetEnthusiasm > currentNode) {
                distanceFromEnd[targetEnthusiasm - currentNode] =
                        distance[currentNode];
            }

            // We already have a path from 0 to the complementary enthusiasm, so combine the two paths.
            if (distanceFromEnd[currentNode] != INFINITE) {
                int totalDistance = distance[currentNode] + distanceFromEnd[currentNode];
                if (totalDistance < minimumDistance) {
                    minimumDistance = totalDistance;
                    meetInTheMiddleNode = currentNode;
                }
                continue;
            }

            // Relax all cheers.
            for (int cheerID = 0; cheerID < cheers.length; cheerID++) {
                int candidateNode = currentNode + cheers[cheerID].deltaEnthusiasm;
                if (candidateNode <= 0
                        || candidateNode > maxNode) {
                    continue;
                }

                int candidateEnthusiasm = distance[currentNode] + cheers[cheerID].difficulty;
                if (candidateEnthusiasm >= distance[candidateNode]
                        || candidateEnthusiasm > minimumDistance) {
                    continue;
                }

                distance[candidateNode] = candidateEnthusiasm;
                parent[candidateNode] = cheerID;

                if (queue[candidateEnthusiasm] == null) {
                    queue[candidateEnthusiasm] = new IntQueue();
                }
                queue[candidateEnthusiasm].add(candidateNode);
            }
        }
        printSequence(cheers, parent, targetEnthusiasm, meetInTheMiddleNode);
    }

    private static void printSequence(Cheer[] cheers, int[] parent, int targetEnthusiasm, int meetInTheMiddleNode)
            throws IOException {
        int sequenceSize = 0;
        int[] sequence = new int[parent.length];

        // First half: 0 -> meetInTheMiddleNode
        int currentEnthusiasm = meetInTheMiddleNode;

        while (currentEnthusiasm != 0) {
            int cheerID = parent[currentEnthusiasm];
            sequence[sequenceSize++] = cheerID + 1;
            currentEnthusiasm -= cheers[cheerID].deltaEnthusiasm;
        }

        // Second half: 0 -> targetEnthusiasm - meetInTheMiddleNode
        currentEnthusiasm = targetEnthusiasm - meetInTheMiddleNode;

        while (currentEnthusiasm != 0) {
            int cheerID = parent[currentEnthusiasm];
            sequence[sequenceSize++] = cheerID + 1;
            currentEnthusiasm -= cheers[cheerID].deltaEnthusiasm;
        }

        FastWriter.println(sequenceSize);
        for (int i = 0; i < sequenceSize; i++) {
            FastWriter.print(sequence[i]);
            FastWriter.print(' ');
        }
    }

    private static final class FastInputStream {
        private static final int BUF_SIZE = 1 << 16;
        private final InputStream in;
        private final byte[] buf = new byte[BUF_SIZE];
        private int pos;
        private int count;

        FastInputStream(InputStream in) {
            this.in = in;
        }

        private void readBuf() throws IOException {
            pos = 0;
            count = in.read(buf);
        }

        private void skipUnprintable() throws IOException {
            while (true) {
                while (pos < count && buf[pos] <= ' ') {
                    pos++;
                }

                if (pos < count) {
                    return;
                }

                readBuf();

                if (count <= 0) {
                    throw new NoSuchElementException();
                }
            }
        }

        int nextInt() throws IOException {
            skipUnprintable();

            int sign = 1;
            if (buf[pos] == '-') {
                sign = -1;
                pos++;
            }

            int value = 0;
            while (true) {
                while (pos < count) {
                    byte c = buf[pos];

                    if (c < '0' || c > '9') {
                        return value * sign;
                    }

                    value = value * 10 + c - '0';
                    pos++;
                }

                readBuf();
                if (count <= 0) {
                    return value * sign;
                }
            }
        }
    }

    private static final class FastWriter {
        private static final int BUFFER_SIZE = 1 << 15;
        private static final DataOutputStream out = new DataOutputStream(System.out);
        private static final byte[] buffer = new byte[BUFFER_SIZE];
        private static final byte[] number = new byte[11];
        private static int ptr;

        static void print(int value) throws IOException {
            if (value == 0) {
                print('0');
                return;
            }
            if (value < 0) {
                print('-');
                value = -value;
            }

            int size = 0;
            while (value > 0) {
                number[size++] = (byte) ('0' + value % 10);
                value /= 10;
            }
            while (size > 0) {
                print((char) number[--size]);
            }
        }

        static void print(char value) throws IOException {
            if (ptr == BUFFER_SIZE) {
                writeBuffer();
            }
            buffer[ptr++] = (byte) value;
        }

        static void println(int value) throws IOException {
            print(value);
            print('\n');
        }

        static void println() throws IOException {
            print('\n');
        }

        static void flush() throws IOException {
            writeBuffer();
            out.flush();
        }

        private static void writeBuffer() throws IOException {
            if (ptr > 0) {
                out.write(buffer, 0, ptr);
                ptr = 0;
            }
        }
    }
}