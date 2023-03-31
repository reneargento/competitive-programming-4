package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/03/23.
 */
@SuppressWarnings("unchecked")
public class ThroughTheGrapevine {

    private static class PersonDay {
        int personID;
        int day;

        public PersonDay(int personID, int day) {
            this.personID = personID;
            this.day = day;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        int connections = FastReader.nextInt();
        int elapsedDays = FastReader.nextInt();

        int[] skepticismLevel = new int[adjacencyList.length];
        Map<String, Integer> nameToIDMap = new HashMap<>();

        for (int personID = 0; personID < adjacencyList.length; personID++) {
            String name = FastReader.next();
            skepticismLevel[personID] = FastReader.nextInt();
            nameToIDMap.put(name, personID);
            adjacencyList[personID] = new ArrayList<>();
        }
        for (int i = 0; i < connections; i++) {
            int personID1 = nameToIDMap.get(FastReader.next());
            int personID2 = nameToIDMap.get(FastReader.next());
            adjacencyList[personID1].add(personID2);
            adjacencyList[personID2].add(personID1);
        }
        int originPersonID = nameToIDMap.get(FastReader.next());

        int peopleThatHeardTheRumor = countPeopleThatHeardTheRumor(adjacencyList, skepticismLevel, elapsedDays,
                originPersonID);
        outputWriter.printLine(peopleThatHeardTheRumor);
        outputWriter.flush();
    }

    private static int countPeopleThatHeardTheRumor(List<Integer>[] adjacencyList, int[] skepticismLevel,
                                                    int elapsedDays, int originPersonID) {
        int peopleThatHeardTheRumor = 0;
        boolean[] visited = new boolean[adjacencyList.length];
        int[] sourcesHeard = new int[adjacencyList.length];

        Queue<PersonDay> queue = new LinkedList<>();
        queue.offer(new PersonDay(originPersonID, 1));
        visited[originPersonID] = true;

        while (!queue.isEmpty()) {
            PersonDay personDay = queue.poll();
            if (personDay.day > elapsedDays) {
                break;
            }

            for (int neighborID : adjacencyList[personDay.personID]) {
                if (!visited[neighborID]) {
                    peopleThatHeardTheRumor++;
                    visited[neighborID] = true;
                }

                sourcesHeard[neighborID]++;
                if (sourcesHeard[neighborID] == skepticismLevel[neighborID]) {
                    queue.offer(new PersonDay(neighborID, personDay.day + 1));
                }
            }
        }
        return peopleThatHeardTheRumor;
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
