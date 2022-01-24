package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/01/22.
 */
public class RomanRoulette {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int people = FastReader.nextInt();
        int skip = FastReader.nextInt();

        while (people != 0 || skip != 0) {
            int startPosition = computeStartPositionToSurvive(people, skip);
            outputWriter.printLine(startPosition);

            people = FastReader.nextInt();
            skip = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeStartPositionToSurvive(int people, int skip) {
        for (int i = 0; i < people; i++) {
            if (canSurvive(people, skip, i)) {
                return i + 1;
            }
        }
        return -1;
    }

    private static boolean canSurvive(int people, int skip, int startPosition) {
        LinkedList<Integer> peopleList = new LinkedList<>();
        for (int i = 0; i < people; i++) {
            peopleList.add(i);
        }
        int currentPosition = startPosition;

        for (int i = 0; i < people - 1; i++) {
            int killed = (currentPosition + skip - 1) % peopleList.size();
            peopleList.remove(killed);

            int replaced = (killed + skip - 1) % (peopleList.size());
            int personToReplace = peopleList.get(replaced);
            peopleList.remove(replaced);

            if (replaced < killed) {
                peopleList.add(killed - 1, personToReplace);
                currentPosition = killed % peopleList.size();
            } else if (killed < peopleList.size()) {
                peopleList.add(killed, personToReplace);
                currentPosition = (killed + 1) % peopleList.size();
            } else {
                peopleList.add(personToReplace);
                currentPosition = 0;
            }
        }
        return peopleList.get(0) == 0;
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
