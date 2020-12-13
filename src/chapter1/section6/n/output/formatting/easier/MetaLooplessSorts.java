package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 12/12/20.
 */
public class MetaLooplessSorts {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int programs = scanner.nextInt();

        for (int p = 0; p < programs; p++) {
            if (p > 0) {
                outputWriter.printLine();
            }

            int variables = scanner.nextInt();
            printPascalSortingProgram(variables, outputWriter);
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void printPascalSortingProgram(int variables, OutputWriter outputWriter) {
        printHeader(variables, outputWriter);
        printComparisons(variables, outputWriter);
        outputWriter.printLine("end.");
    }

    private static void printHeader(int variables, OutputWriter outputWriter) {
        outputWriter.printLine("program sort(input,output);");
        outputWriter.printLine("var");

        StringJoiner variablesDeclaration = new StringJoiner(",");
        for (int i = 0; i < variables; i++) {
            variablesDeclaration.add(String.valueOf(getVariableName(i)));
        }
        String variablesDeclarationString = variablesDeclaration.toString();

        outputWriter.printLine(variablesDeclarationString + " : integer;");
        outputWriter.printLine("begin");
        outputWriter.printLine("  readln(" + variablesDeclarationString  + ");");
    }

    private static void printComparisons(int variables, OutputWriter outputWriter) {
        StringBuilder variablesString = new StringBuilder();
        for (int i = 0; i < variables; i++) {
            variablesString.append(getVariableName(i));
        }
        printComparisons(variables, variablesString.toString(), outputWriter, 1, "  ");
    }

    private static void printComparisons(int variables, String variablesString, OutputWriter outputWriter,
                                         int variableId, String prefix) {
        if (variableId == variables) {
            writeSortedVariables(variablesString, prefix, outputWriter);
            return;
        }

        char variableName = getVariableName(variableId);

        for (int i = variableId - 1; i >= -1; i--) {
            int variableIndex = Math.max(i, 0);
            char comparisonVariable = variablesString.charAt(variableIndex);

            outputWriter.print(prefix);
            if (i == variableId - 1) {
                outputWriter.printLine("if " + comparisonVariable + " < " + variableName + " then");
                variablesString = moveVariables(variablesString, comparisonVariable, variableName);
                printComparisons(variables, variablesString, outputWriter, variableId + 1, prefix + "  ");
            } else {
                for (int j = variableId - 1; j > i; j--) {
                    char previousComparisonVariable = variablesString.charAt(j);
                    variablesString = moveVariables(variablesString, variableName, previousComparisonVariable);
                }

                if (i != -1) {
                    outputWriter.printLine("else if " + comparisonVariable + " < " + variableName + " then");
                    variablesString = moveVariables(variablesString, comparisonVariable, variableName);
                    printComparisons(variables, variablesString, outputWriter, variableId + 1, prefix + "  ");
                } else {
                    outputWriter.printLine("else");
                    variablesString = moveVariables(variablesString, variableName, comparisonVariable);
                    printComparisons(variables, variablesString, outputWriter, variableId + 1, prefix + "  ");
                }
            }
        }
    }

    private static String moveVariables(String variablesString, char lessVariable, char moreVariable) {
        int indexOfLess = variablesString.indexOf(lessVariable);
        int indexOfMore = variablesString.indexOf(moreVariable);

        if (indexOfLess < indexOfMore) {
            return variablesString;
        }

        variablesString = variablesString.replaceAll(String.valueOf(lessVariable), "");
        return variablesString.substring(0, indexOfMore) + lessVariable + variablesString.substring(indexOfMore);
    }

    private static void writeSortedVariables(String variablesString, String prefix, OutputWriter outputWriter) {
        StringJoiner variablesDeclaration = new StringJoiner(",");
        for (char variable : variablesString.toCharArray()) {
            variablesDeclaration.add(String.valueOf(variable));
        }
        outputWriter.printLine(prefix + "writeln(" + variablesDeclaration.toString() + ")");
    }

    private static char getVariableName(int index) {
        return (char) ('a' + index);
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
