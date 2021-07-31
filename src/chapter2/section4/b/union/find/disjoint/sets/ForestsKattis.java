package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/07/21.
 */
@SuppressWarnings("unchecked")
public class ForestsKattis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int people = FastReader.nextInt();
        int trees = FastReader.nextInt();
        String line = FastReader.getLine();

        List<Integer>[] treesHeardPerPerson = new List[people];
        for (int i = 0; i < treesHeardPerPerson.length; i++) {
            treesHeardPerPerson[i] = new ArrayList<>();
        }

        while (line != null) {
            String[] data = line.split(" ");
            int person = Integer.parseInt(data[0]) - 1;
            int tree = Integer.parseInt(data[1]);
            treesHeardPerPerson[person].add(tree);

            line = FastReader.getLine();
        }

        int opinions = countOpinions(treesHeardPerPerson);
        outputWriter.printLine(opinions);
        outputWriter.flush();
    }

    private static int countOpinions(List<Integer>[] treesHeardPerPerson) {
        for (List<Integer> opinion : treesHeardPerPerson) {
            Collections.sort(opinion);
        }

        Set<Integer> peopleChecked = new HashSet<>();
        int opinions = 0;

        for (int person1 = 0; person1 < treesHeardPerPerson.length; person1++) {
            if (peopleChecked.contains(person1)) {
                continue;
            }
            peopleChecked.add(person1);
            opinions++;

            for (int person2 = person1 + 1; person2 < treesHeardPerPerson.length; person2++) {
                if (peopleChecked.contains(person2)) {
                    continue;
                }

                if (areOpinionsEqual(treesHeardPerPerson[person1], treesHeardPerPerson[person2])) {
                    peopleChecked.add(person2);
                }
            }
        }
        return opinions;
    }

    private static boolean areOpinionsEqual(List<Integer> opinion1, List<Integer> opinion2) {
        if (opinion1.size() != opinion2.size()) {
            return false;
        }

        for (int i = 0; i < opinion1.size(); i++) {
            if (!opinion1.get(i).equals(opinion2.get(i))) {
                return false;
            }
        }
        return true;
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
