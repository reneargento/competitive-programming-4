package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 03/05/26.
 */
public class LoglanALogicalLanguage {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("#")) {
            line = line.trim();
            StringBuilder sentence = new StringBuilder(line);
            while (sentence.charAt(sentence.length() - 1) != '.') {
                sentence.append(" ");
                line = FastReader.getLine().trim();
                sentence.append(line);
            }

            String result = checkSentence(sentence.toString());
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String checkSentence(String sentence) {
        sentence = sentence.substring(0, sentence.length() - 1);
        List<String> tokensList = getTokens(sentence);
        String[] tokens = tokensList.toArray(new String[tokensList.size()]);
        return checkSentence(tokens);
    }

    private static String checkSentence(String[] tokens) {
        if (tokens.length == 0) {
            return "Bad!";
        }
        boolean isStatement = parseStatement(tokens, 0) == tokens.length;
        boolean isPredclaim = parsePredClaim(tokens, 0) == tokens.length;
        return isStatement || isPredclaim ? "Good" : "Bad!";
    }

    private static int parseStatement(String[] tokens, int index) {
        int predNameResult = parsePredName(tokens, index);
        if (predNameResult == -1) {
            return -1;
        }
        int verbPredResult = parseVerbPred(tokens, predNameResult);
        if (verbPredResult == -1) {
            return -1;
        }
        if (verbPredResult == tokens.length) {
            return verbPredResult;
        }
        return parsePredName(tokens, verbPredResult);
    }

    private static int parsePredClaim(String[] tokens, int index) {
        if (isDA(tokens[index])) {
            return parsePreds(tokens, index + 1);
        } else {
            int predNameResult = parsePredName(tokens, index);
            if (predNameResult == -1
                    || predNameResult >= tokens.length - 1) {
                return -1;
            }
            if (isBA(tokens[predNameResult])) {
                return parsePreds(tokens, predNameResult + 1);
            } else {
                return -1;
            }
        }
    }

    private static int parsePredName(String[] tokens, int index) {
        if (index >= tokens.length) {
            return -1;
        }
        if (isName(tokens[index])) {
            return index + 1;
        } else {
            if (!isLA(tokens[index])) {
                return -1;
            }
            return parsePredString(tokens, index + 1);
        }
    }

    private static int parseVerbPred(String[] tokens, int index) {
        if (index >= tokens.length || !isMOD(tokens[index])) {
            return -1;
        }
        return parsePredString(tokens, index + 1);
    }

    private static int parsePredString(String[] tokens, int index) {
        if (index >= tokens.length || !isPredicate(tokens[index])) {
            return -1;
        }

        int result = parsePredString(tokens, index + 1);
        if (result != -1) {
            return result;
        }
        return index + 1;
    }

    private static int parsePreds(String[] tokens, int index) {
        int result = parsePredString(tokens, index);
        if (result == -1) {
            return -1;
        }

        while (result < tokens.length && isA(tokens[result])) {
            result = parsePreds(tokens, result + 1);
            if (result == -1) {
                return -1;
            }
        }
        return result;
    }

    private static boolean isLA(String token) {
        return token.equals("la") || token.equals("le") || token.equals("li") || token.equals("lo") || token.equals("lu");
    }

    private static boolean isBA(String token) {
        return token.equals("ba") || token.equals("be") || token.equals("bi") || token.equals("bo") || token.equals("bu");
    }

    private static boolean isDA(String token) {
        return token.equals("da") || token.equals("de") || token.equals("di") || token.equals("do") || token.equals("du");
    }

    private static boolean isMOD(String token) {
        return token.equals("ga") || token.equals("ge") || token.equals("gi") || token.equals("go") || token.equals("gu");
    }

    private static boolean isA(String token) {
        return token.equals("a") || token.equals("e") || token.equals("i") || token.equals("o") || token.equals("u");
    }

    private static boolean isPredicate(String token) {
        if (token.length() != 5) {
            return false;
        }
        boolean isFormat1Valid =
                isConsonant(token.charAt(0))
                        && isConsonant(token.charAt(1))
                        && !isConsonant(token.charAt(2))
                        && isConsonant(token.charAt(3))
                        && !isConsonant(token.charAt(4));
        boolean isFormat2Valid =
                isConsonant(token.charAt(0))
                        && !isConsonant(token.charAt(1))
                        && isConsonant(token.charAt(2))
                        && isConsonant(token.charAt(3))
                        && !isConsonant(token.charAt(4));
        return isFormat1Valid || isFormat2Valid;
    }

    private static boolean isName(String token) {
        char symbol = token.charAt(token.length() - 1);
        return isConsonant(symbol);
    }

    private static boolean isConsonant(char symbol) {
        return symbol != 'a' && symbol != 'e' && symbol != 'i' && symbol != 'o' && symbol != 'u';
    }

    private static List<String> getTokens(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token = new StringBuilder();
                }
            } else {
                token.append(character);
            }
        }
        if (token.length() > 0) {
            tokens.add(token.toString());
        }
        return tokens;
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
