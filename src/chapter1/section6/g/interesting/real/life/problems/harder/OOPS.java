package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 11/11/20.
 */
public class OOPS {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String codeComplement = "";
        int[] instructionSizes = {8, 8, 8, 8, 8, 4, 4, 4, 4, 4, 4, 4, 12, 12, 12, 4};

        while (scanner.hasNext() || !codeComplement.equals("")) {
            StringBuilder code = new StringBuilder(codeComplement);
            if (scanner.hasNext()) {
                code.append(scanner.nextLine());
            }

            codeComplement = "";

            for (int i = 0; i < code.length(); i++) {
                char character = code.charAt(i);
                int decimalIndex = Integer.valueOf(String.valueOf(character), 16);
                int endIndex = i + instructionSizes[decimalIndex];

                if (endIndex >= code.length()) {
                    int charactersNeededFromOtherLine = endIndex - code.length() + 1;
                    String nextLine = scanner.nextLine();
                    code.append(nextLine, 0, charactersNeededFromOtherLine);
                    codeComplement = nextLine.substring(charactersNeededFromOtherLine);
                }

                switch (character) {
                    case '0': printAdd(code.toString(), i + 1); break;
                    case '1': printSub(code.toString(), i + 1); break;
                    case '2': printMul(code.toString(), i + 1); break;
                    case '3': printDiv(code.toString(), i + 1); break;
                    case '4': printMov(code.toString(), i + 1); break;
                    case '5': printBreq(code.toString(), i + 1); break;
                    case '6': printBrle(code.toString(), i + 1); break;
                    case '7': printBrls(code.toString(), i + 1); break;
                    case '8': printBrge(code.toString(), i + 1); break;
                    case '9': printBrgr(code.toString(), i + 1); break;
                    case 'A': printBrne(code.toString(), i + 1); break;
                    case 'B': printBr(code.toString(), i + 1); break;
                    case 'C': printAnd(code.toString(), i + 1); break;
                    case 'D': printOr(code.toString(), i + 1); break;
                    case 'E': printXor(code.toString(), i + 1); break;
                    case 'F': printNot(code.toString(), i + 1); break;
                }
                i = endIndex;
            }
        }
    }

    private static String getOperand(String code, int index) {
        StringBuilder codeHexadecimal = new StringBuilder();
        for (int i = index; i < index + 4; i++) {
            codeHexadecimal.append(code.charAt(i));
        }
        int decimal = Integer.parseInt(codeHexadecimal.toString(), 16);
        String bits = Integer.toBinaryString(decimal);
        if (bits.length() != 16) {
            bits = appendZeros(bits);
        }
        String operandBits = bits.substring(0, 2);
        String operand = "";

        if (operandBits.equals("00")) {
            operand += "R";
        } else if (operandBits.equals("01")) {
            operand += "$";
        } else if (operandBits.equals("10")) {
            operand += "PC+";
        }
        int value = Integer.parseInt(bits.substring(2), 2);
        return operand + value;
    }

    private static String appendZeros(String bits) {
        int missingBits = 16 - bits.length();
        StringBuilder leftBits = new StringBuilder();

        for (int i = 0; i < missingBits; i++) {
            leftBits.append('0');
        }
        return leftBits.toString() + bits;
    }

    private static List<String> getOperands(String code, int operandsCount, int index) {
        List<String> operands = new ArrayList<>();
        for (int i = 0; i < operandsCount; i++) {
            int currentIndex = index + i * 4;
            String operand = getOperand(code, currentIndex);
            operands.add(operand);
        }
        return operands;
    }

    private static void printAdd(String code, int index) {
        List<String> operands = getOperands(code, 2, index);
        System.out.printf("ADD %s,%s\n", operands.get(0), operands.get(1));
    }

    private static void printSub(String code, int index) {
        List<String> operands = getOperands(code, 2, index);
        System.out.printf("SUB %s,%s\n", operands.get(0), operands.get(1));
    }

    private static void printMul(String code, int index) {
        List<String> operands = getOperands(code, 2, index);
        System.out.printf("MUL %s,%s\n", operands.get(0), operands.get(1));
    }

    private static void printDiv(String code, int index) {
        List<String> operands = getOperands(code, 2, index);
        System.out.printf("DIV %s,%s\n", operands.get(0), operands.get(1));
    }

    private static void printMov(String code, int index) {
        List<String> operands = getOperands(code, 2, index);
        System.out.printf("MOV %s,%s\n", operands.get(0), operands.get(1));
    }

    private static void printBreq(String code, int index) {
        List<String> operands = getOperands(code, 1, index);
        System.out.printf("BREQ %s\n", operands.get(0));
    }

    private static void printBrle(String code, int index) {
        List<String> operands = getOperands(code, 1, index);
        System.out.printf("BRLE %s\n", operands.get(0));
    }

    private static void printBrls(String code, int index) {
        List<String> operands = getOperands(code, 1, index);
        System.out.printf("BRLS %s\n", operands.get(0));
    }

    private static void printBrge(String code, int index) {
        List<String> operands = getOperands(code, 1, index);
        System.out.printf("BRGE %s\n", operands.get(0));
    }

    private static void printBrgr(String code, int index) {
        List<String> operands = getOperands(code, 1, index);
        System.out.printf("BRGR %s\n", operands.get(0));
    }

    private static void printBrne(String code, int index) {
        List<String> operands = getOperands(code, 1, index);
        System.out.printf("BRNE %s\n", operands.get(0));
    }

    private static void printBr(String code, int index) {
        List<String> operands = getOperands(code, 1, index);
        System.out.printf("BR %s\n", operands.get(0));
    }

    private static void printAnd(String code, int index) {
        List<String> operands = getOperands(code, 3, index);
        System.out.printf("AND %s,%s,%s\n", operands.get(0), operands.get(1), operands.get(2));
    }

    private static void printOr(String code, int index) {
        List<String> operands = getOperands(code, 3, index);
        System.out.printf("OR %s,%s,%s\n", operands.get(0), operands.get(1), operands.get(2));
    }

    private static void printXor(String code, int index) {
        List<String> operands = getOperands(code, 3, index);
        System.out.printf("XOR %s,%s,%s\n", operands.get(0), operands.get(1), operands.get(2));
    }

    private static void printNot(String code, int index) {
        List<String> operands = getOperands(code, 1, index);
        System.out.printf("NOT %s\n", operands.get(0));
    }
}
