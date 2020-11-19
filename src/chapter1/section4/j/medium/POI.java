package chapter1.section4.j.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 26/09/20.
 */
@SuppressWarnings("unchecked")
public class POI {

    private static class Contestant implements Comparable<Contestant> {
        int score;
        int tasksSolved;
        int id;

        public Contestant(int score, int tasksSolved, int id) {
            this.score = score;
            this.tasksSolved = tasksSolved;
            this.id = id;
        }

        @Override
        public int compareTo(Contestant other) {
            if (score > other.score) {
                return -1;
            } else if (score < other.score) {
                return 1;
            }

            if (tasksSolved > other.tasksSolved) {
                return -1;
            } else if (tasksSolved < other.tasksSolved) {
                return 1;
            }

            return id - other.id;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int contestants = scanner.nextInt();
        int tasks = scanner.nextInt();
        int philipsId = scanner.nextInt() - 1;

        int[] unsolvedTasksCount = new int[tasks];
        List<Integer>[] solvedTasks = new ArrayList[contestants];

        for (int i = 0; i < contestants; i++) {
            solvedTasks[i] = new ArrayList<>();

            for (int t = 0; t < tasks; t++) {
                int solved = scanner.nextInt();

                if (solved == 0) {
                    unsolvedTasksCount[t]++;
                } else {
                    solvedTasks[i].add(t);
                }
            }
        }

        Contestant[] ranking = new Contestant[contestants];
        for (int i = 0; i < contestants; i++) {
            int points = 0;

            for (int solvedTaskId : solvedTasks[i]) {
                points += unsolvedTasksCount[solvedTaskId];
            }

            ranking[i] = new Contestant(points, solvedTasks[i].size(), i);
        }

        Arrays.sort(ranking);

        int philipsScore = -1;
        int philipsRanking = -1;

        for (int i = 0; i < ranking.length; i++) {
            if (ranking[i].id == philipsId) {
                philipsScore = ranking[i].score;
                philipsRanking = i + 1;
                break;
            }
        }

        System.out.printf("%d %d\n", philipsScore, philipsRanking);
    }
}
