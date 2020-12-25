package chapter1.section6.p.time.waster.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/12/20.
 */
public class AutomatedJudgeScript {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int runNumber = 1;

        int linesInSolution = scanner.nextInt();
        while (linesInSolution != 0) {
            scanner.nextLine();
            StringBuilder allSolution = new StringBuilder();
            StringBuilder digitsInSolution = new StringBuilder();
            readLines(allSolution, digitsInSolution, linesInSolution, scanner);

            int linesInTeamOutput = scanner.nextInt();
            scanner.nextLine();
            StringBuilder teamSolution = new StringBuilder();
            StringBuilder digitsInTeamOutput = new StringBuilder();
            readLines(teamSolution, digitsInTeamOutput, linesInTeamOutput, scanner);

            System.out.printf("Run #%d: ", runNumber);
            if (allSolution.toString().equals(teamSolution.toString())) {
                System.out.println("Accepted");
            } else if (digitsInSolution.toString().equals(digitsInTeamOutput.toString())) {
                System.out.println("Presentation Error");
            } else {
                System.out.println("Wrong Answer");
            }

            runNumber++;
            linesInSolution = scanner.nextInt();
        }
    }

    private static void readLines(StringBuilder allCharacters, StringBuilder digits, int lines, Scanner scanner) {
        for (int i = 0; i < lines; i++) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                allCharacters.append("\n");
            }

            for (char symbol : line.toCharArray()) {
                allCharacters.append(symbol);
                if (Character.isDigit(symbol)) {
                    digits.append(symbol);
                }
            }
            allCharacters.append("BREAKLINE");
        }
    }
}
