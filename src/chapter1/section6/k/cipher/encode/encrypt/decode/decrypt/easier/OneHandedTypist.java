package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 25/11/20.
 */
public class OneHandedTypist {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Character> qwertyToDvorakMap = createQwertyToDvorakMap();

        while (scanner.hasNext()) {
            String text = scanner.nextLine();
            for (char character : text.toCharArray()) {
                System.out.print(qwertyToDvorakMap.get(character));
            }
            System.out.println();
        }
    }

    private static Map<Character, Character> createQwertyToDvorakMap() {
        Map<Character, Character> qwertyToDvorakMap = new HashMap<>();
        qwertyToDvorakMap.put('`', '`');
        qwertyToDvorakMap.put('1', '1');
        qwertyToDvorakMap.put('2', '2');
        qwertyToDvorakMap.put('3', '3');
        qwertyToDvorakMap.put('4', 'q');
        qwertyToDvorakMap.put('5', 'j');
        qwertyToDvorakMap.put('6', 'l');
        qwertyToDvorakMap.put('7', 'm');
        qwertyToDvorakMap.put('8', 'f');
        qwertyToDvorakMap.put('9', 'p');
        qwertyToDvorakMap.put('0', '/');
        qwertyToDvorakMap.put('-', '[');
        qwertyToDvorakMap.put('=', ']');
        qwertyToDvorakMap.put('q', '4');
        qwertyToDvorakMap.put('w', '5');
        qwertyToDvorakMap.put('e', '6');
        qwertyToDvorakMap.put('r', '.');
        qwertyToDvorakMap.put('t', 'o');
        qwertyToDvorakMap.put('y', 'r');
        qwertyToDvorakMap.put('u', 's');
        qwertyToDvorakMap.put('i', 'u');
        qwertyToDvorakMap.put('o', 'y');
        qwertyToDvorakMap.put('p', 'b');
        qwertyToDvorakMap.put('[', ';');
        qwertyToDvorakMap.put(']', '=');
        qwertyToDvorakMap.put('\\', '\\');
        qwertyToDvorakMap.put('a', '7');
        qwertyToDvorakMap.put('s', '8');
        qwertyToDvorakMap.put('d', '9');
        qwertyToDvorakMap.put('f', 'a');
        qwertyToDvorakMap.put('g', 'e');
        qwertyToDvorakMap.put('h', 'h');
        qwertyToDvorakMap.put('j', 't');
        qwertyToDvorakMap.put('k', 'd');
        qwertyToDvorakMap.put('l', 'c');
        qwertyToDvorakMap.put(';', 'k');
       qwertyToDvorakMap.put('\'', '-');
        qwertyToDvorakMap.put('z', '0');
        qwertyToDvorakMap.put('x', 'z');
        qwertyToDvorakMap.put('c', 'x');
        qwertyToDvorakMap.put('v', ',');
        qwertyToDvorakMap.put('b', 'i');
        qwertyToDvorakMap.put('n', 'n');
        qwertyToDvorakMap.put('m', 'w');
        qwertyToDvorakMap.put(',', 'v');
        qwertyToDvorakMap.put('.', 'g');
        qwertyToDvorakMap.put('/', '\'');
        qwertyToDvorakMap.put('~', '~');
        qwertyToDvorakMap.put('!', '!');
        qwertyToDvorakMap.put('@', '@');
        qwertyToDvorakMap.put('#', '#');
        qwertyToDvorakMap.put('$', 'Q');
        qwertyToDvorakMap.put('%', 'J');
        qwertyToDvorakMap.put('^', 'L');
        qwertyToDvorakMap.put('&', 'M');
        qwertyToDvorakMap.put('*', 'F');
        qwertyToDvorakMap.put('(', 'P');
        qwertyToDvorakMap.put(')', '?');
        qwertyToDvorakMap.put('_', '{');
        qwertyToDvorakMap.put('+', '}');
        qwertyToDvorakMap.put('Q', '$');
        qwertyToDvorakMap.put('W', '%');
        qwertyToDvorakMap.put('E', '^');
        qwertyToDvorakMap.put('R', '>');
        qwertyToDvorakMap.put('T', 'O');
        qwertyToDvorakMap.put('Y', 'R');
        qwertyToDvorakMap.put('U', 'S');
        qwertyToDvorakMap.put('I', 'U');
        qwertyToDvorakMap.put('O', 'Y');
        qwertyToDvorakMap.put('P', 'B');
        qwertyToDvorakMap.put('{', ':');
        qwertyToDvorakMap.put('}', '+');
        qwertyToDvorakMap.put('|', '|');
        qwertyToDvorakMap.put('A', '&');
        qwertyToDvorakMap.put('S', '*');
        qwertyToDvorakMap.put('D', '(');
        qwertyToDvorakMap.put('F', 'A');
        qwertyToDvorakMap.put('G', 'E');
        qwertyToDvorakMap.put('H', 'H');
        qwertyToDvorakMap.put('J', 'T');
        qwertyToDvorakMap.put('K', 'D');
        qwertyToDvorakMap.put('L', 'C');
        qwertyToDvorakMap.put(':', 'K');
        qwertyToDvorakMap.put('"', '_');
        qwertyToDvorakMap.put('Z', ')');
        qwertyToDvorakMap.put('X', 'Z');
        qwertyToDvorakMap.put('C', 'X');
        qwertyToDvorakMap.put('V', '<');
        qwertyToDvorakMap.put('B', 'I');
        qwertyToDvorakMap.put('N', 'N');
        qwertyToDvorakMap.put('M', 'W');
        qwertyToDvorakMap.put('<', 'V');
        qwertyToDvorakMap.put('>', 'G');
        qwertyToDvorakMap.put('?', '"');
        qwertyToDvorakMap.put(' ', ' ');
        return qwertyToDvorakMap;
    }
}
