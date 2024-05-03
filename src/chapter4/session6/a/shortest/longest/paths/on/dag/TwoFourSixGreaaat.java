package chapter4.session6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/04/24.
 */
// This is the Java version of TwoFourSixGreaaat.cpp.
// While TwoFourSixGreaaat.cpp gets AC in Kattis, this Java code (despite all optimizations) gets TLE.
// There are currently no ACs in Java for this problem, making me wonder if it is even possible to do it with the
// time limit of 1 second.
public class TwoFourSixGreaaat {

    private static class Cheer {
        int deltaEnthusiasm;
        int difficulty;

        public Cheer(int deltaEnthusiasm, int difficulty) {
            this.deltaEnthusiasm = deltaEnthusiasm;
            this.difficulty = difficulty;
        }
    }

    private static final int INFINITE = 100000000;

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

            // Prevent self-loops
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

    @SuppressWarnings("unchecked")
    private static void computeCheersSequence(Cheer[] cheers, int targetEnthusiasm, int minimumCheer) throws IOException {
        int maxNode = targetEnthusiasm - minimumCheer;
        int[] distance = new int[maxNode + 1];
        int[] distanceFromEnd = new int[maxNode + 1];
        int[] parent = new int[maxNode + 1];
        Deque<Integer>[] queue = new Deque[maxNode + 1];

        Arrays.fill(distance, INFINITE);
        Arrays.fill(distanceFromEnd, INFINITE);
        Arrays.fill(parent, -1);

        distance[0] = 0;
        parent[0] = -1;
        queue[0] = new ArrayDeque<>();
        queue[0].add(0);

        int meetInTheMiddleNode = 0;
        // totalDistance = targetEnthusiasm + 1 ensures that parent array is properly updated in edge cases
        int minimumDistance = targetEnthusiasm + 1;

        int enthusiasm = 0;
        while (enthusiasm < queue.length) {
            while (enthusiasm < queue.length && (queue[enthusiasm] == null || queue[enthusiasm].isEmpty())) {
                enthusiasm++;
            }
            if (enthusiasm == queue.length) {
                break;
            }

            int currentNode = queue[enthusiasm].pollFirst();
            if (distance[currentNode] >= minimumDistance
                    || currentNode == targetEnthusiasm) {
                break;
            }
            if (enthusiasm > distance[currentNode]) {
                // Inferior vertex
                continue;
            }

            if (targetEnthusiasm > currentNode) {
                distanceFromEnd[targetEnthusiasm - currentNode] = distance[currentNode];
            }
            if (distanceFromEnd[currentNode] != INFINITE) {
                if (distance[currentNode] + distanceFromEnd[currentNode] < minimumDistance) {
                    minimumDistance = distance[currentNode] + distanceFromEnd[currentNode];
                    meetInTheMiddleNode = currentNode;
                }
                continue;
            }

            for (int cheerID = 0; cheerID < cheers.length; cheerID++) {
                int candidateNode = currentNode + cheers[cheerID].deltaEnthusiasm;
                if (candidateNode > maxNode) {
                    break;
                }
                if (candidateNode <= 0) {
                    continue;
                }

                int candidateEnthusiasm = distance[currentNode] + cheers[cheerID].difficulty;
                if (candidateEnthusiasm >= distance[candidateNode]
                        || candidateEnthusiasm > minimumDistance) {
                    continue;
                }

                if (distanceFromEnd[candidateNode] != INFINITE
                        && distanceFromEnd[candidateNode] + candidateEnthusiasm > minimumDistance) {
                    continue;
                }

                distance[candidateNode] = candidateEnthusiasm;
                parent[candidateNode] = cheerID;
                if (queue[candidateEnthusiasm] == null) {
                    queue[candidateEnthusiasm] = new ArrayDeque<>();
                }
                queue[candidateEnthusiasm].offerLast(candidateNode);
            }
        }
        printSequence(cheers, parent, targetEnthusiasm, meetInTheMiddleNode);
    }

    private static void printSequence(Cheer[] cheers, int[] parent, int targetEnthusiasm, int meetInTheMiddleNode)
            throws IOException {
        int sequenceSize = 0;
        int[] sequence = new int[parent.length];
        int currentEnthusiasm = meetInTheMiddleNode;

        while (currentEnthusiasm != 0) {
            sequence[sequenceSize] = parent[currentEnthusiasm] + 1;
            sequenceSize++;
            Cheer cheer = cheers[parent[currentEnthusiasm]];
            currentEnthusiasm -= cheer.deltaEnthusiasm;
        }

        currentEnthusiasm = targetEnthusiasm - meetInTheMiddleNode;
        while (currentEnthusiasm != 0) {
            sequence[sequenceSize] = parent[currentEnthusiasm] + 1;
            sequenceSize++;
            Cheer cheer = cheers[parent[currentEnthusiasm]];
            currentEnthusiasm -= cheer.deltaEnthusiasm;
        }

        FastWriter.println(sequenceSize);
        for (int i = 0; i < sequenceSize; i++) {
            FastWriter.print(sequence[i] + " ");
        }
    }

