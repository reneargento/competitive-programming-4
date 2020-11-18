package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/09/20.
 */
public class ZerosAndOnes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String digits;
        int caseId = 1;

        while (scanner.hasNext()) {
            digits = scanner.nextLine();
            if (digits.equals("\n")) {
                break;
            }

            System.out.printf("Case %d:\n", caseId);

            int[] prefixSum = getPrefixSum(digits);
            int queries = scanner.nextInt();

            for (int q = 0; q < queries; q++) {
                int index1 = scanner.nextInt();
                int index2 = scanner.nextInt();

                int min = Math.min(index1, index2);
                int max = Math.max(index1, index2);

                boolean areTheSame = false;

                if (digits.charAt(min) == '0' && digits.charAt(max) == '0') {
                    if (prefixSum[max] == prefixSum[min]) {
                        areTheSame = true;
                    }
                } else if (digits.charAt(min) == '1' && digits.charAt(max) == '1') {
                    int difference = max - min + 1;
                    if (prefixSum[max] - prefixSum[min] + 1 == difference) {
                        areTheSame = true;
                    }
                }

                if (areTheSame) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
            }

            caseId++;
            scanner.nextLine();
        }
    }

    private static int[] getPrefixSum(String digits) {
        int[] prefixSum = new int[digits.length()];

        for (int i = 0; i < digits.length(); i++) {
            int value = 0;
            if (digits.charAt(i) == '1') {
                value = 1;
            }

            if (i == 0) {
                prefixSum[i] = value;
            } else {
                prefixSum[i] = prefixSum[i - 1] + value;
            }
        }
        return prefixSum;
    }

}
