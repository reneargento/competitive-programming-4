package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/22.
 */
public class PageLayout {

    private static class Story {
        int width;
        int height;
        int x;
        int y;

        public Story(int width, int height, int x, int y) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int storiesNumber = FastReader.nextInt();

        while (storiesNumber != 0) {
            Story[] stories = new Story[storiesNumber];
            for (int i = 0; i < stories.length; i++) {
                stories[i] = new Story(FastReader.nextInt() - 1, FastReader.nextInt() - 1,
                        FastReader.nextInt() + 1, FastReader.nextInt() + 1);
            }
            long maximumArea = computeMaximumArea(stories, 0, 0, 0);
            outputWriter.printLine(maximumArea);
            storiesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeMaximumArea(Story[] stories, int mask, int index, long currentArea) {
        if (index == stories.length) {
            return currentArea;
        }

        long areaWithoutStory = computeMaximumArea(stories, mask, index + 1, currentArea);
        long areaWithStory = 0;

        Story story = stories[index];
        if (!intersects(stories, story, mask)) {
            int newMask = mask | (1 << index);
            long area = (story.height + 1L) * (story.width + 1L);
            areaWithStory = computeMaximumArea(stories, newMask, index + 1, currentArea + area);
        }
        return Math.max(areaWithoutStory, areaWithStory);
    }

    private static boolean intersects(Story[] stories, Story story, int mask) {
        int y2 = story.y + story.height;
        int x2 = story.x + story.width;

        for (int i = 0; i < stories.length; i++) {
            if ((mask & (1 << i)) > 0) {
                int otherStoryY2 = stories[i].y + stories[i].height;
                int otherStoryX2 = stories[i].x + stories[i].width;

                if (story.x <= otherStoryX2 &&
                        stories[i].x <= x2 &&
                        story.y <= otherStoryY2 &&
                        stories[i].y <= y2) {
                    return true;
                }
            }
        }
        return false;
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