    private static final class FastInputStream {
        private static final int BUF_SIZE = 1 << 14;
        private final InputStream in;
        private final byte buf[] = new byte[BUF_SIZE];
        private int pos = 0;
        private int count = 0;
        private static final int TOKEN_SIZE = 1 << 20;
        private final byte tokenBuf[] = new byte[TOKEN_SIZE];

        public FastInputStream(final InputStream in) {
            this.in = in;
        }
        private final void readBuf() {
            pos = 0;
            try { count = in.read(buf); }
            catch(IOException e) { e.printStackTrace(); }
        }
        private final boolean hasNextByte() {
            if(pos < count) return true;
            readBuf();
            return count > 0;
        }
        private final byte read() { if(hasNextByte()) return buf[pos ++]; else throw new NoSuchElementException(); }
        private final boolean isPrintableChar(final byte c) { return 33 <= c && c <= 126; }
        private final boolean isNumber(final byte c) { return 48 <= c && c <= 57; }
        private final void skipUnprintable() {
            while(true) {
                for(int i = pos; i < count; i ++) {
                    if(isPrintableChar(buf[i])) { pos = i; return; }
                }
                readBuf();
                if(count <= 0) throw new NoSuchElementException();
            }
        }
        private final boolean readEOL() {
            if(!hasNextByte()) return true;
            if(buf[pos] == 13) {
                pos ++;
                if(hasNextByte() && buf[pos] == 10) pos ++;
                return true;
            }
            if(buf[pos] == 10) {
                pos ++;
                return true;
            }
            return false;
        }

        public final char nextChar() {
            skipUnprintable();
            return (char)buf[pos ++];
        }
        public final String next() {
            skipUnprintable();
            int tokenCount = 0;
            outer: while(count > 0) {
                for(int i = pos; i < count; i ++) {
                    final byte b = buf[i];
                    if(!isPrintableChar(b)) { pos = i; break outer; }
                    tokenBuf[tokenCount ++] = b;
                }
                readBuf();
            }
            return new String(tokenBuf, 0, tokenCount);
        }
        public final String nextLine() {
            readEOL();
            if(!hasNextByte()) throw new NoSuchElementException();
            int tokenCount = 0;
            while(!readEOL()) tokenBuf[tokenCount ++] = read();
            return new String(tokenBuf, 0, tokenCount);
        }
        public final int nextInt() {
            skipUnprintable();
            int n = 0;
            boolean minus = false;
            if(buf[pos] == 45) {
                minus = true;
                pos ++;
                if(!hasNextByte() || !isNumber(buf[pos])) throw new InputMismatchException();
            }
            outer: while(count > 0) {
                for(int i = pos; i < count; i ++) {
                    final byte b = buf[i];
                    if(!isPrintableChar(b)) { pos = i; break outer; }
                    if(!isNumber(b)) throw new InputMismatchException();
                    if(minus) {
                        if(n < - 214748364) throw new ArithmeticException("int overflow");
                        if(n == - 214748364 && b > 56) throw new ArithmeticException("int overflow");
                        n = (n << 3) + (n << 1) + 48 - b;
                    }else {
                        if(n > 214748364) throw new ArithmeticException("int overflow");
                        if(n == 214748364 && b >= 56) throw new ArithmeticException("int overflow");
                        n = (n << 3) + (n << 1) - 48 + b;
                    }
                }
                readBuf();
            }
            return n;
        }
        public final long nextLong() {
            skipUnprintable();
            long n = 0;
            boolean minus = false;
            if(buf[pos] == 45) {
                minus = true;
                pos ++;
                if(!hasNextByte() || !isNumber(buf[pos])) throw new InputMismatchException();
            }
            outer: while(count > 0) {
                for(int i = pos; i < count; i ++) {
                    final byte b = buf[i];
                    if(!isPrintableChar(b)) { pos = i; break outer; }
                    if(!isNumber(b)) throw new InputMismatchException();
                    if(minus) {
                        if(n < - 922337203685477580l) throw new ArithmeticException("long overflow");
                        if(n == - 922337203685477580l && b > 56) throw new ArithmeticException("long overflow");
                        n = (n << 3) + (n << 1) + 48 - b;
                    }else {
                        if(n > 922337203685477580l) throw new ArithmeticException("long overflow");
                        if(n == 922337203685477580l && b >= 56) throw new ArithmeticException("long overflow");
                        n = (n << 3) + (n << 1) - 48 + b;
                    }
                }
                readBuf();
            }
            return n;
        }
        public final double nextDouble() { return Double.parseDouble(next()); }

        public final void close() {
            try { in.close(); }
            catch(IOException e) { e.printStackTrace(); }
        }
    }

