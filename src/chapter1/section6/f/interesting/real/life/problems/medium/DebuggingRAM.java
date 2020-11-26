package chapter1.section6.f.interesting.real.life.problems.medium;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 04/11/20.
 */
public class DebuggingRAM {

    private static class Variable {
        String name;
        int bytesSize;

        public Variable(String name, int bytesSize) {
            this.name = name;
            this.bytesSize = bytesSize;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            scanner.nextInt();
            int variablesNumber = scanner.nextInt();
            Map<String, String> variableBits = new HashMap<>();

            Variable[] variables = new Variable[variablesNumber];
            for (int i = 0; i < variables.length; i++) {
                variables[i] = new Variable(scanner.next(), scanner.nextInt());
            }

            for (int i = 0; i < variables.length; i++) {
                StringBuilder bits = new StringBuilder();

                for (int s = 0; s < variables[i].bytesSize; s++) {
                    String byteValue = scanner.next();
                    bits.append(byteValue);
                }
                variableBits.put(variables[i].name, bits.toString());
            }

            int queries = scanner.nextInt();
            for (int q = 0; q < queries; q++) {
                String queryVariable = scanner.next();
                System.out.print(queryVariable + "=");

                if (variableBits.containsKey(queryVariable)) {
                    String queryBits = variableBits.get(queryVariable);

                    if (queryBits.length() > 63) {
                        BigInteger decimalValue = new BigInteger(queryBits, 2);
                        System.out.println(decimalValue);
                    } else {
                        long decimalValue = Long.parseLong(queryBits, 2);
                        System.out.println(decimalValue);
                    }
                } else {
                    System.out.println();
                }
            }
        }
    }
}
