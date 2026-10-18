package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Rene Argento on 16/10/2026.
 */
public class MakePalindromeChecker {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String result = evaluateExpression(line);
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String evaluateExpression(String expression) {
        if (!isValidExpression(expression)) {
            return "FFFFFFF The solution is not accepted";
        }

        String[] data = expression.split(" ");
        String inputString = data[0];
        int integer = Integer.parseInt(data[1]);
        String outputString = data[2];

        char secondCondition = checkSecondCondition(outputString);
        char thirdCondition = checkThirdCondition(inputString, outputString);
        char fourthCondition = checkFourthCondition(inputString, outputString);
        char fifthCondition = checkFifthCondition(inputString, outputString);
        char sixthCondition = checkSixthCondition(inputString, integer, outputString);
        char seventhCondition = checkSeventhCondition(inputString, integer);

        String result = "T" + secondCondition + thirdCondition + fourthCondition + fifthCondition + sixthCondition
                + seventhCondition;
        String solutionText = result.contains("F") ? "The solution is not accepted" : "The solution is accepted";
        return result + " " + solutionText;
    }

    private static boolean isValidExpression(String expression) {
        Pattern pattern = Pattern.compile("([a-z]{1,1000}) (\\d{1,4}) ([a-z]{1,2000})");
        return pattern.matcher(expression).matches();
    }

    private static char checkSecondCondition(String string) {
        for (int i = 0; i < string.length() / 2; i++) {
            int endIndex = string.length() - i - 1;
            if (string.charAt(i) != string.charAt(endIndex)) {
                return 'F';
            }
        }
        return 'T';
    }

    private static char checkThirdCondition(String inputString, String outputString) {
        Set<Character> outputCharactersSet = new HashSet<>();
        for (char character : outputString.toCharArray()) {
            outputCharactersSet.add(character);
        }

        for (char character : inputString.toCharArray()) {
            if (!outputCharactersSet.contains(character)) {
                return 'F';
            }
        }
        return 'T';
    }

    private static char checkFourthCondition(String inputString, String outputString) {
        int[] frequenciesInput = computeFrequencies(inputString);
        int[] frequenciesOutput = computeFrequencies(outputString);

        for (int i = 0; i < frequenciesInput.length; i++) {
            if (frequenciesInput[i] > frequenciesOutput[i]) {
                return 'F';
            }
        }
        return 'T';
    }

    private static char checkFifthCondition(String inputString, String outputString) {
        int inputStringIndex = 0;

        for (int i = 0; i < outputString.length(); i++) {
            if (inputStringIndex == inputString.length()) {
                return 'T';
            }
            if (outputString.charAt(i) == inputString.charAt(inputStringIndex)) {
                inputStringIndex++;
            }
        }
        return inputStringIndex == inputString.length() ? 'T' : 'F';
    }

    private static char checkSixthCondition(String inputString, int integer, String outputString) {
        return outputString.length() == inputString.length() + integer ? 'T' : 'F';
    }

    private static char checkSeventhCondition(String inputString, int integer) {
        return integer < inputString.length() ? 'T' : 'F';
    }

    private static int[] computeFrequencies(String string) {
        int[] frequencies = new int[26];
        for (int i = 0; i < string.length(); i++) {
            frequencies[string.charAt(i) - 'a']++;
        }
        return frequencies;
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

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}