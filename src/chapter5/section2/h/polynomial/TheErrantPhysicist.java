package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 19/04/25.
 */
public class TheErrantPhysicist {

    private static class Term {
        int coefficient;
        int xExponent;
        int yExponent;

        public Term(int coefficient, int xExponent, int yExponent) {
            this.coefficient = coefficient;
            this.xExponent = xExponent;
            this.yExponent = yExponent;
        }
    }

    private static class Result {
        String exponents;
        String equation;

        public Result(String exponents, String equation) {
            this.exponents = exponents;
            this.equation = equation;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("#")) {
            String polynomial2 = FastReader.getLine();
            Result result = multiplyPolynomials(line, polynomial2);

            outputWriter.printLine(result.exponents);
            outputWriter.printLine(result.equation);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result multiplyPolynomials(String polynomial1, String polynomial2) {
        List<Term> polynomial1Terms = computeTerms(polynomial1);
        List<Term> polynomial2Terms = computeTerms(polynomial2);

        Term[][] termsResult = multiplyAllTerms(polynomial1Terms, polynomial2Terms);

        StringBuilder exponents = new StringBuilder();
        StringBuilder equation = new StringBuilder();
        boolean isFirstTerm = true;

        for (int x = termsResult.length - 1; x >= 0; x--) {
            for (int y = 0; y < termsResult[x].length; y++) {
                if (termsResult[x][y] != null && termsResult[x][y].coefficient != 0) {
                    Term term = termsResult[x][y];

                    if (term.coefficient < 0) {
                        if (isFirstTerm) {
                            equation.append("-");
                            exponents.append(" ");
                        } else {
                            equation.append(" - ");
                            addSpaces(exponents, 3);
                        }
                    } else if (!isFirstTerm) {
                        equation.append(" + ");
                        addSpaces(exponents, 3);
                    }

                    long absoluteCoefficient = Math.abs(term.coefficient);
                    int coefficientLength = String.valueOf(absoluteCoefficient).length();
                    int xExponentLength = String.valueOf(term.xExponent).length();
                    int yExponentLength = String.valueOf(term.yExponent).length();

                    if (absoluteCoefficient > 1
                            || (term.xExponent == 0 && term.yExponent == 0)) {
                        equation.append(absoluteCoefficient);
                        addSpaces(exponents, coefficientLength);
                    }

                    if (term.xExponent >= 1) {
                        equation.append("x");
                        exponents.append(" ");

                        if (term.xExponent > 1) {
                            addSpaces(equation, xExponentLength);
                            exponents.append(term.xExponent);
                        }
                    }

                    if (term.yExponent >= 1) {
                        equation.append("y");
                        exponents.append(" ");

                        if (term.yExponent > 1) {
                            addSpaces(equation, yExponentLength);
                            exponents.append(term.yExponent);
                        }
                    }

                    if (isFirstTerm) {
                        isFirstTerm = false;
                    }
                }
            }
        }
        return new Result(exponents.toString(), equation.toString());
    }

    private static void addSpaces(StringBuilder string, int spaces) {
        for (int i = 0; i < spaces; i++) {
            string.append(" ");
        }
    }

    private static List<Term> computeTerms(String polynomial) {
        List<Term> terms = new ArrayList<>();

        polynomial += "+";
        StringBuilder term = new StringBuilder();
        for (int i = 0; i < polynomial.length(); i++) {
            char symbol = polynomial.charAt(i);

            if (symbol == '+' || symbol == '-') {
                if (term.length() > 0) {
                    terms.add(getTerm(term.toString()));
                }

                term = new StringBuilder();
                if (symbol == '-') {
                    term.append(symbol);
                }
            } else {
                term.append(symbol);
            }
        }
        return terms;
    }

    private static Term getTerm(String termString) {
        boolean isNegative = termString.charAt(0) == '-';
        termString += "*";
        int coefficient = 1;
        int xExponent = 0;
        int yExponent = 0;

        StringBuilder currentValue = new StringBuilder();
        boolean isCoefficient = true;
        char currentVariable = '*';
        for (int i = 0; i < termString.length(); i++) {
            char symbol = termString.charAt(i);
            if (symbol == '-') {
                continue;
            }

            if (symbol == 'x' || symbol == 'y' || symbol == '*') {
                int value = 1;

                if (currentValue.length() > 0) {
                    value = Integer.parseInt(currentValue.toString());
                }

                if (isCoefficient) {
                    coefficient = value;
                    if (isNegative) {
                        coefficient *= -1;
                    }
                    isCoefficient = false;
                } else {
                    if (currentVariable == 'x') {
                        xExponent = value;
                    } else {
                        yExponent = value;
                    }
                }
                currentVariable = symbol;
                currentValue = new StringBuilder();
            } else {
                currentValue.append(symbol);
            }
        }
        return new Term(coefficient, xExponent, yExponent);
    }

    private static Term[][] multiplyAllTerms(List<Term> polynomial1Terms, List<Term> polynomial2Terms) {
        Term[][] termsResult = new Term[201][201];

        for (Term polynomial1Term : polynomial1Terms) {
            for (Term polynomial2Term : polynomial2Terms) {
                int coefficient = polynomial1Term.coefficient * polynomial2Term.coefficient;
                int xExponent = polynomial1Term.xExponent + polynomial2Term.xExponent;
                int yExponent = polynomial1Term.yExponent + polynomial2Term.yExponent;

                if (termsResult[xExponent][yExponent] != null) {
                    termsResult[xExponent][yExponent].coefficient += coefficient;
                } else {
                    termsResult[xExponent][yExponent] = new Term(coefficient, xExponent, yExponent);
                }
            }
        }
        return termsResult;
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
