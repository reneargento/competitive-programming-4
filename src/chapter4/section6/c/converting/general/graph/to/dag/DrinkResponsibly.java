package chapter4.section6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

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
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        double moneyDouble = inputReader.nextDouble();
        double targetUnitsDouble = inputReader.nextDouble();
        Drink[] drinks = new Drink[inputReader.nextInt()];

        int money = (int) (moneyDouble * 100);
        int targetUnits = (int) (targetUnitsDouble * 10);

        for (int i = 0; i < drinks.length; i++) {
            String name = inputReader.next();
            int strength = inputReader.nextInt();
            String size = inputReader.next();
            int cost = (int) (inputReader.nextDouble() * 100);

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

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        private int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private double nextDouble() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                res *= 10;
                res += c - '0';
                c = snext();
            }
            if (c == '.') {
                c = snext();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = snext();
                }
            }
            return res * sgn;
        }

        private String next() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
