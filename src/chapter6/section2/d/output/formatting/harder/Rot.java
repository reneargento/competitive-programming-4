package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/06/2026.
 */
public class Rot {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] words = new String[FastReader.nextInt()];
        FastReader.nextInt();

        for (int i = 0; i < words.length; i++) {
            words[i] = FastReader.getLine();
        }
        int angle = FastReader.nextInt();
        rotate(words, angle, outputWriter);
        outputWriter.flush();
    }

    private static void rotate(String[] words, int angle, OutputWriter outputWriter) {
        if (angle < 180 || angle == 360) {
            if (angle == 0 || angle == 360) {
                rotate0(words, outputWriter);
            } else if (angle == 45) {
                rotate45(words, outputWriter);
            } else if (angle == 90) {
                rotate90(words, outputWriter);
            } else if (angle == 135) {
                String[] newOrderWord = switchOrder(words);
                rotate135(newOrderWord, outputWriter);
            }
        } else {
            String[] reversedWords = reverseWords(words);
            String[] newOrderWord = switchOrder(reversedWords);

            if (angle == 180) {
                rotate0(newOrderWord, outputWriter);
            } else if (angle == 225) {
                rotate45(newOrderWord, outputWriter);
            } else if (angle == 270) {
                rotate90(newOrderWord, outputWriter);
            } else if (angle == 315) {
                rotate135(reversedWords, outputWriter);
            }
        }
    }

    private static String[] reverseWords(String[] words) {
        String[] reversedWords = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            reversedWords[i] = new StringBuilder(words[i]).reverse().toString();
        }
        return reversedWords;
    }

    private static String[] switchOrder(String[] words) {
        String[] newOrderWord = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            newOrderWord[i] = words[words.length - 1 - i];
        }
        return newOrderWord;
    }

    private static void rotate0(String[] words, OutputWriter outputWriter) {
        for (String word : words) {
            outputWriter.printLine(word);
        }
    }

    private static void rotate45(String[] words, OutputWriter outputWriter) {
        // Section 1
        int leadingSpaces = words.length - 1;
        for (int i = 0; i < words.length; i++) {
            for (int s = 0; s < leadingSpaces; s++) {
                outputWriter.print(' ');
            }

            for (int j = 0; j <= i; j++) {
                if (j == words[i - j].length()) {
                    break;
                }
                if (j > 0) {
                    outputWriter.print(' ');
                }
                outputWriter.print(words[i - j].charAt(j));
            }
            leadingSpaces--;
            outputWriter.printLine();
        }

        // Section 2
        leadingSpaces = 1;
        int characterIndex = 1;
        for (int i = 0; i < words[0].length() - 1; i++) {
            for (int s = 0; s < leadingSpaces; s++) {
                outputWriter.print(' ');
            }

            int characterIndexIncrement = 0;
            for (int j = words.length - 1; j >= 0; j--, characterIndexIncrement++) {
                int index = characterIndex + characterIndexIncrement;
                if (index < words[j].length()) {
                    if (j < words.length - 1) {
                        outputWriter.print(' ');
                    }
                    outputWriter.print(words[j].charAt(index));
                }
            }
            leadingSpaces++;
            characterIndex++;
            outputWriter.printLine();
        }
    }

    private static void rotate90(String[] words, OutputWriter outputWriter) {
        for (int i = 0; i < words[0].length(); i++) {
            for (int j = words.length - 1; j >= 0; j--) {
                outputWriter.print(words[j].charAt(i));
            }
            outputWriter.printLine();
        }
    }

    private static void rotate135(String[] words, OutputWriter outputWriter) {
        // Section 1
        int leadingSpaces = words[0].length() - 1;
        for (int i = 0; i < words[0].length(); i++) {
            for (int s = 0; s < leadingSpaces; s++) {
                outputWriter.print(' ');
            }

            for (int j = 0; j <= i; j++) {
                if (j >= words.length) {
                    break;
                }
                if (j > 0) {
                    outputWriter.print(' ');
                }
                outputWriter.print(words[j].charAt(i - j));
            }
            leadingSpaces--;
            outputWriter.printLine();
        }

        // Section 2
        leadingSpaces = 1;
        for (int i = 0; i < words.length - 1; i++) {
            for (int s = 0; s < leadingSpaces; s++) {
                outputWriter.print(' ');
            }

            int characterIndexDecrement = 0;
            boolean addSpace = false;
            for (int j = 1; j < words.length; j++, characterIndexDecrement++) {
                int index = words[0].length() - 1 - characterIndexDecrement + i;
                if (index >= 0 && index < words[j].length()) {
                    if (addSpace) {
                        outputWriter.print(' ');
                    }
                    outputWriter.print(words[j].charAt(index));
                    addSpace = true;
                }
            }
            leadingSpaces++;
            outputWriter.printLine();
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