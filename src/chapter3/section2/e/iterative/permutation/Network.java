package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/11/21.
 */
public class Network {

    private static class Packet {
        int message;
        int startByte;
        int endByte;

        public Packet(int message, int startByte, int endByte) {
            this.message = message;
            this.startByte = startByte;
            this.endByte = endByte;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int messages = FastReader.nextInt();
        int packetsNumber = FastReader.nextInt();
        int caseId = 1;

        while (messages != 0 || packetsNumber != 0) {
            int[] messageSizes = new int[messages + 1];
            for (int i = 1; i < messageSizes.length; i++) {
                messageSizes[i] = FastReader.nextInt();
            }

            Packet[] packets = new Packet[packetsNumber];
            for (int i = 0; i < packets.length; i++) {
                packets[i] = new Packet(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }

            int bufferSize = computeBufferSize(messageSizes, packets);
            outputWriter.printLine(String.format("Case %d: %d\n", caseId, bufferSize));

            caseId++;
            messages = FastReader.nextInt();
            packetsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeBufferSize(int[] messageSizes, Packet[] packets) {
        int[] order = new int[messageSizes.length - 1];
        for (int i = 0; i < order.length; i++) {
            order[i] = i + 1;
        }
        int[] currentOrder = new int[order.length];
        return computeBufferSize(messageSizes, packets, 0, 0, order, currentOrder);
    }

    private static int computeBufferSize(int[] messageSizes, Packet[] packets, int mask, int index,
                                         int[] order, int[] currentOrder) {
        if (mask == (1 << order.length) - 1) {
            int[] messageSizesCopy = new int[messageSizes.length];
            System.arraycopy(messageSizes, 0, messageSizesCopy, 0, messageSizes.length);
            return computeBufferSize(messageSizesCopy, packets, currentOrder);
        }

        int minBufferSize = Integer.MAX_VALUE;
        for (int i = 0; i < order.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                currentOrder[index] = order[i];
                int bufferSize = computeBufferSize(messageSizes, packets, newMask, index + 1,
                        order, currentOrder);
                minBufferSize = Math.min(minBufferSize, bufferSize);
            }
        }
        return minBufferSize;
    }

    private static int computeBufferSize(int[] messageSizes, Packet[] packets, int[] order) {
        int maxBufferSize = 0;
        int bufferSize = 0;
        int currentMessageIndex = 0;
        Map<Integer, List<Packet>> packagesPerMessage = new HashMap<>();

        Map<Integer, Integer> nextByteToSendPerMessage = new HashMap<>();
        for (int i = 1; i < messageSizes.length; i++) {
            nextByteToSendPerMessage.put(i, 1);
            packagesPerMessage.put(i, new ArrayList<>());
        }

        for (Packet packet : packets) {
            int message = packet.message;

            packagesPerMessage.get(message).add(packet);
            int packetLength = packet.endByte - packet.startByte + 1;
            bufferSize += packetLength;

            if (canPassBuffer(packet, nextByteToSendPerMessage)
                    && message == order[currentMessageIndex]) {
                int bytesConsumed = removePackagesFromBuffer(packagesPerMessage, nextByteToSendPerMessage,
                        message);
                bufferSize -= bytesConsumed;
                messageSizes[message] -= bytesConsumed;

                while (messageSizes[message] == 0) {
                    currentMessageIndex++;
                    if (currentMessageIndex == order.length) {
                        break;
                    }
                    message = order[currentMessageIndex];

                    bytesConsumed = removePackagesFromBuffer(packagesPerMessage, nextByteToSendPerMessage,
                            message);
                    bufferSize -= bytesConsumed;
                    messageSizes[message] -= bytesConsumed;
                }
            } else {
                maxBufferSize = Math.max(maxBufferSize, bufferSize);
            }
        }
        return maxBufferSize;
    }

    private static boolean canPassBuffer(Packet packet, Map<Integer, Integer> nextByteToSendPerMessage) {
        return nextByteToSendPerMessage.get(packet.message) == packet.startByte;
    }

    private static int removePackagesFromBuffer(Map<Integer, List<Packet>> packagesPerMessage,
                                                Map<Integer, Integer> nextByteToSendPerMessage,
                                                int message) {
        int bytesDeleted = 0;

        while (true) {
            List<Packet> packages = packagesPerMessage.get(message);
            int nextByte = nextByteToSendPerMessage.get(message);
            int indexToRemove = -1;

            for (int i = 0; i < packages.size(); i++) {
                if (packages.get(i).startByte == nextByte) {
                    nextByteToSendPerMessage.put(message, packages.get(i).endByte + 1);
                    indexToRemove = i;
                    bytesDeleted += packages.get(i).endByte - packages.get(i).startByte + 1;
                    break;
                }
            }

            if (indexToRemove != -1) {
                packages.remove(indexToRemove);
            } else {
                break;
            }
        }
        return bytesDeleted;
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
