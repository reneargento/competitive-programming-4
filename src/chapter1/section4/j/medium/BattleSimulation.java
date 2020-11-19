package chapter1.section4.j.medium;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 23/09/20.
 */
public class BattleSimulation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String monsterMoves = scanner.next();
        StringBuilder moves = new StringBuilder();

        for (int i = 0; i < monsterMoves.length(); i++) {

            if (i < monsterMoves.length() - 2) {
                Set<Character> uniqueMoves = new HashSet<>();
                uniqueMoves.add(monsterMoves.charAt(i));
                uniqueMoves.add(monsterMoves.charAt(i + 1));
                uniqueMoves.add(monsterMoves.charAt(i + 2));

                if (uniqueMoves.size() == 3) {
                    moves.append('C');
                    i += 2;
                    continue;
                }
            }

            switch (monsterMoves.charAt(i)) {
                case 'R':
                    moves.append('S');
                    break;
                case 'B':
                    moves.append('K');
                    break;
                case 'L':
                    moves.append('H');
                    break;
            }
        }

        System.out.println(moves);
    }

}
