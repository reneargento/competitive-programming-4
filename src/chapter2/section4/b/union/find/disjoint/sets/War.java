package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/07/21.
 */
@SuppressWarnings("unchecked")
public class War {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int people = FastReader.nextInt();
        UnionFind unionFind = new UnionFind(people);
        Set<Integer>[] enemySets = createEnemySets(people);

        int operation = FastReader.nextInt();
        int person1 = FastReader.nextInt();
        int person2 = FastReader.nextInt();

        while (operation != 0 || person1 != 0 || person2 != 0) {
            int person1Leader = unionFind.find(person1);
            int person2Leader = unionFind.find(person2);

            if (operation == 1 || operation == 2) {
                if (operation == 1) {
                    if (enemySets[person1Leader].contains(person2Leader)
                            || enemySets[person2Leader].contains(person1Leader) ) {
                        outputWriter.printLine("-1");
                    } else {
                        unionFind.union(person1Leader, person2Leader, enemySets);
                    }
                } else {
                    if (unionFind.connected(person1Leader, person2Leader)) {
                        outputWriter.printLine("-1");
                    } else if (!enemySets[person1Leader].contains(person2Leader) &&
                            !enemySets[person2Leader].contains(person1Leader)) {
                        addEnemy(person1Leader, person2Leader, unionFind, enemySets);
                    }
                }
            } else if (operation == 3) {
                outputWriter.printLine(unionFind.connected(person1Leader, person2Leader) ? "1" : "0");
            } else {
                if (enemySets[person1Leader].contains(person2Leader)
                        || enemySets[person2Leader].contains(person1Leader) ) {
                    outputWriter.printLine("1");
                } else {
                    outputWriter.printLine("0");
                }
            }

            operation = FastReader.nextInt();
            person1 = FastReader.nextInt();
            person2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void addEnemy(int person1Leader, int person2Leader, UnionFind unionFind,
                                 Set<Integer>[] enemySets) {
        int firstEnemy = getFirstEnemy(enemySets, person1Leader);
        int secondEnemy = getFirstEnemy(enemySets, person2Leader);
        if (firstEnemy != -1) {
            unionFind.union(firstEnemy, person2Leader, enemySets);
        } else if (secondEnemy != -1) {
            unionFind.union(person1Leader, secondEnemy, enemySets);
        }

        enemySets[person1Leader].add(person2Leader);
        enemySets[person2Leader].add(person1Leader);
    }

    private static int getFirstEnemy(Set<Integer>[] enemySets, int person) {
        if (!enemySets[person].isEmpty()) {
            for (int enemy : enemySets[person]) {
                return enemy;
            }
        }
        return -1;
    }

    private static void addEnemyIfValid(int enemy, int leader, Set<Integer>[] enemySets,
                                        UnionFind unionFind) {
        if (enemy != -1) {
            int enemyLeader = unionFind.find(enemy);
            enemySets[enemyLeader].add(leader);
        }
    }

    private static Set<Integer>[] createEnemySets(int people) {
        Set<Integer>[] enemyList = new Set[people];
        for (int i = 0; i < people; i++) {
            enemyList[i] = new HashSet<>();
        }
        return enemyList;
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
        public void union(int site1, int site2, Set<Integer>[] enemySets) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            int firstEnemy = getFirstEnemy(enemySets, leader1);
            int secondEnemy = getFirstEnemy(enemySets, leader2);

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
                enemySets[leader2].addAll(enemySets[leader1]);
                addEnemyIfValid(secondEnemy, leader1, enemySets, this);
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                sizes[leader1] += sizes[leader2];
                enemySets[leader1].addAll(enemySets[leader2]);
                addEnemyIfValid(firstEnemy, leader2, enemySets, this);
            } else {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
                ranks[leader2]++;
                enemySets[leader2].addAll(enemySets[leader1]);
                addEnemyIfValid(secondEnemy, leader1, enemySets, this);
            }
            components--;

            if (firstEnemy != -1 && secondEnemy != -1 && !connected(firstEnemy, secondEnemy)) {
                union(firstEnemy, secondEnemy, enemySets);
            }
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
