package chapter1.section4.j.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 24/09/20.
 */
public class GreedyGiftGivers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = 0;

        while (scanner.hasNext()) {
            int people = scanner.nextInt();
            Map<String, Integer> nameToIndexMap = new HashMap<>();
            Map<Integer, String> indexToNameMap = new HashMap<>();
            long[] money = new long[people];

            for (int p = 0; p < people; p++) {
                String name = scanner.next();
                nameToIndexMap.put(name, p);
                indexToNameMap.put(p, name);
            }

            for (int p = 0; p < people; p++) {
                String name = scanner.next();
                int index = nameToIndexMap.get(name);

                int value = scanner.nextInt();
                int peopleReceiving = scanner.nextInt();

                money[index] -= value;

                if (peopleReceiving != 0) {
                    int valueGiven = value / peopleReceiving;
                    int valueKept = value % peopleReceiving;

                    for (int r = 0; r < peopleReceiving; r++) {
                        String receiverName = scanner.next();
                        int receiverIndex = nameToIndexMap.get(receiverName);
                        money[receiverIndex] += valueGiven;
                    }
                    money[index] += valueKept;
                } else {
                    money[index] += value;
                }
            }

            if (test > 0) {
                System.out.println();
            }

            for (int p = 0; p < people; p++) {
                System.out.printf("%s %d\n", indexToNameMap.get(p), money[p]);
            }

            test++;
        }

    }

}
