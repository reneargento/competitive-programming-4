package chapter2.section2.k.special.stack.based.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 23/03/21.
 */
public class NestingABunchOfBrackets {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String expression = FastReader.getLine();

        while (expression != null) {
            int errorPosition = checkExpression(expression);
            if (errorPosition < 0) {
                System.out.println("YES");
            } else {
                System.out.printf("NO %d\n", errorPosition);
            }

            expression = FastReader.getLine();
        }
    }

    private static int checkExpression(String expression) {
        Deque<Character> stack = new ArrayDeque<>();
        int specialCharacters = 0;

        for (int i = 0; i < expression.length(); i++) {
            char character = expression.charAt(i);

            if (character == '[' || character == '{' || character == '<') {
                stack.push(character);
            } else if (character == '(') {
                if (i != expression.length() - 1 && expression.charAt(i + 1) == '*') {
                    stack.push('*');
                    i++;
                    specialCharacters++;
                } else {
                    stack.push(character);
                }
            } else if (character == ']') {
                if (!stack.isEmpty() && stack.peek() == '[') {
                    stack.pop();
                } else {
                    return (i + 1) - specialCharacters;
                }
            } else if (character == '}') {
                if (!stack.isEmpty() && stack.peek() == '{') {
                    stack.pop();
                } else {
                    return (i + 1) - specialCharacters;
                }
            } else if (character == '>') {
                if (!stack.isEmpty() && stack.peek() == '<') {
                    stack.pop();
                } else {
                    return (i + 1) - specialCharacters;
                }
            }  else if (character == ')') {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    return (i + 1) - specialCharacters;
                }
            } else if (character == '*'
                    && i != expression.length() - 1
                    && expression.charAt(i + 1) == ')') {
                if (!stack.isEmpty() && stack.peek() == '*') {
                    stack.pop();
                    i++;
                    specialCharacters++;
                } else {
                    return (i + 1) - specialCharacters;
                }
            }
        }
        if (!stack.isEmpty()) {
            return (expression.length() + 1) - specialCharacters;
        }
        return -1;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
