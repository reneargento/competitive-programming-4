package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/03/23.
 */
@SuppressWarnings("unchecked")
public class Conservation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] stagesLocation = new int[FastReader.nextInt()];
            int dependencies = FastReader.nextInt();

            int[] inDegree = new int[stagesLocation.length];
            List<Integer>[] adjacencyList = new ArrayList[stagesLocation.length];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < stagesLocation.length; i++) {
                stagesLocation[i] = FastReader.nextInt();
            }

            for (int i = 0; i < dependencies; i++) {
                int stageID1 = FastReader.nextInt() - 1;
                int stageID2 = FastReader.nextInt() - 1;
                adjacencyList[stageID1].add(stageID2);
                inDegree[stageID2]++;
            }
            int minimumTransportRequired = computeMinimumTransportRequired(adjacencyList, stagesLocation, inDegree);
            outputWriter.printLine(minimumTransportRequired);
        }
        outputWriter.flush();
    }

    private static int computeMinimumTransportRequired(List<Integer>[] adjacencyList, int[] stagesLocation,
                                                       int[] inDegree) {
        int[] inDegreeCopy = new int[inDegree.length];
        System.arraycopy(inDegree, 0, inDegreeCopy, 0, inDegree.length);

        int transportStartingFromLab1 = computeMinimumTransportRequired(adjacencyList, stagesLocation,
                inDegreeCopy, true);
        int transportStartingFromLab2 = computeMinimumTransportRequired(adjacencyList, stagesLocation,
                inDegree, false);
        return Math.min(transportStartingFromLab1, transportStartingFromLab2);
    }

    private static int computeMinimumTransportRequired(List<Integer>[] adjacencyList, int[] stagesLocation,
                                                       int[] inDegree, boolean startFromFirstLab) {
        int minimumTransportRequired = 0;
        Queue<Integer> queueLab1 = new LinkedList<>();
        Queue<Integer> queueLab2 = new LinkedList<>();
        int currentLaboratory = -1;

        for (int stageID = 0; stageID < inDegree.length; stageID++) {
            if (inDegree[stageID] == 0) {
                if (stagesLocation[stageID] == 1) {
                    queueLab1.offer(stageID);
                } else {
                    queueLab2.offer(stageID);
                }
            }
        }

        while (!queueLab1.isEmpty() || !queueLab2.isEmpty()) {
            int stageID;
            if ((!queueLab1.isEmpty() &&
                    ((currentLaboratory == -1 && startFromFirstLab) || currentLaboratory == 1))
                    || queueLab2.isEmpty()) {
                stageID = queueLab1.poll();
            } else {
                stageID = queueLab2.poll();
            }

            if (currentLaboratory != -1 && currentLaboratory != stagesLocation[stageID]) {
                minimumTransportRequired++;
            }
            currentLaboratory = stagesLocation[stageID];

            for (int neighborID : adjacencyList[stageID]) {
                inDegree[neighborID]--;
                if (inDegree[neighborID] == 0) {
                    int nextStageLaboratory = stagesLocation[neighborID];
                    if (nextStageLaboratory == 1) {
                        queueLab1.offer(neighborID);
                    } else {
                        queueLab2.offer(neighborID);
                    }
                }
            }
        }
        return minimumTransportRequired;
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
