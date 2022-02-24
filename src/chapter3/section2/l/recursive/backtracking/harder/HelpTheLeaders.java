package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/02/22.
 */
public class HelpTheLeaders {

    private static class Topic implements Comparable<Topic> {
        String name;

        public Topic(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(Topic other) {
            if (name.length() != other.name.length()) {
                return Integer.compare(other.name.length(), name.length());
            }
            return name.compareTo(other.name);
        }
    }

    private static class TopicGroup implements Comparable<TopicGroup> {
        Topic[] topics;

        public TopicGroup(Topic[] topics) {
            this.topics = topics;
        }

        @Override
        public int compareTo(TopicGroup other) {
            for (int i = 0; i < topics.length; i++) {
                int compare = topics[i].compareTo(other.topics[i]);
                if (compare != 0) {
                    return compare;
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            Topic[] topics = new Topic[FastReader.nextInt()];
            int prohibitedPairsNumber = FastReader.nextInt();
            int topicsToCover = FastReader.nextInt();

            for (int i = 0; i < topics.length; i++) {
                topics[i] = new Topic(FastReader.next().toUpperCase());
            }
            Map<String, Set<String>> prohibitedPairs = new HashMap<>();
            for (int i = 0; i < prohibitedPairsNumber; i++) {
                String topic1 = FastReader.next().toUpperCase();
                String topic2 = FastReader.next().toUpperCase();

                if (!prohibitedPairs.containsKey(topic1)) {
                    prohibitedPairs.put(topic1, new HashSet<>());
                }
                if (!prohibitedPairs.containsKey(topic2)) {
                    prohibitedPairs.put(topic2, new HashSet<>());
                }
                prohibitedPairs.get(topic1).add(topic2);
                prohibitedPairs.get(topic2).add(topic1);
            }

            List<TopicGroup> topicGroups = new ArrayList<>();
            Topic[] currentTopics = new Topic[topicsToCover];
            computeTopicGroups(topics, prohibitedPairs, topicGroups, currentTopics, 0, 0);

            Collections.sort(topicGroups);
            outputWriter.printLine(String.format("Set %d:", t));
            for (TopicGroup topicGroup : topicGroups) {
                Topic[] topicArray = topicGroup.topics;
                outputWriter.print(topicArray[0].name);

                for (int i = 1; i < topicArray.length; i++) {
                    outputWriter.print(" " + topicArray[i].name);
                }
                outputWriter.printLine();
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void computeTopicGroups(Topic[] topics, Map<String, Set<String>> prohibitedPairs,
                                           List<TopicGroup> topicGroups, Topic[] currentTopics,
                                           int currentTopicsIndex, int topicsIndex) {
        if (currentTopicsIndex == currentTopics.length) {
            Topic[] topicsToDiscuss = new Topic[currentTopics.length];
            System.arraycopy(currentTopics, 0, topicsToDiscuss, 0, currentTopics.length);
            Arrays.sort(topicsToDiscuss);
            topicGroups.add(new TopicGroup(topicsToDiscuss));
            return;
        }
        if (topicsIndex == topics.length) {
            return;
        }

        // Not adding topic
        computeTopicGroups(topics, prohibitedPairs, topicGroups, currentTopics, currentTopicsIndex,
                topicsIndex + 1);

        // Possibly adding topic
        Set<String> prohibitedTopics = prohibitedPairs.get(topics[topicsIndex].name);
        if (prohibitedTopics != null) {
            for (int i = 0; i < currentTopicsIndex; i++) {
                if (prohibitedTopics.contains(currentTopics[i].name)) {
                    return;
                }
            }
        }
        currentTopics[currentTopicsIndex] = topics[topicsIndex];
        computeTopicGroups(topics, prohibitedPairs, topicGroups, currentTopics, currentTopicsIndex + 1,
                topicsIndex + 1);
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
