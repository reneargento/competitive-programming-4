package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/07/22.
 */
public class HorrorFilmNight {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] person1Likes = readLikes();
        int[] person2Likes = readLikes();
        int maximumFilms = computeMaximumFilms(person1Likes, person2Likes);
        outputWriter.printLine(maximumFilms);
        outputWriter.flush();
    }

    private static int[] readLikes() throws IOException {
        int[] personLikes = new int[FastReader.nextInt()];
        for (int i = 0; i < personLikes.length; i++) {
            personLikes[i] = FastReader.nextInt();
        }
        return personLikes;
    }

    private static int computeMaximumFilms(int[] person1Likes, int[] person2Likes) {
        int maximumFilms = 0;

        Arrays.sort(person1Likes);
        Arrays.sort(person2Likes);
        boolean person1Disliked = false;
        boolean person2Disliked = false;

        int person1Index = 0;
        int person2Index = 0;

        while (person1Index < person1Likes.length
                || person2Index < person2Likes.length) {
            if (person1Index == person1Likes.length) {
                maximumFilms++;
                break;
            } else if (person2Index == person2Likes.length) {
                maximumFilms++;
                break;
            }

            if (person1Likes[person1Index] == person2Likes[person2Index]) {
                maximumFilms++;
                person1Index++;
                person2Index++;
                person1Disliked = false;
                person2Disliked = false;
            } else {
                if (person1Likes[person1Index] < person2Likes[person2Index]) {
                    if (!person2Disliked) {
                        maximumFilms++;
                        person1Disliked = false;
                        person2Disliked = true;
                    }
                    person1Index++;
                } else {
                    if (!person1Disliked) {
                        maximumFilms++;
                        person2Disliked = false;
                        person1Disliked = true;
                    }
                    person2Index++;
                }
            }
        }
        return maximumFilms;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
