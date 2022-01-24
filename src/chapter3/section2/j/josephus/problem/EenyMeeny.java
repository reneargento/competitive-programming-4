package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/01/22.
 */
public class EenyMeeny {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String[] rhyme = FastReader.getLine().split(" ");
        int skip = rhyme.length - 1;
        int kids = FastReader.nextInt();
        List<String> names = new ArrayList<>();

        for (int i = 0; i < kids; i++) {
            names.add(FastReader.getLine());
        }

        List<String> team1 = new ArrayList<>();
        List<String> team2 = new ArrayList<>();
        selectTeams(names, skip, team1, team2);

        printTeamNames(team1, outputWriter);
        printTeamNames(team2, outputWriter);
        outputWriter.flush();
    }

    private static void selectTeams(List<String> names, int skip, List<String> team1, List<String> team2) {
        int kids = names.size();
        boolean isTeam1 = true;
        int kidIndex = 0;

        for (int i = 0; i < kids; i++) {
            kidIndex = (kidIndex + skip) % names.size();

            if (isTeam1) {
                team1.add(names.get(kidIndex));
            } else {
                team2.add(names.get(kidIndex));
            }
            isTeam1 = !isTeam1;
            names.remove(kidIndex);

            if (names.isEmpty()) {
                break;
            }
            kidIndex %= names.size();
        }
    }

    private static void printTeamNames(List<String> team, OutputWriter outputWriter) {
        outputWriter.printLine(team.size());
        for (String name : team) {
            outputWriter.printLine(name);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