    private static final class FastWriter {
        private static final byte[] INT_MIN_BYTES = {45, 50, 49, 52, 55, 52, 56, 51, 54, 52, 56};
        private static final byte[] LONG_MIN_BYTES = {45, 57, 50, 50, 51, 51, 55, 50, 48, 51, 54, 56, 53, 52, 55, 55, 53, 56, 48, 56};
        private static final int BUFFER_SIZE = 1 << 13;
        private static final int PRECISION = 6;
        private static final DataOutputStream out = new DataOutputStream(System.out);
        private static final byte[] buffer = new byte[BUFFER_SIZE];
        private static int ptr = 0;

        public static void println() throws IOException {
            if (ptr + 1 > BUFFER_SIZE) writeBuffer();
            buffer[ptr++] = '\n';
        }

        public static void print(int v) throws IOException {
            int digits = countDigits(v);
            if (ptr + digits + 1 > BUFFER_SIZE) writeBuffer();
            if (v == Integer.MIN_VALUE) {
                for (int i = 0; i < 11; i++) buffer[ptr++] = INT_MIN_BYTES[i];
            } else if (v == 0) {
                buffer[ptr++] = '0';
            } else {
                if (v < 0) {
                    buffer[ptr++] = '-';
                    v = -v;
                }
                for (int i = ptr + digits - 1; i >= ptr; i--) {
                    buffer[i] = (byte) ('0' + v % 10);
                    v /= 10;
                }
                ptr += digits;
            }
        }

        public static void print(long v) throws IOException {
            int digits = countDigits(v);
            if (ptr + digits + 1 > BUFFER_SIZE) writeBuffer();
            if (v == Long.MIN_VALUE) {
                for (int i = 0; i < 20; i++) buffer[ptr++] = LONG_MIN_BYTES[i];
            } else if (v == 0) {
                buffer[ptr++] = '0';
            } else {
                if (v < 0) {
                    buffer[ptr++] = '-';
                    v = -v;
                }
                for (int i = ptr + digits - 1; i >= ptr; i--) {
                    buffer[i] = (byte) ('0' + v % 10);
                    v /= 10;
                }
                ptr += digits;
            }
        }

        public static void print(float v) throws IOException {
            if (ptr + 12 + PRECISION > BUFFER_SIZE) writeBuffer();
            print((int) v);
            if (v < 0) v = -v;
            buffer[ptr++] = '.';
            var x = v - (int) v;
            for (int i = 0; i < PRECISION - 1; i++) buffer[ptr++] = (byte) ('0' + (x *= 10) % 10);
            buffer[ptr++] = (byte) ('0' + Math.round(x * 10) % 10);
        }

        public static void print(double v) throws IOException {
            if (ptr + 21 + PRECISION > BUFFER_SIZE) writeBuffer();
            print((long) v);
            if (v < 0) v = -v;
            buffer[ptr++] = '.';
            var x = v - (long) v;
            for (int i = 0; i < PRECISION - 1; i++) buffer[ptr++] = (byte) ('0' + (x *= 10) % 10);
            buffer[ptr++] = (byte) ('0' + Math.round(x * 10) % 10);
        }

        public static void print(String v) throws IOException {
            for (int i = 0, n = v.length(); i < n; i++) print(v.charAt(i));
        }

        public static void print(char v) throws IOException {
            if (ptr >= BUFFER_SIZE) writeBuffer();
            buffer[ptr++] = (byte) v;
        }

        public static void println(int v) throws IOException {
            print(v);
            println();
        }

        public static void println(long v) throws IOException {
            print(v);
            println();
        }

        public static void println(float v) throws IOException {
            print(v);
            println();
        }

        public static void println(double v) throws IOException {
            print(v);
            println();
        }

        public static void println(String v) throws IOException {
            print(v);
            println();
        }

        public static void println(char v) throws IOException {
            print(v);
            println();
        }

        public static void flush() throws IOException {
            writeBuffer();
            out.flush();
        }

        private static int countDigits(int v) {
            if (v >= 1000000000) return 10;
            if (v >= 100000000) return 9;
            if (v >= 10000000) return 8;
            if (v >= 1000000) return 7;
            if (v >= 100000) return 6;
            if (v >= 10000) return 5;
            if (v >= 1000) return 4;
            if (v >= 100) return 3;
            if (v >= 10) return 2;
            return 1;
        }

        private static int countDigits(long v) {
            if (v >= 1000000000000000000L) return 19;
            if (v >= 100000000000000000L) return 18;
            if (v >= 10000000000000000L) return 17;
            if (v >= 1000000000000000L) return 16;
            if (v >= 100000000000000L) return 15;
            if (v >= 10000000000000L) return 14;
            if (v >= 1000000000000L) return 13;
            if (v >= 100000000000L) return 12;
            if (v >= 10000000000L) return 11;
            if (v >= 1000000000L) return 10;
            if (v >= 100000000L) return 9;
            if (v >= 10000000L) return 8;
            if (v >= 1000000L) return 7;
            if (v >= 100000L) return 6;
            if (v >= 10000L) return 5;
            if (v >= 1000L) return 4;
            if (v >= 100L) return 3;
            if (v >= 10L) return 2;
            return 1;
        }

        private static void writeBuffer() throws IOException {
            out.write(buffer, 0, ptr);
            ptr = 0;
        }
    }
}
