package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/03/23.
 */
@SuppressWarnings("unchecked")
public class BuildDependencies {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rules = FastReader.nextInt();

        Map<String, Integer> nameToIDMap = new HashMap<>();
        String[] idToName = new String[rules];
        int[] inDegrees = new int[rules];

        List<Integer>[] adjacencyList = new ArrayList[rules];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < rules; i++) {
            String[] values = FastReader.getLine().split(" ");
            String filename = values[0].substring(0, values[0].length() - 1);
            int fileID = getFileID(filename, nameToIDMap, idToName);

            for (int dependency = 1; dependency < values.length; dependency++) {
                int dependencyID = getFileID(values[dependency], nameToIDMap, idToName);
                adjacencyList[dependencyID].add(fileID);
                inDegrees[fileID]++;
            }
        }

        String modifiedFile = FastReader.next();
        int modifiedFileID = getFileID(modifiedFile, nameToIDMap, idToName);

        List<String> filesToRecompile = computeFilesToRecompile(adjacencyList, idToName, inDegrees, modifiedFileID);
        for (String fileName : filesToRecompile) {
            outputWriter.printLine(fileName);
        }
        outputWriter.flush();
    }

    private static int getFileID(String filename, Map<String, Integer> nameToIDMap, String[] idToName) {
        if (!nameToIDMap.containsKey(filename)) {
            int fileID = nameToIDMap.size();
            nameToIDMap.put(filename, fileID);
            idToName[fileID] = filename;
        }
        return nameToIDMap.get(filename);
    }

    private static List<String> computeFilesToRecompile(List<Integer>[] adjacencyList, String[] idToName,
                                                        int[] inDegrees, int modifiedFileID) {
        List<String> filesToRecompile = new ArrayList<>();
        removeUnrelatedFiles(adjacencyList, inDegrees, modifiedFileID);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(modifiedFileID);

        while (!queue.isEmpty()) {
            int fileID = queue.poll();
            String fileName = idToName[fileID];
            filesToRecompile.add(fileName);

            for (int neighborID : adjacencyList[fileID]) {
                inDegrees[neighborID]--;
                if (inDegrees[neighborID] == 0) {
                    queue.offer(neighborID);
                }
            }
        }
        return filesToRecompile;
    }

    private static void removeUnrelatedFiles(List<Integer>[] adjacencyList, int[] inDegrees, int modifiedFileID) {
        Queue<Integer> queue = new LinkedList<>();
        for (int fileID = 0; fileID < inDegrees.length; fileID++) {
            if (fileID == modifiedFileID) {
                continue;
            }
            if (inDegrees[fileID] == 0) {
                queue.offer(fileID);
            }
        }

        while (!queue.isEmpty()) {
            int fileID = queue.poll();

            for (int neighborID : adjacencyList[fileID]) {
                if (neighborID != modifiedFileID) {
                    inDegrees[neighborID]--;
                    if (inDegrees[neighborID] == 0) {
                        queue.offer(neighborID);
                    }
                }
            }
        }
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
