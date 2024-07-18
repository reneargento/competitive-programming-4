package chapter4.session6.d.tree;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rene Argento on 15/07/24.
 */
// Note that even though the problem description mentions that nodes have only one parent, there is a test case
// where a node has more than one parent! This makes most solutions that would solve a one-parent-only problem get WA.
// Based on https://www.cnblogs.com/devymex/archive/2010/08/12/1798351.html
public class ClimbingTrees {

    private static class Node {
        int parent;
        int position;
        List<Integer> children;

        public Node(int parent, int position) {
            this.parent = parent;
            this.position = position;
            this.children = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Node> tree = new ArrayList<>();
        List<Integer> order = new ArrayList<>();
        List<Integer> depth = new ArrayList<>();

        Map<String, Integer> nameToIdMap = new HashMap<>();

        String line = FastReader.getLine();
        while (!line.startsWith("no.child")) {
            List<String> names = getWords(line);
            int personId2 = getNameId(nameToIdMap, tree, names.get(1));
            int personId1 = getNameId(nameToIdMap, tree, names.get(0));

            tree.get(personId2).children.add(personId1);
            tree.get(personId1).parent = personId2;
            line = FastReader.getLine();
        }

        addVirtualRoot(tree);
        buildRMQTable(tree, order, depth, tree.size() - 1, 0);

        line = FastReader.getLine();
        while (line != null) {
            List<String> names = getWords(line);
            String name1 = names.get(0);
            String name2 = names.get(1);
            if (!nameToIdMap.containsKey(name1) || !nameToIdMap.containsKey(name2)
                    || name1.equals(name2)) {
                outputWriter.printLine("no relation");
                line = FastReader.getLine();
                continue;
            }

            int personId1 = getNameId(nameToIdMap, tree, names.get(0));
            int personId2 = getNameId(nameToIdMap, tree, names.get(1));

            int person1Position = tree.get(personId1).position;
            int person2Position = tree.get(personId2).position;
            if (depth.get(person1Position) > depth.get(person2Position)) {
                int aux = person1Position;
                person1Position = person2Position;
                person2Position = aux;
            }

            int minPosition = Math.min(person1Position, person2Position);
            int maxPosition = Math.max(person1Position, person2Position);
            int lcaDepth = getMinDepth(depth, minPosition, maxPosition + 1);
            int lcaId = order.get(lcaDepth);

            if (tree.get(lcaId).parent == -1) {
                outputWriter.printLine("no relation");
            } else {
                int depthDistance = depth.get(person2Position) - depth.get(person1Position);
                int kDistance = depth.get(person1Position) - lcaDepth;

                if (kDistance == 0) {
                    String relation;
                    if (tree.get(personId1).position == person1Position) {
                        relation = "parent";
                    } else {
                        // Positions were switched
                        relation = "child";
                    }
                    String relationDescription = computeRelationDescription(relation, depthDistance);
                    outputWriter.printLine(relationDescription);
                } else if (kDistance == 1 && depthDistance == 0) {
                    outputWriter.printLine("sibling");
                } else {
                    outputWriter.print((kDistance - 1) + " cousin");
                    if (depthDistance > 0) {
                        outputWriter.print(" removed " + depthDistance);
                    }
                    outputWriter.printLine();
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void addVirtualRoot(List<Node> tree) {
        tree.add(new Node(-1, -1));
        int virtualRootId = tree.size() - 1;

        for (int nodeId = 0; nodeId < tree.size() - 1; nodeId++) {
            Node node = tree.get(nodeId);

            if (node.parent == -1) {
                node.parent = virtualRootId;
                tree.get(virtualRootId).children.add(nodeId);
            }
        }
    }

    private static void buildRMQTable(List<Node> tree, List<Integer> order, List<Integer> depth, int nodeId,
                                      int currentDepth) {
        order.add(nodeId);
        depth.add(currentDepth);
        Node node = tree.get(nodeId);

        if (node.position == -1) {
            node.position = order.size() - 1;
        }

        for (int childId : node.children) {
            buildRMQTable(tree, order, depth, childId, currentDepth + 1);
            order.add(nodeId);
            depth.add(currentDepth);
        }
    }

    private static int getMinDepth(List<Integer> depth, int startIndex, int endIndex) {
        int minDepth = Integer.MAX_VALUE;
        for (int position = startIndex; position <= endIndex; position++) {
            if (depth.get(position) < minDepth) {
                minDepth = depth.get(position);
            }
        }
        return minDepth;
    }

    private static String computeRelationDescription(String relation, int distance) {
        StringBuilder relationDescription = new StringBuilder();

        for (int i = 1; i < distance - 1; i++) {
            relationDescription.append("great ");
        }

        if (distance > 1) {
            relationDescription.append("grand ");
        }
        relationDescription.append(relation);
        return relationDescription.toString();
    }

    private static int getNameId(Map<String, Integer> nameToIdMap, List<Node> tree, String name) {
        if (!nameToIdMap.containsKey(name)) {
            nameToIdMap.put(name, nameToIdMap.size());
            tree.add(new Node(-1, -1));
        }
        return nameToIdMap.get(name);
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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
