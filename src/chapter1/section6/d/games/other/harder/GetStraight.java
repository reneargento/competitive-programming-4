package chapter1.section6.d.games.other.harder;

import java.util.*;

/**
 * Created by Rene Argento on 21/10/20.
 */
public class GetStraight {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String card = scanner.next();

        while (!card.equals("#")) {
            String[] originalCards = new String[5];
            originalCards[0] = card;

            int[] values = new int[5];
            values[0] = getValue(card);

            for (int i = 1; i <= 4; i++) {
                originalCards[i] = scanner.next();
                values[i] = getValue(originalCards[i]);
            }

            int[] valuesCopy = new int[values.length];
            System.arraycopy(values, 0, valuesCopy, 0, values.length);

            Map<Integer, Integer> multiplier = getMultiplier(values);

            double maxPayment = getPayment(valuesCopy, false) - 1;
            int cardIndexToSwap = -1;

            for (int i = 0; i < values.length; i++) {
                double totalPayment = 0;

                for (int valueToSwap = 1; valueToSwap <= 13; valueToSwap++) {
                    valuesCopy = new int[values.length];
                    System.arraycopy(values, 0, valuesCopy, 0, values.length);
                    valuesCopy[i] = valueToSwap;

                    double payment = getPayment(valuesCopy, false);
                    totalPayment += payment * multiplier.get(valueToSwap);
                }

                double finalPayment = (totalPayment / 47.0) - 2;
                if (finalPayment > maxPayment) {
                    maxPayment = finalPayment;
                    cardIndexToSwap = i;
                }
            }

            if (cardIndexToSwap == -1) {
                System.out.println("Stay");
            } else {
                System.out.printf("Exchange %s\n", originalCards[cardIndexToSwap]);
            }
            card = scanner.next();
        }
    }

    private static Map<Integer, Integer> getMultiplier(int[] values) {
        Map<Integer, Integer> multiplier = new HashMap<>();

        for (int i = 1; i <= 13; i++) {
            multiplier.put(i, 4);
        }

        for (int value : values) {
            int current = multiplier.get(value);
            multiplier.put(value, current - 1);
        }

        return multiplier;
    }

    private static int getPayment(int[] values, boolean getMaxRun) {
        Arrays.sort(values);
        int maxRun = 0;

        boolean isBedAndBreakfast = false;
        boolean isDoubleDutch = false;

        for (int i = 0; i < values.length; i++) {
            int value = values[i];
            int run = 1;
            int index = i + 1;

            boolean[] used = new boolean[values.length];
            used[i] = true;

            while (true) {
                index %= values.length;
                if (index == i) {
                    break;
                }

                if (values[index] == value + 1
                        || (values[index] == 1 && value == 13)) {
                    run++;
                    value = values[index];
                    used[index] = true;
                }
                index++;
            }

            if (run == 3) {
                int[] newValues = getNewValues(values, used);

                int newRun = getPayment(newValues, true);
                if (newRun == 2) {
                    isBedAndBreakfast = true;
                }
            } else if (run == 2) {
                int[] newValues = getNewValues(values, used);

                int newRun = getPayment(newValues, true);
                if (newRun == 3) {
                    isBedAndBreakfast = true;
                } else if (newRun == 2) {
                    isDoubleDutch = true;
                }
            }

            maxRun = Math.max(maxRun, run);
        }

        if (getMaxRun) {
            return maxRun;
        }

        if (maxRun == 5) {
            return 100;
        } else if (maxRun == 4) {
            return 10;
        } else if (isBedAndBreakfast) {
            return 5;
        } else if (maxRun == 3) {
            return 3;
        } else if (isDoubleDutch) {
            return 1;
        }
        return 0;
    }

    private static int[] getNewValues(int[] values, boolean[] used) {
        List<Integer> newValuesList = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (!used[i]) {
                newValuesList.add(values[i]);
            }
        }

        int[] newValues = new int[newValuesList.size()];
        for (int i = 0; i < newValuesList.size(); i++) {
            newValues[i] = newValuesList.get(i);
        }
        return newValues;
    }

    private static int getValue(String card) {
        char value = card.charAt(0);
        if (Character.isDigit(value)) {
            return Character.getNumericValue(value);
        }

        switch (value) {
            case 'T': return 10;
            case 'J': return 11;
            case 'Q': return 12;
            case 'K': return 13;
            default: return 1;
        }
    }
}
