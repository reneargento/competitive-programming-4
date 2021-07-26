package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/07/21.
 */
public class AssociationForControlOverMinds {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int recipes = FastReader.nextInt();
        UnionFind unionFind = new UnionFind(500001);
        int recipesConcocted = 0;

        for (int r = 0; r < recipes; r++) {
            int[] ingredients = new int[FastReader.nextInt()];
            Map<Integer, Integer> groupToMissingIngredient = new HashMap<>();

            for (int i = 0; i < ingredients.length; i++) {
                ingredients[i] = FastReader.nextInt();
            }

            for (int i = 0; i < ingredients.length; i++) {
                int leadIngredient = unionFind.find(ingredients[i]);

                if (groupToMissingIngredient.containsKey(leadIngredient)) {
                    int remaining = groupToMissingIngredient.get(leadIngredient) - 1;
                    if (remaining == 0) {
                        groupToMissingIngredient.remove(leadIngredient);
                    } else {
                        groupToMissingIngredient.put(leadIngredient, remaining);
                    }
                } else {
                    int requiredIngredients = unionFind.size(leadIngredient);
                    if (requiredIngredients > 1) {
                        groupToMissingIngredient.put(leadIngredient, requiredIngredients - 1);
                    }
                }
            }

            if (groupToMissingIngredient.isEmpty()) {
                recipesConcocted++;

                for (int i = 1; i < ingredients.length; i++) {
                    unionFind.union(ingredients[i - 1], ingredients[i]);
                }
            }
        }

        outputWriter.printLine(recipesConcocted);
        outputWriter.flush();
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private final int[] sizes;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            sizes = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
                sizes[i] = 1;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        // O(inverse Ackermann function)
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        // O(inverse Ackermann function)
        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                sizes[leader1] += sizes[leader2];
            } else {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
                ranks[leader2]++;
            }
            components--;
        }

        public int size(int set) {
            int leader = find(set);
            return sizes[leader];
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
