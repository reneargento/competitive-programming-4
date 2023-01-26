package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/01/23.
 */
public class Batman {

    private static class Superpower {
        String name;
        int attackFactor;
        int energyConsumed;

        public Superpower(String name, int attackFactor, int energyConsumed) {
            this.name = name;
            this.attackFactor = attackFactor;
            this.energyConsumed = energyConsumed;
        }
    }

    private static class Villain {
        String alias;
        int defenseFactor;
        Set<String> weaknesses;

        public Villain(String alias, int defenseFactor, String weaknessesList) {
            this.alias = alias;
            this.defenseFactor = defenseFactor;
            weaknesses = new HashSet<>();

            String[] weaknessesArray = weaknessesList.split(",");
            Collections.addAll(weaknesses, weaknessesArray);
        }
    }

    private static final int INFINITE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int superpowerNumber = FastReader.nextInt();
        int villainNumber = FastReader.nextInt();
        int calories = FastReader.nextInt();

        while (superpowerNumber != 0 || villainNumber != 0 || calories != 0) {
            Superpower[] superpowers = new Superpower[superpowerNumber];
            Villain[] villains = new Villain[villainNumber];
            for (int i = 0; i < superpowers.length; i++) {
                superpowers[i] = new Superpower(FastReader.next(), FastReader.nextInt(), FastReader.nextInt());
            }
            for (int i = 0; i < villains.length; i++) {
                villains[i] = new Villain(FastReader.next(), FastReader.nextInt(), FastReader.next());
            }
            String canBeatVillains = canBeatVillains(superpowers, villains, calories);
            outputWriter.printLine(canBeatVillains);

            superpowerNumber = FastReader.nextInt();
            villainNumber = FastReader.nextInt();
            calories = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String canBeatVillains(Superpower[] superpowers, Villain[] villains, int calories) {
        // dp[superpowerID][villainID] = minimum energy spent
        int[][] dp = new int[superpowers.length + 1][villains.length + 1];
        for (int superpowerID = 0; superpowerID < dp.length; superpowerID++) {
            dp[superpowerID][0] = 0;
        }
        for (int villainID = 1; villainID < dp[0].length; villainID++) {
            dp[0][villainID] = INFINITE;
        }

        for (int superpowerID = 1; superpowerID < dp.length; superpowerID++) {
            for (int villainID = 1; villainID < dp[0].length; villainID++) {
                Superpower superpower = superpowers[superpowerID - 1];
                Villain villain = villains[villainID - 1];

                dp[superpowerID][villainID] = dp[superpowerID - 1][villainID];
                if (superpower.attackFactor >= villain.defenseFactor
                        && villain.weaknesses.contains(superpower.name)) {
                    int energyUsingSuperpower = dp[superpowerID - 1][villainID - 1] + superpower.energyConsumed;
                    dp[superpowerID][villainID] = Math.min(dp[superpowerID][villainID], energyUsingSuperpower);
                }
            }
        }
        return (dp[superpowers.length][villains.length] <= calories) ? "Yes" : "No";
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
