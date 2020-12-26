package chapter1.section6.p.time.waster.problems.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/20.
 */
public class FunctionalFun {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        String line = FastReader.getLine();

        while (line != null) {
            String[] domain = line.split(" ");
            String[] coDomain = FastReader.getLine().split(" ");
            int mappingsNumber = FastReader.nextInt();

            boolean isSurjective = false;
            boolean isInjective = true;
            boolean isFunction = true;
            Set<String> coDomainValuesMapped = new HashSet<>();
            Map<String, String> mappings = new HashMap<>();

            for (int m = 0; m < mappingsNumber; m++) {
                String[] mapping = FastReader.getLine().split(" ");
                String domainValue = mapping[0];
                String coDomainValue = mapping[2];

                if (coDomainValuesMapped.contains(coDomainValue)) {
                    isInjective = false;
                }
                coDomainValuesMapped.add(coDomainValue);

                if (mappings.containsKey(domainValue)) {
                    if (!mappings.get(domainValue).equals(coDomainValue)) {
                        isFunction = false;
                    }
                }
                mappings.put(domainValue, coDomainValue);
            }

            if (coDomainValuesMapped.size() == coDomain.length - 1) {
                isSurjective = true;
            }

            if (!isFunction) {
                System.out.println("not a function");
            } else if (isInjective && isSurjective) {
                System.out.println("bijective");
            } else if (isInjective) {
                System.out.println("injective");
            } else if (isSurjective) {
                System.out.println("surjective");
            } else {
                System.out.println("neither injective nor surjective");
            }
            line = FastReader.getLine();
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
