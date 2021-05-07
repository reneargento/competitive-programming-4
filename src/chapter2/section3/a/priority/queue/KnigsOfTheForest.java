package chapter2.section3.a.priority.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/05/21.
 */
public class KnigsOfTheForest {

    private static class Moose implements Comparable<Moose> {
        int year;
        int strength;
        boolean isKarl;

        public Moose(int year, int strength, boolean isKarl) {
            this.year = year;
            this.strength = strength;
            this.isKarl = isKarl;
        }

        @Override
        public int compareTo(Moose other) {
            return Integer.compare(other.strength, strength);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tournamentPool = FastReader.nextInt();
        int years = FastReader.nextInt();
        int mooseNumber = years + tournamentPool - 1;
        Moose[] moose = new Moose[mooseNumber];

        for (int i = 0; i < mooseNumber; i++) {
            int year = FastReader.nextInt();
            int strength = FastReader.nextInt();
            boolean isKarl = i == 0;
            moose[i] = new Moose(year, strength, isKarl);
        }

        int year = computeYearKarlWins(moose, tournamentPool, years);
        if (year == -1) {
            System.out.println("unknown");
        } else {
            System.out.println(year);
        }
    }

    private static int computeYearKarlWins(Moose[] moose, int tournamentPool, int years) {
        Arrays.sort(moose, new Comparator<Moose>() {
            @Override
            public int compare(Moose moose1, Moose moose2) {
                return Integer.compare(moose1.year, moose2.year);
            }
        });
        PriorityQueue<Moose> tournament = new PriorityQueue<>();
        int currentYear = 2011;

        for (int i = 0; i < tournamentPool; i++) {
            tournament.offer(moose[i]);
        }

        for (int y = 0; y < years; y++) {
            Moose winner = tournament.poll();
            if (winner.isKarl) {
                return currentYear + y;
            }

            if (y < years - 1) {
                tournament.offer(moose[tournamentPool + y]);
            }
        }
        return -1;
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
}
