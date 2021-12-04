package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/12/21.
 */
public class Geppetto {

    private static class Restriction {
        int ingredient1;
        int ingredient2;

        public Restriction(int ingredient1, int ingredient2) {
            this.ingredient1 = ingredient1;
            this.ingredient2 = ingredient2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int ingredients = FastReader.nextInt();
        Restriction[] restrictions = new Restriction[FastReader.nextInt()];

        for (int i = 0; i < restrictions.length; i++) {
            restrictions[i] = new Restriction(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
        }

        int pizzas = countPizzas(ingredients, restrictions);
        outputWriter.printLine(pizzas);
        outputWriter.flush();
    }

    private static int countPizzas(int ingredients, Restriction[] restrictions) {
        return countPizzas(ingredients, restrictions, 0, 0);
    }

    private static int countPizzas(int ingredients, Restriction[] restrictions, int mask, int index) {
        if (index == ingredients) {
            boolean[] selectedIngredients = selectIngredients(ingredients, mask);
            return tryToMakePizza(selectedIngredients, restrictions);
        }

        int maskWithIngredient = mask | (1 << index);
        return countPizzas(ingredients, restrictions, maskWithIngredient, index + 1)
                + countPizzas(ingredients, restrictions, mask, index + 1);
    }

    private static boolean[] selectIngredients(int ingredients, int mask) {
        boolean[] selectedIngredients = new boolean[ingredients];

        for (int i = 0; i < ingredients; i++) {
            if ((mask & (1 << i)) > 0) {
                selectedIngredients[i] = true;
            }
        }
        return selectedIngredients;
    }

    private static int tryToMakePizza(boolean[] selectedIngredients, Restriction[] restrictions) {
        for (Restriction restriction : restrictions) {
            if (selectedIngredients[restriction.ingredient1]
                    && selectedIngredients[restriction.ingredient2]) {
                return 0;
            }
        }
        return 1;
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
