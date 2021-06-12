package chapter2.section3.i.order.statistics.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/06/21.
 */
public class GalacticCollegiateProgrammingContest {

    private static class Team implements Comparable<Team> {
        int teamId;
        int problemsSolved;
        int penalty;

        public Team(int teamId) {
            this.teamId = teamId;
        }

        @Override
        public int compareTo(Team other) {
            if (problemsSolved != other.problemsSolved) {
                return Integer.compare(other.problemsSolved, problemsSolved);
            }
            if (penalty != other.penalty) {
                return Integer.compare(penalty, other.penalty);
            }
            return Integer.compare(teamId, other.teamId);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int teamsNumber = FastReader.nextInt();
        int events = FastReader.nextInt();

        Map<Integer, Team> idToTeamMap = new HashMap<>();
        RedBlackBST<Team, Integer> ranking = initRanking(teamsNumber, idToTeamMap);

        for (int i = 0; i < events; i++) {
            int teamId = FastReader.nextInt();
            int penalty = FastReader.nextInt();

            Team team = idToTeamMap.get(teamId);

            ranking.delete(team);
            team.problemsSolved++;
            team.penalty += penalty;
            ranking.put(team, 0);

            Team team1 = idToTeamMap.get(1);
            long team1Ranking = ranking.rank(team1) + 1;
            outputWriter.printLine(team1Ranking);
        }
        outputWriter.flush();
    }

    private static RedBlackBST<Team, Integer> initRanking(int teamsNumber, Map<Integer, Team> idToTeamMap) {
        RedBlackBST<Team, Integer> ranking = new RedBlackBST<>();
        for (int i = 1; i <= teamsNumber; i++) {
            Team team = new Team(i);
            ranking.put(team, 0);
            idToTeamMap.put(i, team);
        }
        return ranking;
    }

    private static class RedBlackBST<Key extends Comparable<Key>, Value> {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private class Node {
            private Key key;
            private Value value;
            private Node left, right;

            boolean color;
            long size;

            Node(Key key, Value value, int size, boolean color) {
                this.key = key;
                this.value = value;

                this.size = size;
                this.color = color;
            }
        }

        private Node root;

        private long size(Node node) {
            if (node == null) {
                return 0;
            }
            return node.size;
        }

        private boolean isEmpty() {
            return size(root) == 0;
        }

        private boolean isRed(Node node) {
            if (node == null) {
                return false;
            }
            return node.color == RED;
        }

        private Node rotateLeft(Node node) {
            if (node == null || node.right == null) {
                return node;
            }
            Node newRoot = node.right;

            node.right = newRoot.left;
            newRoot.left = node;

            newRoot.color = node.color;
            node.color = RED;

            newRoot.size = node.size;
            node.size = size(node.left) + 1 + size(node.right);

            return newRoot;
        }

        private Node rotateRight(Node node) {
            if (node == null || node.left == null) {
                return node;
            }
            Node newRoot = node.left;

            node.left = newRoot.right;
            newRoot.right = node;

            newRoot.color = node.color;
            node.color = RED;

            newRoot.size = node.size;
            node.size = size(node.left) + 1 + size(node.right);

            return newRoot;
        }

        private void flipColors(Node node) {
            if (node == null || node.left == null || node.right == null) {
                return;
            }

            //The root must have opposite color of its two children
            if ((isRed(node) && !isRed(node.left) && !isRed(node.right))
                    || (!isRed(node) && isRed(node.left) && isRed(node.right))) {
                node.color = !node.color;
                node.left.color = !node.left.color;
                node.right.color = !node.right.color;
            }
        }

        private void put(Key key, Value value) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }
            if (value == null) {
                delete(key);
                return;
            }

            root = put(root, key, value);
            root.color = BLACK;
        }

        private Node put(Node node, Key key, Value value) {
            if (node == null) {
                return new Node(key, value, 1, RED);
            }

            int compare = key.compareTo(node.key);

            if (compare < 0) {
                node.left = put(node.left, key, value);
            } else if (compare > 0) {
                node.right = put(node.right, key, value);
            } else {
                node.value = value;
            }

            if (isRed(node.right) && !isRed(node.left)) {
                node = rotateLeft(node);
            }
            if (isRed(node.left) && isRed(node.left.left)) {
                node = rotateRight(node);
            }
            if (isRed(node.left) && isRed(node.right)) {
                flipColors(node);
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
        }

        private Node min(Node node) {
            if (node.left == null) {
                return node;
            }
            return min(node.left);
        }

        private long rank(Key key) {
            return rank(root, key);
        }

        private long rank(Node node, Key key) {
            if (node == null) {
                return 0;
            }

            //Returns the number of keys less than node.key in the subtree rooted at node
            int compare = key.compareTo(node.key);
            if (compare < 0) {
                return rank(node.left, key);
            } else if (compare > 0) {
                return size(node.left) + 1 + rank(node.right, key);
            } else {
                return size(node.left);
            }
        }

        private Node deleteMin(Node node) {
            if (node.left == null) {
                return null;
            }

            if (!isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }

            node.left = deleteMin(node.left);
            return balance(node);
        }

        private void delete(Key key) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }

            if (isEmpty()) {
                return;
            }

            if (!isRed(root.left) && !isRed(root.right)) {
                root.color = RED;
            }

            root = delete(root, key);

            if (!isEmpty()) {
                root.color = BLACK;
            }
        }

        private Node delete(Node node, Key key) {
            if (node == null) {
                return null;
            }

            if (key.compareTo(node.key) < 0) {
                if (!isRed(node.left) && node.left != null && !isRed(node.left.left)) {
                    node = moveRedLeft(node);
                }

                node.left = delete(node.left, key);
            } else {
                if (isRed(node.left)) {
                    node = rotateRight(node);
                }

                if (key.compareTo(node.key) == 0 && node.right == null) {
                    return null;
                }

                if (!isRed(node.right) && node.right != null && !isRed(node.right.left)) {
                    node = moveRedRight(node);
                }

                if (key.compareTo(node.key) == 0) {
                    Node aux = min(node.right);
                    node.key = aux.key;
                    node.value = aux.value;
                    node.right = deleteMin(node.right);
                } else {
                    node.right = delete(node.right, key);
                }
            }
            return balance(node);
        }

        private Node moveRedLeft(Node node) {
            //Assuming that node is red and both node.left and node.left.left are black,
            // make node.left or one of its children red
            flipColors(node);

            if (node.right != null && isRed(node.right.left)) {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
                flipColors(node);
            }
            return node;
        }

        private Node moveRedRight(Node node) {
            //Assuming that node is red and both node.right and node.right.left are black,
            // make node.right or one of its children red
            flipColors(node);

            if (node.left != null && isRed(node.left.left)) {
                node = rotateRight(node);
                flipColors(node);
            }
            return node;
        }

        private Node balance(Node node) {
            if (node == null) {
                return null;
            }

            if (isRed(node.right) && !isRed(node.left)) {
                node = rotateLeft(node);
            }

            if (isRed(node.left) && isRed(node.left.left)) {
                node = rotateRight(node);
            }

            if (isRed(node.left) && isRed(node.right)) {
                flipColors(node);
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
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
