package chapter1.section6.m.input.parsing.iterative;

import java.util.*;

/**
 * Created by Rene Argento on 07/12/20.
 */
public class EnglishNumberTranslator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> descriptionToNumberMap = getDescriptionToNumberMap();
        Set<String> multipliers = getMultipliers();

        while (scanner.hasNext()) {
            int number = getNumber(scanner.nextLine(), descriptionToNumberMap, multipliers);
            System.out.println(number);
        }
    }

    private static int getNumber(String description, Map<String, Integer> descriptionToNumberMap,
                                 Set<String> multipliers) {
        int number = 0;
        String[] descriptors = description.split(" ");
        boolean millionUsed = false;
        boolean thousandUsed = false;

        int startIndex = descriptors[0].equals("negative") ? 1 : 0;
        int currentSum = 0;

        for (int i = startIndex; i < descriptors.length; i++) {
            String descriptor = descriptors[i];
            int value = descriptionToNumberMap.get(descriptor);

            if (multipliers.contains(descriptor)) {
                currentSum = value * currentSum;

                if (!descriptor.equals("hundred") || (millionUsed && thousandUsed)) {
                    number += currentSum;
                    currentSum = 0;
                }

                if (descriptor.equals("million")) {
                    millionUsed = true;
                }
                if (descriptor.equals("thousand")) {
                    thousandUsed = true;
                }
            } else {
                currentSum += value;
            }
        }
        number += currentSum;

        if (descriptors[0].equals("negative")) {
            number *= -1;
        }
        return number;
    }

    private static Map<String, Integer> getDescriptionToNumberMap() {
        Map<String, Integer> descriptionToNumberMap = new HashMap<>();
        descriptionToNumberMap.put("zero", 0);
        descriptionToNumberMap.put("one", 1);
        descriptionToNumberMap.put("two", 2);
        descriptionToNumberMap.put("three", 3);
        descriptionToNumberMap.put("four", 4);
        descriptionToNumberMap.put("five", 5);
        descriptionToNumberMap.put("six", 6);
        descriptionToNumberMap.put("seven", 7);
        descriptionToNumberMap.put("eight", 8);
        descriptionToNumberMap.put("nine", 9);
        descriptionToNumberMap.put("ten", 10);
        descriptionToNumberMap.put("eleven", 11);
        descriptionToNumberMap.put("twelve", 12);
        descriptionToNumberMap.put("thirteen", 13);
        descriptionToNumberMap.put("fourteen", 14);
        descriptionToNumberMap.put("fifteen", 15);
        descriptionToNumberMap.put("sixteen", 16);
        descriptionToNumberMap.put("seventeen", 17);
        descriptionToNumberMap.put("eighteen", 18);
        descriptionToNumberMap.put("nineteen", 19);
        descriptionToNumberMap.put("twenty", 20);
        descriptionToNumberMap.put("thirty", 30);
        descriptionToNumberMap.put("forty", 40);
        descriptionToNumberMap.put("fifty", 50);
        descriptionToNumberMap.put("sixty", 60);
        descriptionToNumberMap.put("seventy", 70);
        descriptionToNumberMap.put("eighty", 80);
        descriptionToNumberMap.put("ninety", 90);
        descriptionToNumberMap.put("hundred", 100);
        descriptionToNumberMap.put("thousand", 1000);
        descriptionToNumberMap.put("million", 1000000);
        return descriptionToNumberMap;
    }

    private static Set<String> getMultipliers() {
        Set<String> multipliers = new HashSet<>();
        multipliers.add("hundred");
        multipliers.add("thousand");
        multipliers.add("million");
        return multipliers;
    }
}
