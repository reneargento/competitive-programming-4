package chapter6.section3.section1;

public class Exercise5 {

    private static final int STOP = 0;
    private static final int DIAGONAL = 1;
    private static final int UP = 2;
    private static final int LEFT = 3;

    private static class Result {
        String alignedString1;
        String alignedString2;
        int maxScore;

        public Result(String alignedString1, String alignedString2, int maxScore) {
            this.alignedString1 = alignedString1;
            this.alignedString2 = alignedString2;
            this.maxScore = maxScore;
        }
    }

    private static class BestCell {
        int bestRow;
        int bestColumn;
        int maxScore;

        public BestCell(int bestRow, int bestColumn, int maxScore) {
            this.bestRow = bestRow;
            this.bestColumn = bestColumn;
            this.maxScore = maxScore;
        }
    }

    private static Result smithWaterman(String string1, String string2, int matchScore, int mismatchScore,
                                        int gapPenalty) {
        int length1 = string1.length();
        int length2 = string2.length();

        int[][] score = new int[length1 + 1][length2 + 1];
        int[][] traceback = new int[length1 + 1][length2 + 1];
        BestCell bestCell = buildScoresAndTraceback(string1, string2, matchScore, mismatchScore, gapPenalty, score,
                traceback);

        StringBuilder alignedString1 = new StringBuilder();
        StringBuilder alignedString2 = new StringBuilder();

        int row = bestCell.bestRow;
        int column = bestCell.bestColumn;

        while (traceback[row][column] != STOP) {
            switch (traceback[row][column]) {
                case DIAGONAL: {
                    alignedString1.append(string1.charAt(row - 1));
                    alignedString2.append(string2.charAt(column - 1));
                    row--;
                    column--;
                    break;
                }
                case UP: {
                    alignedString1.append(string1.charAt(row - 1));
                    alignedString2.append("_");
                    row--;
                    break;
                }
                case LEFT: {
                    alignedString1.append("_");
                    alignedString2.append(string2.charAt(column - 1));
                    column--;
                    break;
                }
            }
        }

        alignedString1.reverse();
        alignedString2.reverse();
        return new Result(alignedString1.toString(), alignedString2.toString(), bestCell.maxScore);
    }

    private static BestCell buildScoresAndTraceback(String string1, String string2, int matchScore, int mismatchScore,
                                                    int gapPenalty, int[][] score, int[][] traceback) {
        int maxScore = 0;
        int bestRow = 0;
        int bestColumn = 0;

        for (int i = 1; i <= string1.length(); i++) {
            for (int j = 1; j <= string2.length(); j++) {
                int score1 = score[i - 1][j - 1] +
                        (string1.charAt(i - 1) == string2.charAt(j - 1) ? matchScore : mismatchScore);
                int score2 = score[i - 1][j] - gapPenalty;
                int score3 = score[i][j - 1] - gapPenalty;

                int highestScore = 0;
                int direction = STOP;

                if (score1 > highestScore) {
                    highestScore = score1;
                    direction = DIAGONAL;
                }
                if (score2 > highestScore) {
                    highestScore = score2;
                    direction = UP;
                }
                if (score3 > highestScore) {
                    highestScore = score3;
                    direction = LEFT;
                }

                score[i][j] = highestScore;
                traceback[i][j] = direction;

                if (highestScore > maxScore) {
                    maxScore = highestScore;
                    bestRow = i;
                    bestColumn = j;
                }
            }
        }
        return new BestCell(bestRow, bestColumn, maxScore);
    }

    public static void main() {
        String string1 = "ACACACTA";
        String string2 = "AGCACACA";

        Result result = smithWaterman(string1, string2, 2, -1, 2);
        System.out.println("Score: " + result.maxScore);
        System.out.println("Expected: 10");

        System.out.println("\nA = " + result.alignedString1);
        System.out.println("B = " + result.alignedString2);
        System.out.println("Expected");
        System.out.println("A = ACACA\n" +
                "B = ACACA");
    }
}
