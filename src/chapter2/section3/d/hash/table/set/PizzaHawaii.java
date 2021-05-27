package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/05/21.
 */
public class PizzaHawaii {

    private static class IngredientPair implements Comparable<IngredientPair> {
        String foreignIngredient;
        String nativeIngredient;

        public IngredientPair(String foreignIngredient, String nativeIngredient) {
            this.foreignIngredient = foreignIngredient;
            this.nativeIngredient = nativeIngredient;
        }

        @Override
        public int compareTo(IngredientPair other) {
            if (!foreignIngredient.equals(other.foreignIngredient)) {
                return foreignIngredient.compareTo(other.foreignIngredient);
            }
            return nativeIngredient.compareTo(other.nativeIngredient);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            int pizzas = FastReader.nextInt();
            Map<String, Set<String>> ingredientsMap = new HashMap<>();
            Set<String> allNativeIngredients = new HashSet<>();

            for (int p = 0; p < pizzas; p++) {
                String name = FastReader.next();
                int foreignIngredientsNumber = FastReader.nextInt();
                List<String> foreignIngredients = readIngredients(foreignIngredientsNumber);
                int nativeIngredientsNumber = FastReader.nextInt();
                List<String> nativeIngredients = readIngredients(nativeIngredientsNumber);

                mapIngredients(ingredientsMap, foreignIngredients, nativeIngredients, allNativeIngredients);
                allNativeIngredients.addAll(nativeIngredients);
            }
            printPossibleTranslations(ingredientsMap, outputWriter);
        }
        outputWriter.flush();
    }

    private static List<String> readIngredients(int ingredientsNumber) throws IOException {
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsNumber; i++) {
            ingredients.add(FastReader.next());
        }
        return ingredients;
    }

    private static void mapIngredients(Map<String, Set<String>> ingredientsMap, List<String> foreignIngredients,
                                       List<String> nativeIngredients, Set<String> allNativeIngredients) {
        for (String foreignIngredient : foreignIngredients) {
            if (ingredientsMap.containsKey(foreignIngredient)) {
                removeNonPresentNativeIngredients(ingredientsMap, foreignIngredient, nativeIngredients);
                removeNativeIngredientsFromNonPresentForeignIngredients(ingredientsMap, foreignIngredients, nativeIngredients);
            } else {
                Set<String> ingredientsToAdd = getIngredientsToAdd(nativeIngredients, allNativeIngredients);
                ingredientsMap.put(foreignIngredient, ingredientsToAdd);
            }
        }
    }

    private static void removeNonPresentNativeIngredients(Map<String, Set<String>> ingredientsMap,
                                                          String foreignIngredient, List<String> nativeIngredients) {
        Set<String> mappedIngredients = ingredientsMap.get(foreignIngredient);

        Set<String> ingredientsToRemove = new HashSet<>();
        for (String mappedIngredient : mappedIngredients) {
            if (!nativeIngredients.contains(mappedIngredient)) {
                ingredientsToRemove.add(mappedIngredient);
            }
        }
        mappedIngredients.removeAll(ingredientsToRemove);
    }

    private static void removeNativeIngredientsFromNonPresentForeignIngredients(Map<String, Set<String>> ingredientsMap,
                                                                                List<String> foreignIngredients,
                                                                                List<String> nativeIngredients) {
        for (String foreignIngredient : ingredientsMap.keySet()) {
            if (foreignIngredients.contains(foreignIngredient)) {
                continue;
            }
            Set<String> ingredientsToRemove = new HashSet<>();
            Set<String> nativeIngredientsSet = ingredientsMap.get(foreignIngredient);

            for (String nativeIngredient : nativeIngredients) {
                if (nativeIngredientsSet.contains(nativeIngredient)) {
                    ingredientsToRemove.add(nativeIngredient);
                }
            }
            nativeIngredientsSet.removeAll(ingredientsToRemove);
        }
    }

    private static Set<String> getIngredientsToAdd(List<String> nativeIngredients, Set<String> allNativeIngredients) {
        Set<String> ingredientsToAdd = new HashSet<>();

        for (String nativeIngredient : nativeIngredients) {
            if (!allNativeIngredients.contains(nativeIngredient)) {
                ingredientsToAdd.add(nativeIngredient);
            }
        }
        return ingredientsToAdd;
    }

    private static void printPossibleTranslations(Map<String, Set<String>> ingredientsMap, OutputWriter outputWriter) {
        List<IngredientPair> possibleTranslations = new ArrayList<>();

        for (String foreignIngredient : ingredientsMap.keySet()) {
            for (String nativeIngredient : ingredientsMap.get(foreignIngredient)) {
                possibleTranslations.add(new IngredientPair(foreignIngredient, nativeIngredient));
            }
        }

        Collections.sort(possibleTranslations);
        for (IngredientPair translation : possibleTranslations) {
            outputWriter.printLine(String.format("(%s, %s)", translation.foreignIngredient,
                    translation.nativeIngredient));
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
