package chapter4.session6.d.tree;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/07/24.
 */
@SuppressWarnings("unchecked")
public class AnotherCrisis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberOfEmployees = FastReader.nextInt();
        int pressurePercentage = FastReader.nextInt();

        while (numberOfEmployees != 0 || pressurePercentage != 0) {
            List<Integer>[] adjacencyList = new List[numberOfEmployees + 1];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int employeeId = 1; employeeId < adjacencyList.length; employeeId++) {
                int directBoss = FastReader.nextInt();
                adjacencyList[directBoss].add(employeeId);
            }

            int minimumPetitionsNeeded = computeMinimumPetitionsNeeded(adjacencyList, pressurePercentage, 0);
            outputWriter.printLine(minimumPetitionsNeeded);

            numberOfEmployees = FastReader.nextInt();
            pressurePercentage = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumPetitionsNeeded(List<Integer>[] adjacencyList, int pressurePercentage,
                                                     int employeeId) {
        if (adjacencyList[employeeId].isEmpty()) {
            return 1;
        }

        int directSubordinates = adjacencyList[employeeId].size();
        int petitionsRequired = (int) Math.ceil(directSubordinates * pressurePercentage / 100.0);

        List<Integer> petitionsByChild = new ArrayList<>();
        for (int subordinateId : adjacencyList[employeeId]) {
            int petitions = computeMinimumPetitionsNeeded(adjacencyList, pressurePercentage, subordinateId);
            petitionsByChild.add(petitions);
        }

        Collections.sort(petitionsByChild);

        int totalPetitionsSigned = 0;
        for (int i = 0; i < petitionsRequired; i++) {
            totalPetitionsSigned += petitionsByChild.get(i);
        }
        return totalPetitionsSigned;
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
