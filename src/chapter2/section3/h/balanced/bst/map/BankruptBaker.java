package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/06/21.
 */
public class BankruptBaker {

    private static class Recipe implements Comparable<Recipe> {
        String name;
        long cost;

        public Recipe(String name, long cost) {
            this.name = name;
            this.cost = cost;
        }

        @Override
        public int compareTo(Recipe other) {
            if (cost != other.cost) {
                return Long.compare(cost, other.cost);
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int binders = FastReader.nextInt();

        for (int b = 0; b < binders; b++) {
            String binder = FastReader.getLine();
            int ingredientsNumber = FastReader.nextInt();
            int recipesNumber = FastReader.nextInt();
            int budget = FastReader.nextInt();

            Map<String, Integer> ingredientToCostMap = new HashMap<>();
            for (int i = 0; i < ingredientsNumber; i++) {
                ingredientToCostMap.put(FastReader.next(), FastReader.nextInt());
            }

            List<Recipe> recipesWithinBudget = new ArrayList<>();
            for (int i = 0; i < recipesNumber; i++) {
                String recipeName = FastReader.getLine();
                long cost = 0;

                int requirements = FastReader.nextInt();
                for (int r = 0; r < requirements; r++) {
                    String ingredient = FastReader.next();
                    int quantity = FastReader.nextInt();
                    cost += ingredientToCostMap.get(ingredient) * quantity;
                }
                if (cost <= budget) {
                    Recipe recipe = new Recipe(recipeName, cost);
                    recipesWithinBudget.add(recipe);
                }
            }

            outputWriter.printLine(binder.toUpperCase());
            if (recipesWithinBudget.isEmpty()) {
                outputWriter.printLine("Too expensive!");
            } else {
                Collections.sort(recipesWithinBudget);
                for (Recipe recipe : recipesWithinBudget) {
                    outputWriter.printLine(recipe.name);
                }
            }
            outputWriter.printLine();
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
