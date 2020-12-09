package chapter1.section6.m.input.parsing.iterative;

import java.util.*;

/**
 * Created by Rene Argento on 07/12/20.
 */
public class MatrixChainMultiplication {

    private static class Matrix {
        int rows;
        int columns;

        public Matrix(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
        }

        Matrix multiply(Matrix otherMatrix) {
            if (columns != otherMatrix.rows) {
                return null;
            }
            return new Matrix(rows, otherMatrix.columns);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Matrix> matrixMap = new HashMap<>();

        int matrices = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < matrices; i++) {
            String[] matrixData = scanner.nextLine().split(" ");
            char id = matrixData[0].charAt(0);
            int rows = Integer.parseInt(matrixData[1]);
            int columns = Integer.parseInt(matrixData[2]);
            matrixMap.put(id, new Matrix(rows, columns));
        }

        while (scanner.hasNext()) {
            long multiplications = countMultiplications(scanner.nextLine(), matrixMap);
            if (multiplications == -1) {
                System.out.println("error");
            } else {
                System.out.println(multiplications);
            }
        }
    }

    private static long countMultiplications(String expression, Map<Character, Matrix> matrixMap) {
        Deque<Matrix> stack = new ArrayDeque<>();
        long multiplications = 0;

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (Character.isLetter(symbol)) {
                stack.push(matrixMap.get(symbol));
            } else if (symbol == ')') {
                Matrix matrix2 = stack.pop();
                Matrix matrix1 = stack.pop();

                Matrix resultMatrix = matrix1.multiply(matrix2);
                if (resultMatrix == null) {
                    return -1;
                }
                stack.push(resultMatrix);
                multiplications += matrix1.rows * matrix1.columns * matrix2.columns;
            }
        }
        return multiplications;
    }
}