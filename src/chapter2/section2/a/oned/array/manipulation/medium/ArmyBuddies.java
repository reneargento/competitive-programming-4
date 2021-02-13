package chapter2.section2.a.oned.array.manipulation.medium;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class ArmyBuddies {

    private static class Soldier {
        int id;
        Soldier left;
        Soldier right;

        public Soldier(int id) {
            this.id = id;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int soldiersNumber = FastReader.nextInt();
        int reports = FastReader.nextInt();

        while (soldiersNumber != 0 || reports != 0) {
            Soldier[] soldiers = createSoldiersArray(soldiersNumber);

            for (int r = 0; r < reports; r++) {
                int leftSoldierKilledId = FastReader.nextInt();
                int rightSoldierKilledId = FastReader.nextInt();

                Soldier leftSoldierKilled = soldiers[leftSoldierKilledId];
                Soldier rightSoldierKilled = soldiers[rightSoldierKilledId];

                Soldier leftSoldier = leftSoldierKilled.left;
                Soldier rightSoldier = rightSoldierKilled.right;

                if (leftSoldier == null) {
                    outputWriter.print("* ");
                } else {
                    outputWriter.print(leftSoldier.id + " ");
                    leftSoldier.right = rightSoldier;
                }

                if (rightSoldier == null) {
                    outputWriter.printLine("*");
                } else {
                    outputWriter.printLine(rightSoldier.id);
                    rightSoldier.left = leftSoldier;
                }
            }
            outputWriter.printLine("-");

            soldiersNumber = FastReader.nextInt();
            reports = FastReader.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static Soldier[] createSoldiersArray(int soldiersNumber) {
        Soldier[] soldiers = new Soldier[soldiersNumber + 2];
        for (int i = 1; i <= soldiersNumber; i++) {
            soldiers[i] = new Soldier(i);
        }

        for (int i = 1; i <= soldiersNumber; i++) {
            Soldier soldier = soldiers[i];
            soldier.left = soldiers[i - 1];
            soldier.right = soldiers[i + 1];
        }
        return soldiers;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
