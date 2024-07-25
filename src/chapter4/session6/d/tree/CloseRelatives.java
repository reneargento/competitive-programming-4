package chapter4.session6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/07/24.
 */
@SuppressWarnings("unchecked")
public class CloseRelatives {

    private static final int MAX_NAMES = 5001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        List<Integer>[] adjacencyList = new List[MAX_NAMES];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        Map<String, Integer> nameToIdMap = new HashMap<>();
        String[] idToName = new String[MAX_NAMES];
        int rootId = -1;

        while (line != null) {
            String[] names = line.split(",");
            int childId = getNameId(names[0], nameToIdMap, idToName);
            if (rootId == -1) {
                rootId = childId;
            }

            for (int i = 1; i < names.length; i++) {
                int parentId = getNameId(names[i], nameToIdMap, idToName);
                adjacencyList[childId].add(parentId);
            }
            line = FastReader.getLine();
        }

        List<List<String>> familyLists = computeFamilyLists(adjacencyList, idToName, rootId);
        outputWriter.printLine(familyLists.size());

        for (List<String> familyList : familyLists) {
            outputWriter.printLine();
            for (String name : familyList) {
                outputWriter.printLine(name);
            }
        }
        outputWriter.flush();
    }

    private static int getNameId(String name, Map<String, Integer> nameToIdMap, String[] idToName) {
        if (!nameToIdMap.containsKey(name)) {
            int nameId = nameToIdMap.size();
            nameToIdMap.put(name, nameId);
            idToName[nameId] = name;
        }
        return nameToIdMap.get(name);
    }

    private static List<List<String>> computeFamilyLists(List<Integer>[] adjacencyList, String[] idToName, int rootId) {
        List<List<String>> familyLists = new ArrayList<>();

        List<Integer>[] mirrorAdjacencyList = new List[MAX_NAMES];
        for (int i = 0; i < adjacencyList.length; i++) {
            mirrorAdjacencyList[i] = new ArrayList<>(adjacencyList[i]);
            Collections.reverse(mirrorAdjacencyList[i]);
        }

        List<String> familyListLeftToRight = new ArrayList<>();
        List<String> familyListRightToLeft = new ArrayList<>();
        traverseTree(adjacencyList, mirrorAdjacencyList, rootId, idToName, familyListLeftToRight, true);
        traverseTree(adjacencyList, mirrorAdjacencyList, rootId, idToName, familyListRightToLeft, false);

        Collections.reverse(familyListLeftToRight);
        Collections.reverse(familyListRightToLeft);

        familyLists.add(familyListLeftToRight);
        if (areListsDifferent(familyListLeftToRight, familyListRightToLeft)) {
            familyLists.add(familyListRightToLeft);
        }
        return familyLists;
    }

    private static void traverseTree(List<Integer>[] adjacencyList, List<Integer>[] mirrorAdjacencyList, int nodeId,
                                     String[] idToName, List<String> familyList, boolean leftToRight) {
        familyList.add(idToName[nodeId]);

        if (leftToRight) {
            for (int parentId : adjacencyList[nodeId]) {
                traverseTree(adjacencyList, mirrorAdjacencyList, parentId, idToName, familyList, true);
            }
        } else {
            for (int parentId : mirrorAdjacencyList[nodeId]) {
                traverseTree(adjacencyList, mirrorAdjacencyList, parentId, idToName, familyList, false);
            }
        }
    }

    private static boolean areListsDifferent(List<String> list1, List<String> list2) {
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
