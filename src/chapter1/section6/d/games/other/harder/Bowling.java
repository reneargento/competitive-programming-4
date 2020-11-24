package chapter1.section6.d.games.other.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/10/20.
 */
public class Bowling {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        String scoresLine = FastReader.getLine();

        while (!scoresLine.equals("Game Over")) {
            int score = 0;
            String[] scores = scoresLine.split(" ");
            int roll = 1;
            boolean firstRoll = true;

            for (int i = 0; i < scores.length; i++) {
                if (scores[i].equals("/")) {
                    score += 10;
                    score -= Character.getNumericValue(scores[i - 1].charAt(0));
                    if (roll < 10) {
                        score += getNextRollScore(scores, i + 1);
                    }
                } else if (scores[i].equals("X")) {
                    score += 10;
                    if (roll < 10) {
                        score += getNextRollScore(scores, i + 1);
                        score += getNextRollScore(scores, i + 2);
                    }
                    firstRoll = false;
                } else {
                    score += Character.getNumericValue(scores[i].charAt(0));
                }

                if (firstRoll) {
                    firstRoll = false;
                } else {
                    firstRoll = true;
                    roll++;
                }

            }
            System.out.println(score);
            scoresLine = FastReader.getLine();
        }
    }

    private static int getNextRollScore(String[] scores, int index) {
        if (index < scores.length) {
            String score = scores[index];

            if (score.equals("X")) {
                return 10;
            } else if (score.equals("/")) {
                return 10 - Character.getNumericValue(scores[index - 1].charAt(0));
            } else {
                return Character.getNumericValue(score.charAt(0));
            }
        }
        return 0;
    }

    public static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        //Used to check EOF
        //If getLine() == null, it is a EOF
        //Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

}
