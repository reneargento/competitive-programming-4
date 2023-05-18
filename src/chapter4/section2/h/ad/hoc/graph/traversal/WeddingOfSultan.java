package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/05/23.
 */
public class WeddingOfSultan {

    private static class Sprinkler implements Comparable<Sprinkler> {
        char name;
        int trails;

        public Sprinkler(char name, int trails) {
            this.name = name;
            this.trails = trails;
        }

        @Override
        public int compareTo(Sprinkler other) {
            return Character.compare(name, other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String sprinklerNames = FastReader.next();
            List<Sprinkler> sprinklers = computeTrails(sprinklerNames);

            outputWriter.printLine(String.format("Case %d", t));
            for (Sprinkler sprinkler : sprinklers) {
                outputWriter.printLine(String.format("%c = %d", sprinkler.name, sprinkler.trails));
            }
        }
        outputWriter.flush();
    }

    private static List<Sprinkler> computeTrails(String sprinklerNames) {
        List<Sprinkler> sprinklers = new ArrayList<>();
        Map<Character, Integer> sprinklerNameToTrails = new HashMap<>();
        Deque<Character> stack = new ArrayDeque<>();
        char entrance = sprinklerNames.charAt(0);

        for (char sprinklerName : sprinklerNames.toCharArray()) {
            if (stack.isEmpty()) {
                stack.push(sprinklerName);
            } else {
                char currentSprinklerName = stack.peek();
                if (sprinklerName != entrance) {
                    int trails = sprinklerNameToTrails.getOrDefault(currentSprinklerName, 0) + 1;
                    sprinklerNameToTrails.put(currentSprinklerName, trails);
                }

                if (currentSprinklerName == sprinklerName) {
                    stack.pop();
                } else {
                    stack.push(sprinklerName);
                }
            }
        }

        for (Map.Entry<Character, Integer> entry : sprinklerNameToTrails.entrySet()) {
            sprinklers.add(new Sprinkler(entry.getKey(), entry.getValue()));
        }
        Collections.sort(sprinklers);
        return sprinklers;
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
