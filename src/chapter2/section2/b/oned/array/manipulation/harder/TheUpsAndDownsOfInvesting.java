package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 02/02/21.
 */
public class TheUpsAndDownsOfInvesting {

    private static class StockAnalysis {
        int peaks;
        int valleys;

        public StockAnalysis(int peaks, int valleys) {
            this.peaks = peaks;
            this.valleys = valleys;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stocksNumber = scanner.nextInt();
        int peakSequence = scanner.nextInt();
        int valleySequence = scanner.nextInt();
        int[] stocks = new int[stocksNumber];

        for (int i = 0; i < stocks.length; i++) {
            stocks[i] = scanner.nextInt();
        }

        StockAnalysis stockAnalysis = countPeaksAndValleys(stocks, peakSequence, valleySequence);
        System.out.printf("%d %d\n", stockAnalysis.peaks, stockAnalysis.valleys);
    }

    private static StockAnalysis countPeaksAndValleys(int[] stocks, int peakSequence, int valleySequence) {
        if (stocks.length < 2) {
            return new StockAnalysis(0, 0);
        }

        int peaks = 0;
        int valleys = 0;

        boolean decreasing = stocks[1] < stocks[0];
        int increaseNumber = decreasing ? 0 : 2;
        int decreaseNumber = decreasing ? 2 : 0;

        for (int i = 2; i < stocks.length; i++) {
            if (stocks[i] > stocks[i - 1]) {
                if (decreasing) {
                    if (increaseNumber >= peakSequence && decreaseNumber >= peakSequence) {
                        peaks++;
                    }

                    increaseNumber = 2;
                    decreasing = false;
                } else {
                    increaseNumber++;
                }
            } else {
                if (!decreasing) {
                    if (increaseNumber >= valleySequence && decreaseNumber >= valleySequence) {
                        valleys++;
                    }

                    decreaseNumber = 2;
                    decreasing = true;
                } else {
                    decreaseNumber++;
                }
            }
        }

        if (decreasing && increaseNumber >= peakSequence && decreaseNumber >= peakSequence) {
            peaks++;
        } else if (!decreasing && increaseNumber >= valleySequence && decreaseNumber >= valleySequence) {
            valleys++;
        }

        return new StockAnalysis(peaks, valleys);
    }
}
