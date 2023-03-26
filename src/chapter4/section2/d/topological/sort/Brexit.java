package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/03/23.
 */
@SuppressWarnings("unchecked")
public class Brexit {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int countries = FastReader.nextInt();
        int partnerships = FastReader.nextInt();
        int homeCountry = FastReader.nextInt() - 1;
        int firstExitCountry = FastReader.nextInt() - 1;
        int[] inDegrees = new int[countries];

        List<Integer>[] adjacencyList = new ArrayList[countries];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < partnerships; i++) {
            int country1 = FastReader.nextInt() - 1;
            int country2 = FastReader.nextInt() - 1;
            adjacencyList[country1].add(country2);
            adjacencyList[country2].add(country1);
            inDegrees[country1]++;
            inDegrees[country2]++;
        }
        boolean willStay = willStay(adjacencyList, inDegrees, homeCountry, firstExitCountry);
        outputWriter.printLine(willStay ? "stay" : "leave");
        outputWriter.flush();
    }

    private static boolean willStay(List<Integer>[] adjacencyList, int[] inDegrees, int homeCountry,
                                    int firstExitCountry) {
        if (firstExitCountry == homeCountry) {
            return false;
        }
        int[] partnersThatLeft = new int[adjacencyList.length];
        boolean[] visited = new boolean[adjacencyList.length];
        int[] requiredNumberToLeave = computeRequiredNumberToLeave(inDegrees);

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(firstExitCountry);
        visited[firstExitCountry] = true;

        while (!queue.isEmpty()) {
            int countryID = queue.poll();

            for (int neighborID : adjacencyList[countryID]) {
                partnersThatLeft[neighborID]++;

                if (partnersThatLeft[neighborID] >= requiredNumberToLeave[neighborID] && !visited[neighborID]) {
                    queue.offer(neighborID);
                    visited[neighborID] = true;

                    if (neighborID == homeCountry) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static int[] computeRequiredNumberToLeave(int[] inDegrees) {
        int[] requiredNumberToLeave = new int[inDegrees.length];
        for (int countryID = 0; countryID < inDegrees.length; countryID++) {
            requiredNumberToLeave[countryID] = (int) Math.ceil(inDegrees[countryID] / 2.0);
        }
        return requiredNumberToLeave;
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
