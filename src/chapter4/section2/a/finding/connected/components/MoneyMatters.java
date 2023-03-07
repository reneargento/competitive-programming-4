package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/03/23.
 */
@SuppressWarnings("unchecked")
public class MoneyMatters {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] moneyOwed = new int[FastReader.nextInt()];
        int friendshipsNumber = FastReader.nextInt();
        List<Integer>[] friendships = new List[moneyOwed.length];

        for (int i = 0; i < moneyOwed.length; i++) {
            moneyOwed[i] = FastReader.nextInt();
            friendships[i] = new ArrayList<>();
        }

        for (int i = 0; i < friendshipsNumber; i++) {
            int friendID1 = FastReader.nextInt();
            int friendID2 = FastReader.nextInt();
            friendships[friendID1].add(friendID2);
            friendships[friendID2].add(friendID1);
        }

        boolean isPossibleToGetEven = isPossibleToGetEven(moneyOwed, friendships);
        outputWriter.printLine(isPossibleToGetEven ? "POSSIBLE" : "IMPOSSIBLE");
        outputWriter.flush();
    }

    private static boolean isPossibleToGetEven(int[] moneyOwed, List<Integer>[] friendships) {
        boolean[] visited = new boolean[moneyOwed.length];
        for (int friendID = 0; friendID < moneyOwed.length; friendID++) {
            if (!visited[friendID]) {
                int totalMoney = totalMoneyDFS(moneyOwed, friendships, visited, friendID);
                if (totalMoney != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int totalMoneyDFS(int[] moneyOwed, List<Integer>[] friendships, boolean[] visited, int friendID) {
        visited[friendID] = true;
        int totalMoney = moneyOwed[friendID];
        for (int otherFriendID : friendships[friendID]) {
            if (!visited[otherFriendID]) {
                totalMoney += totalMoneyDFS(moneyOwed, friendships, visited, otherFriendID);
            }
        }
        return totalMoney;
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
