package chapter2.section2.h.bit.manipulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 08/03/21.
 */
// Based on https://github.com/neungkl/parrots-IOI-solution and
// https://github.com/neungkl/parrots-IOI-solution/blob/master/solution/student-group-4.cpp
// Problem located at https://ioinformatics.org/files/ioi2011problem6.pdf
public class Parrots {
    private static final List<Integer> encodedValuesList = new ArrayList<>();

    public static void main(String[] args) {
        int[] originalMessage = { 255, 255, 10, 255, 255, 255, 255, 1, 2, 3, 255 };
        encode(originalMessage.length, originalMessage);

        Collections.shuffle(encodedValuesList);
        Integer[] encodedMessage = new Integer[encodedValuesList.size()];
        encodedValuesList.toArray(encodedMessage);
        System.out.println();

        decode(originalMessage.length, encodedMessage.length, encodedMessage);
    }

    private static void encode(int N, int[] M) {
        int maxColumns = 16;
        long[][] dp = new long[maxColumns * 2][maxColumns];

        for (int i = 0; i < maxColumns * 2; i++) {
            for (int j = 0; j < maxColumns; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (i == 1 && j == 0) {
                    dp[i][0] = 1 + dp[i - 1][maxColumns - 1];
                } else if (j == 0) {
                    dp[i][0] = (dp[i - 1][0] - dp[i - 2][0]) + dp[i - 1][maxColumns - 1];
                } else {
                    dp[i][j] = dp[i][j - 1] + (dp[i - 1][j] - dp[i - 1][0]);
                }
            }
        }

        long fourNumbers;
        for (int i = 0, messageGroup = 0; i < N; i += 4, messageGroup++) {
            long number1 = M[i];
            long number2 = i + 1 < N ? M[i + 1] : 0;
            long number3 = i + 2 < N ? M[i + 2] : 0;
            long number4 = i + 3 < N ? M[i + 3] : 0;
            fourNumbers = number1 | (number2 << 8) | (number3 << 16) | (number4 << 24);

            System.out.println("Encode group " + fourNumbers);

            int row = 0;
            int column = 0;
            while (dp[row][column] <= fourNumbers) {
                column++;
                if (column >= maxColumns) {
                    column = 0;
                    row++;
                }
            }
            column--;
            if (column < 0) {
                column = maxColumns - 1;
                row--;
            }

            fourNumbers -= dp[row][column];

            int value = (messageGroup << 4) | column;
            send(value);

            for (row = row - 1; row >= 0; --row) {
                fourNumbers += dp[row][0];
                while (dp[row][column] > fourNumbers) {
                    column--;
                }

                value = (messageGroup << 4) | column;
                send(value);
                fourNumbers -= dp[row][column];
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void decode(int N, int L, Integer[] X) {
        int maxColumns = 16;
        long[][] dp = new long[maxColumns * 2][maxColumns];

        for (int i = 0; i < maxColumns * 2; i++) {
            for (int j = 0; j < maxColumns; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (i == 1 && j == 0) {
                    dp[i][0] = 1 + dp[i - 1][maxColumns - 1];
                } else if (j == 0) {
                    dp[i][0] = (dp[i - 1][0] - dp[i - 2][0]) + dp[i - 1][maxColumns - 1];
                } else {
                    dp[i][j] = dp[i][j - 1] + (dp[i - 1][j] - dp[i - 1][0]);
                }
            }
        }

        List<Integer>[] parrots = new ArrayList[16];
        for (int i = 0; i < parrots.length; i++) {
            parrots[i] = new ArrayList<>();
        }

        for (int i = 0; i < L; i++) {
            int messageGroup = (X[i] >> 4) & 0xf; // 0xf = 1111
            int value = X[i] & 0xf;
            parrots[messageGroup].add(value);
        }

        for (int message = 0, messageGroup = 0; message < N; message += 4, messageGroup++) {
            parrots[messageGroup].sort(Collections.reverseOrder());

            long fourNumbers = 0;
            int row = parrots[messageGroup].size() - 1;
            for (int i = 0; i < parrots[messageGroup].size(); i++, row--) {
                int column = parrots[messageGroup].get(i);
                if (i == 0) {
                    fourNumbers += dp[row][column];
                } else {
                    fourNumbers += dp[row][column] - dp[row][0];
                }
            }

            System.out.println("Decode group " + fourNumbers);

            long decodedValue1 = (fourNumbers & 0xff); // 0xf = 11111111
            output(decodedValue1);
            if (message + 1 < N) {
                long decodedValue2 = ((fourNumbers >> 8) & 0xff);
                output(decodedValue2);
            }
            if (message + 2 < N) {
                long decodedValue3 = ((fourNumbers >> 16) & 0xff);
                output(decodedValue3);
            }
            if (message + 3 < N) {
                long decodedValue4 = ((fourNumbers >> 24) & 0xff);
                output(decodedValue4);
            }
        }
    }

    private static void send(int value) {
        System.out.println("Sent " + value);
        encodedValuesList.add(value);
    }

    private static void output(long value) {
        System.out.println("Decoded " + value);
    }
}
