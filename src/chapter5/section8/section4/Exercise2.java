package chapter5.section8.section4;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 13/04/26.
 */
public class Exercise2 {

    public static void main(String[] args) {
        BigInteger binomialCoefficient1 = binomialCoefficient(BigInteger.valueOf(4), 2);
        System.out.println("Result:   " + binomialCoefficient1);
        System.out.println("Expected: 6");
        System.out.println();

        BigInteger binomialCoefficient2 = binomialCoefficient(BigInteger.valueOf(133), 71);
        System.out.println("Result:   " + binomialCoefficient2);
        System.out.println("Expected: 555687036928510235891585199545206017600");
    }

    // O(k^2 * lg(n))
    private static BigInteger binomialCoefficient(BigInteger n, int k) {
        if (BigInteger.valueOf(k).compareTo(n) > 0) {
            return BigInteger.ZERO;
        }
        if (BigInteger.valueOf(k).equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        BigInteger[][] transitionMatrix = buildTransitionMatrix(k);
        BigInteger[][] exponentialMatrix = fastMatrixExponentiation(transitionMatrix, n);
        return exponentialMatrix[k][0];
    }

    private static BigInteger[][] buildTransitionMatrix(int k) {
        BigInteger[][] transitionMatrix = new BigInteger[k + 1][k + 1];
        for (int row = 0; row < transitionMatrix.length; row++) {
            for (int column = 0; column < transitionMatrix[0].length; column++) {
                if (row == column
                        || column == (row - 1)) {
                    transitionMatrix[row][column] = BigInteger.ONE;
                } else {
                    transitionMatrix[row][column] = BigInteger.ZERO;
                }
            }
        }
        return transitionMatrix;
    }

    private static BigInteger[][] fastMatrixExponentiation(BigInteger[][] matrix, BigInteger exponent) {
        BigInteger[][] identityMatrix = new BigInteger[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                identityMatrix[i][j] = (i == j) ? BigInteger.ONE : BigInteger.ZERO;
            }
        }

        BigInteger[][] result = identityMatrix;
        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.testBit(0)) {
                result = matrixMultiplication(result, matrix);
            }
            matrix = matrixMultiplication(matrix, matrix);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    private static BigInteger[][] matrixMultiplication(BigInteger[][] matrix1, BigInteger[][] matrix2) {
        BigInteger[][] result = new BigInteger[matrix1.length][matrix2[0].length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j <= i; j++) { // lower triangle only
                result[i][j] = BigInteger.ZERO;
                for (int k = j; k <= i; k++) {
                    result[i][j] = result[i][j].add((matrix1[i][k].multiply(matrix2[k][j])));
                }
            }
        }
        return result;
    }
}
