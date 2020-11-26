package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.*;

/**
 * Created by Rene Argento on 11/11/20.
 */
public class Postscript {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] paper = createBlankPaper();
        Map<Character, Integer[][]> c5InstructionsMap = createC5PrintInstructions();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            List<String> arguments = getRealInput(line.trim());

            if (arguments.get(0).equals("EOP")) {
                printPaper(paper);
                paper = createBlankPaper();
                continue;
            }
            String font = arguments.get(1);
            int row = Integer.parseInt(arguments.get(2)) - 1;

            switch (arguments.get(0)) {
                case "P":
                    int column = Integer.parseInt(arguments.get(3)) - 1;
                    String string = arguments.get(4);
                    print(paper, font, row, column, string, c5InstructionsMap);
                    break;
                case "L":
                    string = arguments.get(3);
                    print(paper, font, row, 0, string, c5InstructionsMap);
                    break;
                case "R":
                    string = arguments.get(3);
                    printRightJustified(paper, font, row, 60, string, c5InstructionsMap);
                    break;
                case "C":
                    string = arguments.get(3);
                    printCentered(paper, font, row, string, c5InstructionsMap);
                    break;
            }
        }
    }

    private static char[][] createBlankPaper() {
        char[][] paper = new char[60][60];
        for (char[] line : paper) {
            Arrays.fill(line, '.');
        }
        return paper;
    }

    private static List<String> getRealInput(String line) {
        List<String> arguments = new ArrayList<>();
        StringBuilder argument = new StringBuilder();

        for (int i = 1; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == '|') {
                for (int j = i + 1; j < line.length(); j++) {
                    char stringCharacter = line.charAt(j);
                    if (stringCharacter == '|') {
                        break;
                    }
                    argument.append(stringCharacter);
                }
                arguments.add(argument.toString());
                argument = new StringBuilder();
                break;
            } else if (character != ' ') {
                argument.append(character);
            } else {
                arguments.add(argument.toString());
                argument = new StringBuilder();
            }
        }
        if (argument.length() != 0) {
            arguments.add(argument.toString());
        }

        return arguments;
    }

    private static void print(char[][] paper, String font, int row, int column, String string,
                              Map<Character, Integer[][]> c5InstructionsMap) {
        int increment = font.equals("C1") ? 1 : 6;
        int columnToPrint = column;

        for (int i = 0; i < string.length(); i++) {
            printCharacter(paper, font, row, columnToPrint, string.charAt(i), c5InstructionsMap);
            columnToPrint += increment;
        }
    }

    private static void printCharacter(char[][] paper, String font, int row, int column, char character,
                                       Map<Character, Integer[][]> c5InstructionsMap) {
        if (font.equals("C1")) {
            if (isValidCell(paper, row, column) && character != ' ') {
                paper[row][column] = character;
            }
        } else {
            printC5Letter(paper, row, column, character, c5InstructionsMap);
        }
    }

    private static void printRightJustified(char[][] paper, String font, int row, int column, String string,
                                            Map<Character, Integer[][]> c5InstructionsMap) {
        int increment = font.equals("C1") ? -1 : -6;
        int columnToPrint = column;

        for (int i = string.length() - 1; i >= 0; i--) {
            columnToPrint += increment;
            printCharacter(paper, font, row, columnToPrint, string.charAt(i), c5InstructionsMap);
        }
    }

    private static void printCentered(char[][] paper, String font, int row, String string,
                                      Map<Character, Integer[][]> c5InstructionsMap) {
        int columnLeft = 30;
        int columnRight = 30;
        if (string.length() % 2 != 0) {
            if (font.equals("C5")) {
                columnRight = 27;
                columnLeft = 27;
            }
        }
        int middle = string.length() / 2;
        String rightToLeftString = string.substring(0, middle);
        String leftToRightString = string.substring(middle);
        print(paper, font, row, columnRight, leftToRightString, c5InstructionsMap);
        printRightJustified(paper, font, row, columnLeft, rightToLeftString, c5InstructionsMap);
    }

    private static void printC5Letter(char[][] paper, int row, int column, char letter,
                                      Map<Character, Integer[][]> c5InstructionsMap) {
        if (!c5InstructionsMap.containsKey(letter)) {
            return;
        }
        Integer[][] instructions = c5InstructionsMap.get(letter);
        for (int i = 0; i < instructions.length; i++) {
            int rowToPrint = row + i;
            for (int j = 0; j < instructions[i].length; j++) {
               int columnToPrint = column + instructions[i][j];
               if (isValidCell(paper, rowToPrint, columnToPrint)) {
                   paper[rowToPrint][columnToPrint] = '*';
               }
            }
        }
    }

    private static void printPaper(char[][] paper) {
        for (int r = 0; r < paper.length; r++) {
            for (int c = 0; c < paper[0].length; c++) {
                System.out.print(paper[r][c]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println();
    }

    private static boolean isValidCell(char[][] paper, int row, int column) {
        return row >= 0 && row < paper.length && column >= 0 && column < paper[0].length;
    }

    private static Map<Character, Integer[][]> createC5PrintInstructions() {
        Map<Character, Integer[][]> c5InstructionsMap = new HashMap<>();
        c5InstructionsMap.put('A', new Integer[][]{{1, 2, 3}, {0, 4}, {0, 1, 2, 3, 4}, {0, 4}, {0, 4}});
        c5InstructionsMap.put('B', new Integer[][]{{0, 1, 2, 3}, {0, 4}, {0, 1, 2, 3}, {0, 4}, {0, 1, 2, 3}});
        c5InstructionsMap.put('C', new Integer[][]{{1, 2, 3, 4}, {0, 4}, {0}, {0}, {1, 2, 3, 4}});
        c5InstructionsMap.put('D', new Integer[][]{{0, 1, 2, 3}, {0, 4}, {0, 4}, {0, 4}, {0, 1, 2, 3}});
        c5InstructionsMap.put('E', new Integer[][]{{0, 1, 2, 3, 4}, {0}, {0, 1, 2}, {0}, {0, 1, 2, 3, 4}});
        c5InstructionsMap.put('F', new Integer[][]{{0, 1, 2, 3, 4}, {0}, {0, 1, 2}, {0}, {0}});
        c5InstructionsMap.put('G', new Integer[][]{{1, 2, 3, 4}, {0}, {0, 3, 4}, {0, 4}, {1, 2, 3}});
        c5InstructionsMap.put('H', new Integer[][]{{0, 4}, {0, 4}, {0, 1, 2, 3, 4}, {0, 4}, {0, 4}});
        c5InstructionsMap.put('I', new Integer[][]{{0, 1, 2, 3, 4}, {2}, {2}, {2}, {0, 1, 2, 3, 4}});
        c5InstructionsMap.put('J', new Integer[][]{{2, 3, 4}, {3}, {3}, {0, 3}, {1, 2}});
        c5InstructionsMap.put('K', new Integer[][]{{0, 4}, {0, 3}, {0, 1, 2}, {0, 3}, {0, 4}});
        c5InstructionsMap.put('L', new Integer[][]{{0}, {0}, {0}, {0}, {0, 1, 2, 3, 4}});
        c5InstructionsMap.put('M', new Integer[][]{{0, 4}, {0, 1, 3, 4}, {0, 2, 4}, {0, 4}, {0, 4}});
        c5InstructionsMap.put('N', new Integer[][]{{0, 4}, {0, 1, 4}, {0, 2, 4}, {0, 3, 4}, {0, 4}});
        c5InstructionsMap.put('O', new Integer[][]{{1, 2, 3}, {0, 4}, {0, 4}, {0, 4}, {1, 2, 3}});
        c5InstructionsMap.put('P', new Integer[][]{{0, 1, 2, 3}, {0, 4}, {0, 1, 2, 3}, {0}, {0}});
        c5InstructionsMap.put('Q', new Integer[][]{{1, 2, 3}, {0, 4}, {0, 4}, {0, 3, 4}, {1, 2, 3, 4}});
        c5InstructionsMap.put('R', new Integer[][]{{0, 1, 2, 3}, {0, 4}, {0, 1, 2, 3}, {0, 3}, {0, 4}});
        c5InstructionsMap.put('S', new Integer[][]{{1, 2, 3, 4}, {0}, {1, 2, 3}, {4}, {0, 1, 2, 3}});
        c5InstructionsMap.put('T', new Integer[][]{{0, 1, 2, 3, 4}, {0, 2, 4}, {2}, {2}, {1, 2, 3}});
        c5InstructionsMap.put('U', new Integer[][]{{0, 4}, {0, 4}, {0, 4}, {0, 4}, {1, 2, 3}});
        c5InstructionsMap.put('V', new Integer[][]{{0, 4}, {0, 4}, {1, 3}, {1, 3}, {2}});
        c5InstructionsMap.put('W', new Integer[][]{{0, 4}, {0, 4}, {0, 2, 4}, {0, 1, 3, 4}, {0, 4}});
        c5InstructionsMap.put('X', new Integer[][]{{0, 4}, {1, 3}, {2}, {1, 3}, {0, 4}});
        c5InstructionsMap.put('Y', new Integer[][]{{0, 4}, {1, 3}, {2}, {2}, {2}});
        c5InstructionsMap.put('Z', new Integer[][]{{0, 1, 2, 3, 4}, {3}, {2}, {1}, {0, 1, 2, 3, 4}});
        return c5InstructionsMap;
    }
}
