package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/04/23.
 */
@SuppressWarnings("unchecked")
public class FamilyDAG {

    private static class FlaggedPerson implements Comparable<FlaggedPerson> {
        String name;
        boolean isHillbilly;
        boolean isParadox;

        public FlaggedPerson(String name, boolean isHillbilly, boolean isParadox) {
            this.name = name;
            this.isHillbilly = isHillbilly;
            this.isParadox = isParadox;
        }

        @Override
        public int compareTo(FlaggedPerson other) {
            return name.compareTo(other.name);
        }
    }

    private static class Result {
        boolean isHillbilly;
        boolean isParadox;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int familyID = 1;
        String line = FastReader.getLine();
        while (line != null) {
            List<Integer>[] adjacencyList = new List[200];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Map<String, Integer> nameToIDMap = new HashMap<>();
            String[] idToName = new String[adjacencyList.length];

            while (!line.equals("done")) {
                String[] data = line.split(" ");
                String parentName = data[0];
                String childName = data[2];

                int parentID = getPersonID(parentName, nameToIDMap, idToName);
                int childID = getPersonID(childName, nameToIDMap, idToName);
                adjacencyList[childID].add(parentID);
                line = FastReader.getLine();
            }

            if (familyID > 1) {
                outputWriter.printLine();
            }
            List<FlaggedPerson> flaggedPersons = analyzeFamilies(adjacencyList, idToName, nameToIDMap.size());
            for (FlaggedPerson person : flaggedPersons) {
                outputWriter.print(person.name);
                if (person.isParadox) {
                    outputWriter.printLine(" paradox");
                } else {
                    outputWriter.printLine(" hillbilly");
                }
            }
            familyID++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int getPersonID(String name, Map<String, Integer> nameToIDMap, String[] idToName) {
        if (!nameToIDMap.containsKey(name)) {
            int personID = nameToIDMap.size();
            nameToIDMap.put(name, personID);
            idToName[personID] = name;
        }
        return nameToIDMap.get(name);
    }

    private static List<FlaggedPerson> analyzeFamilies(List<Integer>[] adjacencyList, String[] idToName,
                                                       int numberOfPeople) {
        List<FlaggedPerson> flaggedPersons = new ArrayList<>();

        for (int personID = 0; personID < numberOfPeople; personID++) {
            boolean[] visited = new boolean[numberOfPeople];

            Result result = new Result();
            depthFirstSearch(adjacencyList, visited, result, personID, personID);
            if (result.isParadox) {
                flaggedPersons.add(new FlaggedPerson(idToName[personID], false, true));
            } else if (result.isHillbilly) {
                flaggedPersons.add(new FlaggedPerson(idToName[personID], true, false));
            }
        }
        Collections.sort(flaggedPersons);
        return flaggedPersons;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, Result result,
                                         int sourcePersonID, int personID) {
        visited[personID] = true;

        for (int parentID : adjacencyList[personID]) {
            if (visited[parentID]) {
                if (parentID == sourcePersonID) {
                    result.isParadox = true;
                    return;
                } else {
                    result.isHillbilly = true;
                }
            } else {
                depthFirstSearch(adjacencyList, visited, result, sourcePersonID, parentID);
            }
        }
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
