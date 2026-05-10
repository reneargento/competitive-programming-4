package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/05/26.
 */
public class SelectGroup {

    private static class Result {
        Set<String> set;
        int endIndex;

        public Result(Set<String> set, int endIndex) {
            this.set = set;
            this.endIndex = endIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Set<String>> groups = new HashMap<>();

        String selectionExpression = FastReader.getLine();
        while (selectionExpression != null) {
            String[] tokens = selectionExpression.split(" ");
            processExpression(outputWriter, groups, tokens);
            selectionExpression = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void processExpression(OutputWriter outputWriter, Map<String, Set<String>> groups, String[] tokens) {
        String type = tokens[0];

        if (type.equals("group")) {
            String name = tokens[1];
            int individuals = Integer.parseInt(tokens[2]);
            Set<String> individualsSet = new HashSet<>(Arrays.asList(tokens).subList(3, individuals + 3));
            groups.put(name, individualsSet);
        } else {
            Result result = processExpression(groups, tokens, 0);
            if (result.set.isEmpty()) {
                outputWriter.printLine();
                return;
            }
            List<String> sortedNames = new ArrayList<>(result.set);
            Collections.sort(sortedNames);

            outputWriter.print(sortedNames.get(0));
            for (int i = 1; i < sortedNames.size(); i++) {
                outputWriter.print(" " + sortedNames.get(i));
            }
            outputWriter.printLine();
        }
    }

    private static Result processExpression(Map<String, Set<String>> groups, String[] tokens, int index) {
        String token = tokens[index];
        if (token.equals("union") || token.equals("intersection") || token.equals("difference")) {
            Result result1 = processExpression(groups, tokens, index + 1);
            Result result2 = processExpression(groups, tokens, result1.endIndex + 1);
            Set<String> resultSet = new HashSet<>();

            if (token.equals("union")) {
                resultSet.addAll(result1.set);
                resultSet.addAll(result2.set);
            } else if (token.equals("intersection")) {
                for (String name : result1.set) {
                    if (result2.set.contains(name)) {
                        resultSet.add(name);
                    }
                }
            } else {
                for (String name : result1.set) {
                    if (!result2.set.contains(name)) {
                        resultSet.add(name);
                    }
                }
            }
            return new Result(resultSet, result2.endIndex);
        } else {
            Set<String> set = groups.get(token);
            return new Result(set, index);
        }
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
