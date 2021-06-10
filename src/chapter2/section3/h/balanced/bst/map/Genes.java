package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/06/21.
 */
public class Genes {

    private static class PersonData implements Comparable<PersonData> {
        String name;
        String geneType;
        int parentsDescribed;

        public PersonData(String name, String geneType) {
            this.name = name;
            this.geneType = geneType;
        }

        @Override
        public int compareTo(PersonData other) {
            if (parentsDescribed != other.parentsDescribed) {
                return Integer.compare(other.parentsDescribed, parentsDescribed);
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int lines = FastReader.nextInt();
        Map<String, PersonData> personToGeneMap = new HashMap<>();
        Map<String, List<String>> parentToChildrenMap = new HashMap<>();
        Map<String, List<String>> childrenToParentMap = new HashMap<>();

        for (int i = 0; i < lines; i++) {
            String name = FastReader.next();
            String part2 = FastReader.next();

            if (isGeneDescription(part2)) {
                personToGeneMap.put(name, new PersonData(name, part2));
            } else {
                if (!parentToChildrenMap.containsKey(name)) {
                    parentToChildrenMap.put(name, new ArrayList<>());
                }
                if (!childrenToParentMap.containsKey(part2)) {
                    childrenToParentMap.put(part2, new ArrayList<>());
                }

                parentToChildrenMap.get(name).add(part2);
                childrenToParentMap.get(part2).add(name);
            }
        }
        computeChildGenes(personToGeneMap, parentToChildrenMap, childrenToParentMap);
        printResults(personToGeneMap);
    }

    private static void computeChildGenes(Map<String, PersonData> personToGeneMap,
                                          Map<String, List<String>> parentToChildrenMap,
                                          Map<String, List<String>> childrenToParentMap) {
        TreeSet<PersonData> peopleTree = new TreeSet<>();
        Map<String, PersonData> personToComputeGeneMap = new HashMap<>();

        for (String child : childrenToParentMap.keySet()) {
            if (!personToGeneMap.containsKey(child)) {
                List<String> parents = childrenToParentMap.get(child);
                int parentsDescribed = 0;

                for (String parent : parents) {
                    if (personToGeneMap.containsKey(parent)) {
                        parentsDescribed++;
                    }
                }
                PersonData personData = new PersonData(child, null);
                personData.parentsDescribed = parentsDescribed;
                peopleTree.add(personData);
                personToComputeGeneMap.put(child, personData);
            }
        }

        while (!peopleTree.isEmpty() ) {
            PersonData personData = peopleTree.pollFirst();
            String name = personData.name;
            List<String> parents = childrenToParentMap.get(name);
            String gene1 = personToGeneMap.get(parents.get(0)).geneType;
            String gene2 = personToGeneMap.get(parents.get(1)).geneType;

            personData.geneType = computeGeneType(gene1, gene2);
            personToGeneMap.put(name, personData);

            if (parentToChildrenMap.containsKey(name)) {
                for (String child : parentToChildrenMap.get(name)) {
                    PersonData childData = personToComputeGeneMap.get(child);
                    peopleTree.remove(childData);
                    childData.parentsDescribed++;
                    peopleTree.add(childData);
                }
            }
        }
    }

    private static String computeGeneType(String gene1, String gene2) {
        if ((!gene1.equals("non-existent") && !gene2.equals("non-existent"))
                || gene1.equals("dominant") || gene2.equals("dominant")) {
            if ((gene1.equals("dominant") && gene2.equals("dominant"))
                    || (gene1.equals("dominant") && gene2.equals("recessive"))
                    || (gene1.equals("recessive") && gene2.equals("dominant"))) {
                return "dominant";
            } else {
                return "recessive";
            }
        } else {
            return "non-existent";
        }
    }

    private static void printResults(Map<String, PersonData> personToGeneMap) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<PersonData> personToGeneList = new ArrayList<>(personToGeneMap.values());
        personToGeneList.sort(new Comparator<PersonData>() {
            @Override
            public int compare(PersonData person1, PersonData person2) {
                return person1.name.compareTo(person2.name);
            }
        });

        for (PersonData person : personToGeneList) {
            outputWriter.printLine(person.name + " " + person.geneType);
        }

        outputWriter.flush();
    }

    private static boolean isGeneDescription(String word) {
        return word.equals("dominant") || word.equals("recessive") || word.equals("non-existent");
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
