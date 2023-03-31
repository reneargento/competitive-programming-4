package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/03/23.
 */
@SuppressWarnings("unchecked")
public class ManagingPackaging {

    private static class Package implements Comparable<Package> {
        int id;
        String name;

        public Package(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int compareTo(Package other) {
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int packagesNumber = FastReader.nextInt();
        int caseID = 1;

        while (packagesNumber != 0) {
            Map<String, Integer> packageNameToID = new HashMap<>();
            String[] packageNames = new String[packagesNumber];
            int[] inDegrees = new int[packagesNumber];
            List<Integer>[] adjacencyList = new List[packagesNumber];
            for (int packageID = 0; packageID < adjacencyList.length; packageID++) {
                adjacencyList[packageID] = new ArrayList<>();
            }

            for (int i = 0; i < packagesNumber; i++) {
                String[] data = FastReader.getLine().split(" ");
                String packageName = data[0];
                int packageID = getPackageID(packageName, packageNameToID, packageNames);

                for (int dependency = 1; dependency < data.length; dependency++) {
                    int dependencyID = getPackageID(data[dependency], packageNameToID, packageNames);
                    adjacencyList[dependencyID].add(packageID);
                    inDegrees[packageID]++;
                }
            }

            if (caseID > 1) {
                outputWriter.printLine();
            }

            List<String> installationOrder = computeOrder(adjacencyList, packageNames, inDegrees);
            if (!installationOrder.isEmpty() && installationOrder.size() == adjacencyList.length) {
                for (String packageName : installationOrder) {
                    outputWriter.printLine(packageName);
                }
            } else {
                outputWriter.printLine("cannot be ordered");
            }

            caseID++;
            packagesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<String> computeOrder(List<Integer>[] adjacencyList, String[] packageNames, int[] inDegrees) {
        List<String> order = new ArrayList<>();
        PriorityQueue<Package> priorityQueue = new PriorityQueue<>();
        for (int packageID = 0; packageID < inDegrees.length; packageID++) {
            if (inDegrees[packageID] == 0) {
                priorityQueue.offer(new Package(packageID, packageNames[packageID]));
            }
        }

        while (!priorityQueue.isEmpty()) {
            Package currentPackage = priorityQueue.poll();
            order.add(currentPackage.name);

            for (int neighborID : adjacencyList[currentPackage.id]) {
                inDegrees[neighborID]--;
                if (inDegrees[neighborID] == 0) {
                    priorityQueue.offer(new Package(neighborID, packageNames[neighborID]));
                }
            }
        }
        return order;
    }

    private static int getPackageID(String packageName, Map<String, Integer> packageNameToID, String[] packageNames) {
        if (!packageNameToID.containsKey(packageName)) {
            int packageID = packageNameToID.size();
            packageNameToID.put(packageName, packageID);
            packageNames[packageID] = packageName;
        }
        return packageNameToID.get(packageName);
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
