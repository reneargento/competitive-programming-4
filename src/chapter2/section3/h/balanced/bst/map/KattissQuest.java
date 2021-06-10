package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Created by Rene Argento on 09/06/21.
 */
public class KattissQuest {

    private static class Quest implements Comparable<Quest> {
        int questId;
        int energyRequired;
        int goldReward;

        public Quest(int questId, int energyRequired, int goldReward) {
            this.questId = questId;
            this.energyRequired = energyRequired;
            this.goldReward = goldReward;
        }

        @Override
        public int compareTo(Quest other) {
            if (energyRequired != other.energyRequired) {
                return Integer.compare(other.energyRequired, energyRequired);
            }
            if (goldReward != other.goldReward) {
                return Integer.compare(other.goldReward, goldReward);
            }
            return Integer.compare(questId, other.questId);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int commands = FastReader.nextInt();
        TreeSet<Quest> questsPool = new TreeSet<>();

        for (int i = 0; i < commands; i++) {
            String command = FastReader.next();
            if (command.equals("add")) {
                Quest quest = new Quest(i, FastReader.nextInt(), FastReader.nextInt());
                questsPool.add(quest);
            } else {
                int energyAvailable = FastReader.nextInt();
                long goldEarned = 0;
                Quest queryQuest = new Quest(Integer.MAX_VALUE, energyAvailable, Integer.MAX_VALUE);

                while (!questsPool.isEmpty()) {
                    Quest nextQuest = questsPool.ceiling(queryQuest);
                    if (nextQuest == null) {
                        break;
                    }
                    goldEarned += nextQuest.goldReward;
                    energyAvailable -= nextQuest.energyRequired;
                    queryQuest.energyRequired = energyAvailable;
                    questsPool.remove(nextQuest);
                }
                outputWriter.printLine(goldEarned);
            }
        }
        outputWriter.flush();
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
