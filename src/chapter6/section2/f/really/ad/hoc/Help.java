package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/07/2026.
 */
public class Help {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String pattern1 = FastReader.getLine();
            String pattern2 = FastReader.getLine();

            String matchingPhrase = computeMatchingPhrase(pattern1, pattern2);
            outputWriter.printLine(matchingPhrase);
        }
        outputWriter.flush();
    }

    private static String computeMatchingPhrase(String pattern1, String pattern2) {
        String[] tokens1 = pattern1.split(" ");
        String[] tokens2 = pattern2.split(" ");
        if (tokens1.length != tokens2.length) {
            return "-";
        }

        UnionFind unionFind = new UnionFind(tokens1, tokens2);
        Map<String, Integer> placeholderToId = new HashMap<>();
        StringBuilder matchingPhrase = new StringBuilder();

        connectPlaceholders(unionFind, placeholderToId, tokens1, tokens2);

        for (int i = 0; i < tokens1.length; i++) {
            if (isPlaceholder(tokens1[i]) && isPlaceholder(tokens2[i])) {
                int id1 = getId(placeholderToId, tokens1[i] + "1");
                int id2 = getId(placeholderToId, tokens2[i] + "2");

                if (!isPlaceholder(unionFind.getValue(id1))
                        && !isPlaceholder(unionFind.getValue(id2))) {
                    String mapping1 = unionFind.getValue(id1);
                    String mapping2 = unionFind.getValue(id2);
                    if (!mapping1.equals(mapping2)) {
                        return "-";
                    } else {
                        appendWord(matchingPhrase, mapping1);
                    }
                } else if (!isPlaceholder(unionFind.getValue(id1))) {
                    String mapping = unionFind.getValue(id1);
                    appendWord(matchingPhrase, mapping);
                } else if (!isPlaceholder(unionFind.getValue(id2))) {
                    String mapping = unionFind.getValue(id2);
                    appendWord(matchingPhrase, mapping);
                } else {
                    String newString = "rene";
                    unionFind.setValue(id1, newString);
                    appendWord(matchingPhrase, newString);
                }
            } else if (isPlaceholder(tokens1[i])) {
                int id1 = getId(placeholderToId, tokens1[i] + "1");
                if (!isPlaceholder(unionFind.getValue(id1))) {
                    String mapping = unionFind.getValue(id1);
                    if (!mapping.equals(tokens2[i])) {
                        return "-";
                    } else {
                        appendWord(matchingPhrase, mapping);
                    }
                } else {
                    unionFind.setValue(id1, tokens2[i]);
                    appendWord(matchingPhrase, tokens2[i]);
                }
            } else if (isPlaceholder(tokens2[i])) {
                int id2 = getId(placeholderToId, tokens2[i] + "2");
                if (!isPlaceholder(unionFind.getValue(id2))) {
                    String mapping = unionFind.getValue(id2);
                    if (!mapping.equals(tokens1[i])) {
                        return "-";
                    } else {
                        appendWord(matchingPhrase, mapping);
                    }
                } else {
                    unionFind.setValue(id2, tokens1[i]);
                    appendWord(matchingPhrase, tokens1[i]);
                }
            } else {
                if (!tokens1[i].equals(tokens2[i])) {
                    return "-";
                }
                appendWord(matchingPhrase, tokens1[i]);
            }
        }
        return matchingPhrase.toString();
    }

    private static int getId(Map<String, Integer> placeholderToId, String token) {
        if (!placeholderToId.containsKey(token)) {
            placeholderToId.put(token, placeholderToId.size());
        }
        return placeholderToId.get(token);
    }

    private static void connectPlaceholders(UnionFind unionFind, Map<String, Integer> placeholderToId,
                                            String[] tokens1, String[] tokens2) {
        for (int i = 0; i < tokens1.length; i++) {
            if (isPlaceholder(tokens1[i]) && isPlaceholder(tokens2[i])) {
                int id1 = getId(placeholderToId, tokens1[i] + "1");
                int id2 = getId(placeholderToId, tokens2[i] + "2");
                unionFind.union(id1, id2);
            } else if (isPlaceholder(tokens1[i])) {
                int id1 = getId(placeholderToId, tokens1[i] + "1");
                unionFind.setValue(id1, tokens2[i]);
            } else if (isPlaceholder(tokens2[i])) {
                int id2 = getId(placeholderToId, tokens2[i] + "2");
                unionFind.setValue(id2, tokens1[i]);
            }
        }
    }

    private static void appendWord(StringBuilder matchingPhrase, String word) {
        if (matchingPhrase.length() > 0) {
            matchingPhrase.append(' ');
        }
        matchingPhrase.append(word);
    }

    private static boolean isPlaceholder(String token) {
        return token.charAt(0) == '<';
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] sizes;
        private String[] values;

        public UnionFind(String[] tokens1, String[] tokens2) {
            int size = tokens1.length + tokens2.length;
            leaders = new int[size];
            sizes = new int[size];
            values = new String[size];

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                sizes[i] = 1;
            }
            Arrays.fill(values, "<");
        }

        public String getValue(int site) {
            int leader = find(site);
            return values[leader];
        }

        public void setValue(int site, String value) {
            int leader = find(site);
            values[leader] = value;
        }

        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (values[leader1].equals("<")) {
                leaders[leader1] = leader2;
                values[leader1] = values[leader2];
                sizes[leader2] += sizes[leader1];
            } else {
                leaders[leader2] = leader1;
                values[leader2] = values[leader1];
                sizes[leader1] += sizes[leader2];
            }
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
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