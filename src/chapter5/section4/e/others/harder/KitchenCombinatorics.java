package chapter5.section4.e.others.harder;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 13/01/26.
 */
public class KitchenCombinatorics {

    private static class Dish {
        List<Integer> ingredients;

        public Dish(List<Integer> ingredients) {
            this.ingredients = ingredients;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] brands = new int[FastReader.nextInt()];
        int starterDishes = FastReader.nextInt();
        int mainDishes = FastReader.nextInt();
        int desserts = FastReader.nextInt();
        int totalDishes = starterDishes + mainDishes + desserts;
        Dish[] dishes = new Dish[totalDishes + 1];
        int numberOfIncompatibles = FastReader.nextInt();
        Set<Integer>[] incompatibles = new HashSet[totalDishes + 1];

        for (int i = 0; i < incompatibles.length; i++) {
            incompatibles[i] = new HashSet<>();
        }

        for (int b = 0; b < brands.length; b++) {
            brands[b] = FastReader.nextInt();
        }
        for (int d = 1; d < dishes.length; d++) {
            List<Integer> ingredients = new ArrayList<>();
            int numberOfIngredients = FastReader.nextInt();
            for (int i = 0; i < numberOfIngredients; i++) {
                ingredients.add(FastReader.nextInt() - 1);
            }
            dishes[d] = new Dish(ingredients);
        }

        for (int i = 0; i < numberOfIncompatibles; i++) {
            int dish1 = FastReader.nextInt();
            int dish2 = FastReader.nextInt();
            incompatibles[dish1].add(dish2);
            incompatibles[dish2].add(dish1);
        }

        long differentExperiences = computeDifferentExperiences(brands, starterDishes, mainDishes, dishes,
                incompatibles);
        if (differentExperiences == -1) {
            outputWriter.printLine("too many");
        } else {
            outputWriter.printLine(differentExperiences);
        }
        outputWriter.flush();
    }

    private static long computeDifferentExperiences(int[] brands, int starterDishes, int mainDishes, Dish[] dishes,
                                                    Set<Integer>[] incompatibles) {
        BigInteger differentExperiences = BigInteger.ZERO;
        BigInteger maxValue = new BigInteger("1000000000000000000");

        for (int i = 1; i <= starterDishes; i++) {
            BigInteger starterOptions = BigInteger.ONE;
            Set<Integer> ingredientsStarterUsed = new HashSet<>();
            for (int ingredient : dishes[i].ingredients) {
                ingredientsStarterUsed.add(ingredient);
                starterOptions = starterOptions.multiply(BigInteger.valueOf(brands[ingredient]));
            }

            for (int j = starterDishes + 1; j <= starterDishes + mainDishes; j++) {
                if (incompatibles[i].contains(j)) {
                    continue;
                }
                BigInteger mainOptions = BigInteger.ONE;
                Set<Integer> ingredientsMainUsed = new HashSet<>();

                for (int ingredient : dishes[j].ingredients) {
                    if (!ingredientsStarterUsed.contains(ingredient)) {
                        ingredientsMainUsed.add(ingredient);
                        mainOptions = mainOptions.multiply(BigInteger.valueOf(brands[ingredient]));
                    }
                }

                for (int k = starterDishes + mainDishes + 1; k < dishes.length; k++) {
                    if (incompatibles[i].contains(j)
                            || incompatibles[i].contains(k)
                            || incompatibles[j].contains(k)) {
                        continue;
                    }

                    BigInteger dessertOptions = BigInteger.ONE;
                    for (int ingredient : dishes[k].ingredients) {
                        if (!ingredientsStarterUsed.contains(ingredient) && !ingredientsMainUsed.contains(ingredient)) {
                            dessertOptions = dessertOptions.multiply(BigInteger.valueOf(brands[ingredient]));
                        }
                    }
                    BigInteger options = starterOptions.multiply(mainOptions).multiply(dessertOptions);
                    differentExperiences = differentExperiences.add(options);
                    if (differentExperiences.compareTo(maxValue) > 0) {
                        return -1;
                    }
                }
            }
        }
        return differentExperiences.longValue();
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
