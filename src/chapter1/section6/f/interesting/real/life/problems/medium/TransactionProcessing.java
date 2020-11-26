package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.*;

/**
 * Created by Rene Argento on 05/11/20.
 */
public class TransactionProcessing {

    private static class Account {
        String id;
        String name;
        List<String> transactions;
        double balance;

        public Account(String id, String name) {
            this.id = id;
            this.name = name;
            transactions = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        Map<String, Account> accountMap = new HashMap<>();

        while (!line.substring(0, 3).equals("000")) {
            String accountId = line.substring(0, 3);
            String accountName = line.substring(3);

            Account account = new Account(accountId, accountName);
            accountMap.put(accountId, account);

            line = scanner.nextLine();
        }

        line = scanner.nextLine();
        List<String> accountsToQuery = new ArrayList<>();

        while (!line.substring(0, 3).equals("000")) {
            String[] data = line.split(" ");
            String account1Id = data[0].substring(0, 3);
            String account2Id = data[0].substring(3);

            if (accountsToQuery.isEmpty()
                    || !accountsToQuery.get(accountsToQuery.size() - 1).equals(account1Id)) {
                accountsToQuery.add(account1Id);
            }

            double value = Integer.parseInt(data[data.length - 1]) / 100.0;

            String account2Name = accountMap.get(account2Id).name;
            String transaction = String.format("%s %-30s %10.2f", account2Id, account2Name, value);

            if (!accountMap.containsKey(account1Id)) {
                accountMap.put(account1Id, new Account(account1Id, ""));
            }

            Account account1 = accountMap.get(account1Id);
            account1.transactions.add(transaction);
            account1.balance += value;

            line = scanner.nextLine();
        }
        printAccountsOutOfBalance(accountsToQuery, accountMap);
    }

    private static void printAccountsOutOfBalance(List<String> accountsToQuery, Map<String, Account> accountMap) {
        for (String accountId : accountsToQuery) {
            Account account = accountMap.get(accountId);

            if (account.balance != 0) {
                System.out.printf("*** Transaction %s is out of balance ***\n", account.id);
                for (String transaction : account.transactions) {
                    System.out.println(transaction);
                }
                System.out.printf("999 %-30s %10.2f\n\n", "Out of Balance", -account.balance);
            }
        }
    }
}
