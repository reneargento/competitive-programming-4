package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.*;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class OnlyIDidIt {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            List<Set<Integer>> solvedProblems = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                Set<Integer> currentSolvedProblems = new HashSet<>();

                int problems = scanner.nextInt();
                for (int p = 0; p < problems; p++) {
                    currentSolvedProblems.add(scanner.nextInt());
                }
                solvedProblems.add(currentSolvedProblems);
            }

            List<Set<Integer>> uniqueProblems = computeUniqueProblems(solvedProblems);
            List<Integer> winners = winners(uniqueProblems);

            System.out.printf("Case #%d:\n", t);
            for (int winner : winners) {
                StringJoiner winnerDescription = new StringJoiner(" ");
                winnerDescription.add(String.valueOf(winner + 1));
                winnerDescription.add(String.valueOf(uniqueProblems.get(winner).size()));

                List<Integer> sortedProblems = new ArrayList<>(uniqueProblems.get(winner));
                Collections.sort(sortedProblems);

                for (int problem : sortedProblems) {
                    winnerDescription.add(String.valueOf(problem));
                }
                System.out.println(winnerDescription);
            }
        }
    }

    private static List<Set<Integer>> computeUniqueProblems(List<Set<Integer>> solvedProblems) {
        List<Set<Integer>> uniqueProblems = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Set<Integer> currentUniqueProblems = new HashSet<>();
            Set<Integer> currentSolvedProblems = solvedProblems.get(i);
            int[] otherIndexes = otherIndexes(i);

            for (int p = 0; p < currentSolvedProblems.size(); p++) {
                for (int problem : currentSolvedProblems) {
                    if (!solvedProblems.get(otherIndexes[0]).contains(problem)
                            && !solvedProblems.get(otherIndexes[1]).contains(problem)) {
                        currentUniqueProblems.add(problem);
                    }
                }
            }
            uniqueProblems.add(currentUniqueProblems);
        }
        return uniqueProblems;
    }

    private static int[] otherIndexes(int index) {
        switch (index) {
            case 0: return new int[] { 1, 2 };
            case 1: return new int[] { 0, 2 };
            default: return new int[] { 0, 1 };
        }
    }

    private static List<Integer> winners(List<Set<Integer>> uniqueProblems) {
        List<Integer> winners = new ArrayList<>();
        int size0 = uniqueProblems.get(0).size();
        int size1 = uniqueProblems.get(1).size();
        int size2 = uniqueProblems.get(2).size();

        if (size0 >= size1 && size0 >= size2) {
            winners.add(0);
        }
        if (size1 >= size0 && size1 >= size2) {
            winners.add(1);
        }
        if (size2 >= size0 && size2 >= size1) {
            winners.add(2);
        }
        return winners;
    }
}
