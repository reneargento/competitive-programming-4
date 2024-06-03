package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/05/24.
 */
public class DrinkResponsibly {

    private static class Drink {
        String name;
        int units;
        double unitsDouble;
        int cost;

        public Drink(String name, int units, double unitsDouble, int cost) {
            this.name = name;
            this.units = units;
            this.cost = cost;
            this.unitsDouble = unitsDouble;
        }
    }

    private static class DrinkPurchased {
        String name;
        int quantity;

        public DrinkPurchased(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }
    }

    private static final double EPSILON = 0.00000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        double moneyDouble = FastReader.nextDouble();
        double targetUnitsDouble = FastReader.nextDouble();
        Drink[] drinks = new Drink[FastReader.nextInt()];

        int money = (int) (moneyDouble * 100);
        int targetUnits = (int) (targetUnitsDouble * 10);

        for (int i = 0; i < drinks.length; i++) {
            String name = FastReader.next();
            int strength = FastReader.nextInt();
            String size = FastReader.next();
            int cost = (int) (FastReader.nextDouble() * 100);

            double unitsDouble = getUnits(strength, size);
            int units = (int) Math.floor(unitsDouble * 10);
            drinks[i] = new Drink(name, units, unitsDouble, cost);
        }

        List<DrinkPurchased> drinksPurchased = computeDrinksToPurchase(money, targetUnits, drinks, targetUnitsDouble);
        if (drinksPurchased == null) {
            outputWriter.printLine("IMPOSSIBLE");
        } else {
            for (DrinkPurchased drink : drinksPurchased) {
                outputWriter.printLine(drink.name + " " + drink.quantity);
            }
        }
        outputWriter.flush();
    }

    private static List<DrinkPurchased> computeDrinksToPurchase(int money, int targetUnits, Drink[] drinks,
                                                                double targetUnitsDouble) {
        // dp[drink id][money left][floor(units left)]
        Boolean[][][] dp = new Boolean[drinks.length][money + 1][targetUnits + 1];
        boolean isPossible = computeDrinksToPurchase(drinks, dp, targetUnitsDouble, money, targetUnits, 0);

        if (isPossible) {
            return computeDrinksToPurchase(drinks, dp, money, targetUnits);
        }
        return null;
    }

    private static boolean computeDrinksToPurchase(Drink[] drinks, Boolean[][][] dp, double unitsDouble,
                                                   int money, int units, int drinkId) {
        if (money == 0 && (unitsDouble - EPSILON <= 0)) {
            return true;
        }
        if (drinkId == drinks.length) {
            return false;
        }

        if (dp[drinkId][money][units] != null) {
            return dp[drinkId][money][units];
        }

        int quantity = 0;
        while (true) {
            Drink drink = drinks[drinkId];
            int unitsToDrink = quantity * drink.units;
            double unitsDoubleToDrink = quantity * drink.unitsDouble;
            int moneyToSpend = quantity * drink.cost;

            int nextMoney = money - moneyToSpend;
            int nextUnits = units - unitsToDrink;
            double nextUnitsDouble = unitsDouble - unitsDoubleToDrink;
            if (nextMoney < 0 || nextUnitsDouble < 0) {
                break;
            }
            boolean isPossible = computeDrinksToPurchase(drinks, dp, nextUnitsDouble, nextMoney, nextUnits,
                    drinkId + 1);
            if (isPossible) {
                dp[drinkId][nextMoney][nextUnits] = true;
                return true;
            }
            quantity++;
        }
        dp[drinkId][money][units] = false;
        return false;
    }

    private static List<DrinkPurchased> computeDrinksToPurchase(Drink[] drinks, Boolean[][][] dp, int money, int units) {
        List<DrinkPurchased> drinksPurchased = new ArrayList<>();

        for (int drinkId = 0; drinkId < drinks.length; drinkId++) {
            int quantity = 0;

            while (true) {
                Drink drink = drinks[drinkId];
                int unitsToDrink = quantity * drink.units;
                int moneyToSpend = quantity * drink.cost;

                int nextMoney = money - moneyToSpend;
                int nextUnits = units - unitsToDrink;
                if (nextMoney < 0 || nextUnits < 0) {
                    break;
                }

                if (dp[drinkId][nextMoney][nextUnits] != null
                        && dp[drinkId][nextMoney][nextUnits]) {
                    if (quantity != 0) {
                        drinksPurchased.add(new DrinkPurchased(drink.name, quantity));
                    }
                    money = nextMoney;
                    units = nextUnits;
                    break;
                }
                quantity++;
            }
        }
        return drinksPurchased;
    }

    private static double getUnits(double strength, String size) {
        int divider = Character.getNumericValue(size.charAt(2));
        return strength / divider;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
