package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/03/22.
 */
// Based on the discussion in https://codeforces.com/blog/entry/75637
@SuppressWarnings("unchecked")
public class MyAncestor {

    private static class Person {
        int index;
        int skill;

        public Person(int index, int skill) {
            this.index = index;
            this.skill = skill;
        }
    }

    private static class Query {
        int skill;
        int result;

        public Query(int skill) {
            this.skill = skill;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int persons = Integer.parseInt(data[0]);
            int queriesNumber = Integer.parseInt(data[1]);

            List<Integer>[] children = new List[persons];
            for (int i = 0; i < children.length; i++) {
                children[i] = new ArrayList<>();
            }

            Person[] ancestors = new Person[persons];
            ancestors[0] = new Person(0, 1);
            for (int i = 1; i < ancestors.length; i++) {
                int parent = FastReader.nextInt();
                ancestors[i] = new Person(i, FastReader.nextInt());
                children[parent].add(i);
            }

            Map<Integer, List<Query>> queriesMap = new HashMap<>();
            Query[] queries = new Query[queriesNumber];
            for (int q = 0; q < queries.length; q++) {
                int person = FastReader.nextInt();
                int skill = FastReader.nextInt();
                Query query = new Query(skill);
                queries[q] = query;

                if (!queriesMap.containsKey(person)) {
                    queriesMap.put(person, new ArrayList<>());
                }
                queriesMap.get(person).add(query);
            }

            Person[] family = new Person[ancestors.length];
            family[0] = ancestors[0];
            solveQueries(ancestors, children, queriesMap, family, 0);

            for (Query query : queries) {
                outputWriter.printLine(query.result);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void solveQueries(Person[] ancestors, List<Integer>[] children,
                                     Map<Integer, List<Query>> queriesMap, Person[] family, int familyIndex) {
        Person currentPerson = family[familyIndex];
        if (queriesMap.containsKey(currentPerson.index)) {
            List<Query> queryList = queriesMap.get(currentPerson.index);
            for (Query query : queryList) {
                query.result = findAncestor(family, query, 0, familyIndex);
            }
        }

        familyIndex++;
        for (int child : children[currentPerson.index]) {
            family[familyIndex] = ancestors[child];
            solveQueries(ancestors, children, queriesMap, family, familyIndex);
        }
    }

    private static int findAncestor(Person[] family, Query query, int low, int high) {
        while (low <= high) {
            int middle = low + (high - low) / 2;
            int skill = family[middle].skill;

            if (skill < query.skill) {
                low = middle + 1;
            } else {
                int result = middle;
                int candidate = findAncestor(family, query, low, middle - 1);
                if (candidate != -1) {
                    result = candidate;
                }
                return family[result].index;
            }
        }
        return -1;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
