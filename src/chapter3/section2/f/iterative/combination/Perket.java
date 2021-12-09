package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/12/21.
 */
public class Perket {

    private static class Ingredient {
        int sourness;
        int bitterness;

        Ingredient(int sourness, int bitterness) {
            this.sourness = sourness;
            this.bitterness = bitterness;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        Ingredient[] ingredients = new Ingredient[FastReader.nextInt()];
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = new Ingredient(FastReader.nextInt(), FastReader.nextInt());
        }

        int smallestDifference = computeSmallestDifference(ingredients, 0, 0);
        outputWriter.printLine(smallestDifference);
        outputWriter.flush();
    }

    private static int computeSmallestDifference(Ingredient[] ingredients, int mask, int index) {
        if (index == ingredients.length) {
            if (mask == 0) {
                return Integer.MAX_VALUE;
            }
            List<Ingredient> selectedIngredients = selectIngredients(ingredients, mask);
            return computeDifference(selectedIngredients);
        }

        int maskWithIngredient = mask | (1 << index);
        int difference1 = computeSmallestDifference(ingredients, mask, index + 1);
        int difference2 = computeSmallestDifference(ingredients, maskWithIngredient, index + 1);
        return Math.min(difference1, difference2);
    }

    private static List<Ingredient> selectIngredients(Ingredient[] ingredients, int mask) {
        List<Ingredient> selectedIngredients = new ArrayList<>();

        for (int i = 0; i < ingredients.length; i++) {
            if ((mask & (1 << i)) != 0) {
                selectedIngredients.add(ingredients[i]);
            }
        }
        return selectedIngredients;
    }

    private static int computeDifference(List<Ingredient> ingredients) {
        int totalSourness = 1;
        int totalBitterness = 0;

        for (Ingredient ingredient : ingredients) {
            totalSourness *= ingredient.sourness;
            totalBitterness += ingredient.bitterness;
        }
        return Math.abs(totalSourness - totalBitterness);
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