package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 12/11/20.
 */
public class BalancingBankAccounts {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int travellers = scanner.nextInt();
        int transactions = scanner.nextInt();
        int caseNumber = 1;

        while (travellers != 0 || transactions != 0) {
            Map<String, Integer> accounts = new HashMap<>();
            for (int i = 0; i < travellers; i++) {
                accounts.put(scanner.next(), 0);
            }

            for (int i = 0; i < transactions; i++) {
                String traveller1 = scanner.next();
                String traveller2 = scanner.next();
                int amount = scanner.nextInt();

                int accountBalance1 = accounts.get(traveller1);
                int accountBalance2 = accounts.get(traveller2);
                accounts.put(traveller1, accountBalance1 - amount);
                accounts.put(traveller2, accountBalance2 + amount);
            }
            System.out.printf("Case #%d\n", caseNumber);
            balanceAccounts(accounts);
            System.out.println();

            caseNumber++;
            travellers = scanner.nextInt();
            transactions = scanner.nextInt();
        }
    }

    private static void balanceAccounts(Map<String, Integer> accounts) {
        while (!areAllAccountsBalanced(accounts)) {
            String highestValueTraveller = getHighestValueAccount(accounts);
            String lowestValueTraveller = getLowestValueAccount(accounts);
            int value1 = accounts.get(highestValueTraveller);
            int value2 = accounts.get(lowestValueTraveller);

            int valueToTransfer = Math.min(value1, Math.abs(value2));
            accounts.put(highestValueTraveller, value1 - valueToTransfer);
            accounts.put(lowestValueTraveller, value2 + valueToTransfer);

            System.out.printf("%s %s %d\n", highestValueTraveller, lowestValueTraveller, valueToTransfer);
        }
    }

    private static boolean areAllAccountsBalanced(Map<String, Integer> accounts) {
        boolean balanced = true;

        for (Integer value : accounts.values()) {
            if (value != 0) {
                balanced = false;
                break;
            }
        }
        return balanced;
    }

    private static String getHighestValueAccount(Map<String, Integer> accounts) {
        String highestValueTraveller = null;
        int maxValue = 0;

        for (String traveller : accounts.keySet()) {
            int value = accounts.get(traveller);

            if (value > maxValue) {
                maxValue = value;
                highestValueTraveller = traveller;
            }
        }
        return highestValueTraveller;
    }

    private static String getLowestValueAccount(Map<String, Integer> accounts) {
        String lowestValueTraveller = null;
        int minValue = Integer.MAX_VALUE;

        for (String traveller : accounts.keySet()) {
            int value = accounts.get(traveller);

            if (value < minValue) {
                minValue = value;
                lowestValueTraveller = traveller;
            }
        }
        return lowestValueTraveller;
    }

}
