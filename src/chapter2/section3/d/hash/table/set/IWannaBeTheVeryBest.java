package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/05/21.
 */
public class IWannaBeTheVeryBest {

    private static class Pokenom {
        long attack;
        long defense;
        long health;

        public Pokenom(long attack, long defense, long health) {
            this.attack = attack;
            this.defense = defense;
            this.health = health;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Pokenom pokenom = (Pokenom) other;
            return attack == pokenom.attack && defense == pokenom.defense && health == pokenom.health;
        }

        @Override
        public int hashCode() {
            return Objects.hash(attack, defense, health);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int pokenomsNumber = FastReader.nextInt();
        int numberToChoose = FastReader.nextInt();
        Pokenom[] pokenoms = new Pokenom[pokenomsNumber];

        for (int i = 0; i < pokenoms.length; i++) {
            pokenoms[i] = new Pokenom(FastReader.nextLong(), FastReader.nextLong(), FastReader.nextLong());
        }

        int chosen = choosePokenoms(pokenoms, numberToChoose);
        outputWriter.printLine(chosen);
        outputWriter.flush();
    }

    private static int choosePokenoms(Pokenom[] pokenoms, int numberToChoose) {
        Set<Pokenom> chosenPokenoms = new HashSet<>();

        sortAndChoosePokenoms(pokenoms, numberToChoose, new Comparator<Pokenom>() {
            @Override
            public int compare(Pokenom pokenom1, Pokenom pokenom2) {
                return Long.compare(pokenom2.attack, pokenom1.attack);
            }
        }, chosenPokenoms);

        sortAndChoosePokenoms(pokenoms, numberToChoose, new Comparator<Pokenom>() {
            @Override
            public int compare(Pokenom pokenom1, Pokenom pokenom2) {
                return Long.compare(pokenom2.defense, pokenom1.defense);
            }
        }, chosenPokenoms);

        sortAndChoosePokenoms(pokenoms, numberToChoose, new Comparator<Pokenom>() {
            @Override
            public int compare(Pokenom pokenom1, Pokenom pokenom2) {
                return Long.compare(pokenom2.health, pokenom1.health);
            }
        }, chosenPokenoms);

        return chosenPokenoms.size();
    }

    private static void sortAndChoosePokenoms(Pokenom[] pokenoms, int numberToChoose, Comparator<Pokenom> comparator,
                                              Set<Pokenom> chosenPokenoms) {
        Arrays.sort(pokenoms, comparator);
        for (int i = 0; i < numberToChoose; i++) {
            chosenPokenoms.add(pokenoms[i]);
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
