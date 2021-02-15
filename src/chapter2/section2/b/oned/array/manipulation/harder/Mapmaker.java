package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.*;

/**
 * Created by Rene Argento on 28/01/21.
 */
public class Mapmaker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arrays = scanner.nextInt();
        int references = scanner.nextInt();
        scanner.nextLine();

        Map<String, int[]> physicalAddresses = new HashMap<>();

        for (int i = 0; i < arrays; i++) {
            List<String> input = getWords(scanner.nextLine());
            String name = input.get(0);
            int baseAddress = Integer.parseInt(input.get(1));
            int sizeInBytes = Integer.parseInt(input.get(2));
            int dimensions = Integer.parseInt(input.get(3));

            int[] lowerBounds = new int[dimensions];
            int[] upperBounds = new int[dimensions];
            int endIndex = 4 + dimensions * 2;
            int boundsIndex = 0;

            for (int j = 4; j < endIndex; j += 2) {
                lowerBounds[boundsIndex] = Integer.parseInt(input.get(j));
                upperBounds[boundsIndex] = Integer.parseInt(input.get(j + 1));
                boundsIndex++;
            }
            computePhysicalAddresses(physicalAddresses, name, baseAddress, sizeInBytes, dimensions, lowerBounds, upperBounds);
        }

        for (int i = 0; i < references; i++) {
            List<String> input = getWords(scanner.nextLine());
            String name = input.get(0);
            long physicalAddress = queryPhysicalAddresses(physicalAddresses.get(name), input);
            outputResult(name, input, physicalAddress);
        }
    }

    private static void computePhysicalAddresses(Map<String, int[]> physicalAddresses, String name, int baseAddress,
                                                 int sizeInBytes, int dimensions, int[] lowerBounds, int[] upperBounds) {
        int[] physicalAddressValues = new int[dimensions + 1];
        physicalAddressValues[physicalAddressValues.length - 1] = sizeInBytes;

        for (int i = physicalAddressValues.length - 2; i >= 1; i--) {
            physicalAddressValues[i] = (physicalAddressValues[i + 1] * (upperBounds[i] - lowerBounds[i] + 1));
        }

        physicalAddressValues[0] = baseAddress;
        for (int i = 1; i < physicalAddressValues.length; i++) {
            physicalAddressValues[0] -= (physicalAddressValues[i] * lowerBounds[i - 1]);
        }
        physicalAddresses.put(name, physicalAddressValues);
    }

    private static long queryPhysicalAddresses(int[] physicalAddresses, List<String> indexesQueried) {
        long physicalAddress = physicalAddresses[0];
        for (int i = 1; i < indexesQueried.size(); i++) {
            int index = Integer.parseInt(indexesQueried.get(i));
            physicalAddress += (physicalAddresses[i] * index);
        }
        return physicalAddress;
    }

    private static void outputResult(String name, List<String> indexesQueried, long physicalAddress) {
        StringJoiner indexesDescription = new StringJoiner(", ");
        for (int i = 1; i < indexesQueried.size(); i++) {
            indexesDescription.add(indexesQueried.get(i));
        }
        System.out.printf("%s[%s] = %d\n", name, indexesDescription.toString(), physicalAddress);
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
    }
}
