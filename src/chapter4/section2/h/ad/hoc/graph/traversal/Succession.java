package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/04/23.
 */
@SuppressWarnings("unchecked")
public class Succession {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int familyRelations = FastReader.nextInt();
        int[] throneClaimerIDs = new int[FastReader.nextInt()];
        String founderName = FastReader.next();
        int maxPeople = familyRelations * 3;
        int[] inDegrees = new int[maxPeople];

        Map<String, Integer> nameToIDMap = new HashMap<>();
        String[] idToName = new String[maxPeople];
        List<Integer>[] adjacencyList = new List[maxPeople];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < familyRelations; i++) {
            String childName = FastReader.next();
            String parentName1 = FastReader.next();
            String parentName2 = FastReader.next();

            int childID = getPersonID(childName, nameToIDMap, idToName);
            int parentID1 = getPersonID(parentName1, nameToIDMap, idToName);
            int parentID2 = getPersonID(parentName2, nameToIDMap, idToName);
            adjacencyList[parentID1].add(childID);
            adjacencyList[parentID2].add(childID);
            inDegrees[childID] += 2;
        }

        for (int i = 0; i < throneClaimerIDs.length; i++) {
            String personName = FastReader.next();
            int personID = getPersonID(personName, nameToIDMap, idToName);
            throneClaimerIDs[i] = personID;
        }

        int founderID = getPersonID(founderName, nameToIDMap, idToName);
        double[] bloodLevels = computeBloodLevels(adjacencyList, inDegrees, founderID);
        String claimantWithMostBlood = null;
        double highestBloodLevel = -1;

        for (int claimerID : throneClaimerIDs) {
            if (bloodLevels[claimerID] > highestBloodLevel) {
                highestBloodLevel = bloodLevels[claimerID];
                claimantWithMostBlood = idToName[claimerID];
            }
        }
        outputWriter.printLine(claimantWithMostBlood);
        outputWriter.flush();
    }

    private static double[] computeBloodLevels(List<Integer>[] adjacencyList, int[] inDegrees, int founderID) {
        double[] bloodLevels = new double[adjacencyList.length];
        List<Integer> topologicalSort = computeTopologicalSort(adjacencyList, inDegrees);
        bloodLevels[founderID] = 1;

        for (int personID : topologicalSort) {
            double bloodLevelToChildren = bloodLevels[personID] / 2.0;

            for (int childID : adjacencyList[personID]) {
                bloodLevels[childID] += bloodLevelToChildren;
            }
        }
        return bloodLevels;
    }

    private static List<Integer> computeTopologicalSort(List<Integer>[] adjacencyList, int[] inDegrees) {
        List<Integer> topologicalSort = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int personID = 0; personID < inDegrees.length; personID++) {
            if (inDegrees[personID] == 0) {
                queue.offer(personID);
            }
        }

        while (!queue.isEmpty()) {
            int personID = queue.poll();
            topologicalSort.add(personID);

            for (int childID : adjacencyList[personID]) {
                inDegrees[childID]--;
                if (inDegrees[childID] == 0) {
                    queue.offer(childID);
                }
            }
        }
        return topologicalSort;
    }

    private static int getPersonID(String name, Map<String, Integer> nameToIDMap, String[] idToName) {
        if (!nameToIDMap.containsKey(name)) {
            int personID = nameToIDMap.size();
            nameToIDMap.put(name, personID);
            idToName[personID] = name;
        }
        return nameToIDMap.get(name);
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
