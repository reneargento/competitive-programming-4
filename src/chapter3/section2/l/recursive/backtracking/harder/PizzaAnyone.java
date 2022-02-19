package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 19/02/22.
 */
public class PizzaAnyone {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String constraints = FastReader.getLine();

        while (constraints != null) {
            List<int[]> constraintList = new ArrayList<>();
            while (!constraints.equals(".")) {
                int[] toppings = new int[16];
                for (int i = 1; i < constraints.length(); i += 2) {
                    int toppingIndex = constraints.charAt(i) - 'A';
                    if (constraints.charAt(i - 1) == '+') {
                        toppings[toppingIndex] = 1;
                    } else {
                        toppings[toppingIndex] = -1;
                    }
                }
                constraintList.add(toppings);
                constraints = FastReader.getLine();
            }

            int[] toppings = new int[16];
            String pizza = orderPizza(constraintList, 0, toppings);
            if (pizza != null) {
                outputWriter.print("Toppings:");
                if (!pizza.isEmpty()) {
                    outputWriter.print(" ");
                }
                outputWriter.printLine(pizza);
            } else {
                outputWriter.printLine("No pizza can satisfy these requests.");
            }
            constraints = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String orderPizza(List<int[]> constraintList, int index, int[] toppings) {
        if (index == toppings.length) {
            if (isValidPizza(constraintList, toppings)) {
                return buildPizzaFromToppings(toppings);
            } else {
                return null;
            }
        }

        toppings[index] = 1;
        String pizzaWithTopping = orderPizza(constraintList, index + 1, toppings);
        if (pizzaWithTopping != null) {
            return pizzaWithTopping;
        }

        toppings[index] = -1;
        String pizzaWithoutTopping = orderPizza(constraintList, index + 1, toppings);
        toppings[index] = 0;
        return pizzaWithoutTopping;
    }

    private static boolean isValidPizza(List<int[]> constraintList, int[] toppings) {
        for (int[] constraint : constraintList) {
            boolean matchesAtLeastOne = false;

            for (int i = 0; i < constraint.length; i++) {
                if (constraint[i] != 0 && constraint[i] == toppings[i]) {
                    matchesAtLeastOne = true;
                    break;
                }
            }

            if (!matchesAtLeastOne) {
                return false;
            }
        }
        return true;
    }

    private static String buildPizzaFromToppings(int[] toppings) {
        StringBuilder pizza = new StringBuilder();
        for (int i = 0; i < toppings.length; i++) {
            if (toppings[i] == 1) {
                char topping = (char) (i + 'A');
                pizza.append(topping);
            }
        }
        return pizza.toString();
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
