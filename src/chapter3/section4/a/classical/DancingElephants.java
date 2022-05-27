package chapter3.section4.a.classical;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/05/22.
 */
// Based on https://github.com/CharlyGaleana/Problemas-Entrenamiento-Marzo/blob/master/Dancing%20Elephants%20(IOI%202011).cpp
// Problem located at https://ioinformatics.org/files/ioi2011problem5.pdf
public class DancingElephants {

    private static class Bucket {
        int position;
        int[] elephants;

        public Bucket() {
            elephants = new int[800];
        }
    }

    private static final int ELEPHANTS_NUMBER = 151002;

    private static int cameraLength;
    private static int bucketsNumber;
    private static int limit;
    private static int bucketsCreated;
    private static int elephantsNumber;
    private static boolean shouldReorderBuckets;

    private static Bucket[] buckets;
    private static int[] elephantToPosition;
    private static int[] elephantToBucket;
    private static int[] elephantToBucketPosition;
    private static int[] leftmostElephantInRightmostBucketCamera;
    private static int[] positionToElephant;
    private static int[] camerasNeededFromElephantToEndOfBucket;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int elephantsNumber = FastReader.nextInt();
        int segmentLength = FastReader.nextInt();
        int acts = FastReader.nextInt();

        int[] initialPositions = new int[ELEPHANTS_NUMBER];
        positionToElephant = new int[ELEPHANTS_NUMBER];

        for (int i = 0; i < elephantsNumber; i++) {
            initialPositions[i] = FastReader.nextInt();
            positionToElephant[i] = i;
        }
        init(elephantsNumber, segmentLength, initialPositions);
        shouldReorderBuckets = true;

        for (int i = 0; i < acts; i++) {
            if (shouldReorderBuckets) {
                placeElephantsInBuckets();
            }
            shouldReorderBuckets = false;

            int elephant = FastReader.nextInt();
            int newPosition = FastReader.nextInt();
            update(elephant, newPosition);

            int minimumCamerasNeeded = query();
            outputWriter.printLine(minimumCamerasNeeded);
        }
        outputWriter.flush();
    }

    public static void init(int elephants, int segmentLength, int[] initialPositions) {
        elephantsNumber = elephants;
        cameraLength = segmentLength;
        elephantToPosition = initialPositions;

        bucketsNumber = (int) Math.sqrt(elephants);
        limit = bucketsNumber + (bucketsNumber / 2) + (bucketsNumber / 4);

        buckets = new Bucket[400];
        elephantToBucket = new int[ELEPHANTS_NUMBER];
        elephantToBucketPosition = new int[ELEPHANTS_NUMBER];
        leftmostElephantInRightmostBucketCamera = new int[ELEPHANTS_NUMBER];
        camerasNeededFromElephantToEndOfBucket = new int[ELEPHANTS_NUMBER];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket();
        }
    }

    private static void placeElephantsInBuckets() {
        int bucketIndex = 0;
        int position = 0;

        for (int i = 0; i < bucketsCreated; i++) {
            for (int j = 0; j < buckets[i].position; j++) {
                positionToElephant[position] = buckets[i].elephants[j];
                position++;
            }
        }
        for (int i = 0; i < bucketsCreated; i++) {
            buckets[i].position = 0;
        }

        for (int i = 0; i < elephantsNumber; i++) {
            if (buckets[bucketIndex].position >= bucketsNumber) {
                bucketIndex++;
            }
            int elephantIndex = buckets[bucketIndex].position;
            buckets[bucketIndex].elephants[elephantIndex] = positionToElephant[i];
            buckets[bucketIndex].position++;

            int elephant = positionToElephant[i];
            elephantToBucket[elephant] = bucketIndex;
            elephantToBucketPosition[elephant] = buckets[bucketIndex].position - 1;
        }

        bucketsCreated = bucketIndex + 1;
        for (int i = 0; i < bucketsCreated; i++) {
            Bucket bucket = buckets[i];
            int currentBucketPosition = bucket.position;
            bucket.elephants[currentBucketPosition] = elephantsNumber + i + 1;
            computeCamerasForBucket(bucket, bucket.position - 1);
        }
    }

    private static void computeCamerasForBucket(Bucket bucket, int position) {
        int bucketPosition = bucket.position;
        int currentElephant = 0;
        if (bucketPosition >= 0) {
            currentElephant = bucket.elephants[bucketPosition];
        }
        int currentElephantMinus1 = 0;
        if (bucketPosition - 1 >= 0) {
            currentElephantMinus1 = bucket.elephants[bucketPosition - 1];
        }
        // Add dummy elephant
        elephantToPosition[currentElephant] = elephantToPosition[currentElephantMinus1] + cameraLength + 1;

        camerasNeededFromElephantToEndOfBucket[currentElephant] = 0;
        leftmostElephantInRightmostBucketCamera[currentElephant] = currentElephant;

        int lastPositionCovered = bucketPosition;
        for (int i = position; i >= 0; i--) {
            int elephant = bucket.elephants[i];
            while (elephantToPosition[bucket.elephants[lastPositionCovered - 1]] >
                    elephantToPosition[elephant] + cameraLength) {
                lastPositionCovered--;
            }
            camerasNeededFromElephantToEndOfBucket[elephant] =
                    camerasNeededFromElephantToEndOfBucket[bucket.elephants[lastPositionCovered]] + 1;
            leftmostElephantInRightmostBucketCamera[elephant] =
                    leftmostElephantInRightmostBucketCamera[bucket.elephants[lastPositionCovered]];
            if (leftmostElephantInRightmostBucketCamera[elephant] == bucket.elephants[bucket.position]) {
                leftmostElephantInRightmostBucketCamera[elephant] = elephant;
            }
        }
    }

    public static void update(int elephant, int position) {
        int bucketIndex = bucketsCreated - 1;
        elephantToPosition[elephant] = position;
        removeFromBucket(elephantToBucket[elephant], elephantToBucketPosition[elephant]);

        for (int i = 0; i < bucketsCreated; i++) {
            if (buckets[i].position > 0) {
                int bucketPositionMinus1 = buckets[i].position - 1;
                int elephantInBucket = buckets[i].elephants[bucketPositionMinus1];
                if (elephantToPosition[elephantInBucket] >= position) {
                    bucketIndex = i;
                    break;
                }
            }
        }

        int leftmostElephantPosition = buckets[bucketIndex].elephants[0];
        int bucketPositionToInsert;
        if (elephantToPosition[leftmostElephantPosition] >= position
                || buckets[bucketIndex].position == 0) {
            bucketPositionToInsert = 0;
        } else {
            bucketPositionToInsert = findBucketPositionToAddElephant(buckets[bucketIndex], position);
        }
        addToBucket(buckets[bucketIndex], bucketIndex, bucketPositionToInsert, elephant);
        elephantToBucket[elephant] = bucketIndex;
        elephantToBucketPosition[elephant] = bucketPositionToInsert;
    }

    private static void removeFromBucket(int bucketIndex, int bucketPosition) {
        Bucket bucket = buckets[bucketIndex];
        bucket.position--;
        if (bucket.position == 0) {
            shouldReorderBuckets = true;
        }

        for (; bucketPosition < bucket.position; bucketPosition++){
            bucket.elephants[bucketPosition] = bucket.elephants[bucketPosition + 1];
            elephantToBucketPosition[bucket.elephants[bucketPosition]] = bucketPosition;
        }
        int lastBucketPosition = 0;
        if (bucket.position >= 0) {
            lastBucketPosition = bucket.position;
        }
        bucket.elephants[lastBucketPosition] = elephantsNumber + bucketIndex + 1;
        computeCamerasForBucket(bucket, bucketPosition - 1);
    }

    private static void addToBucket(Bucket bucket, int bucketIndex, int bucketPositionToInsert, int elephant) {
        bucket.position++;
        if (bucket.position >= limit) {
            shouldReorderBuckets = true;
        }

        for (int bucketPosition = bucket.position; bucketPosition >= bucketPositionToInsert + 1; bucketPosition--) {
            bucket.elephants[bucketPosition] = bucket.elephants[bucketPosition - 1];
            elephantToBucketPosition[bucket.elephants[bucketPosition]] = bucketPosition;
        }
        bucket.elephants[bucketPositionToInsert] = elephant;
        bucket.elephants[bucket.position] = elephantsNumber + bucketIndex + 1;
        computeCamerasForBucket(bucket, bucketPositionToInsert);
    }

    private static int findBucketPositionToAddElephant(Bucket bucket, int position){
        int start = 0;
        int end = bucket.position - 1;

        while (start != end) {
            int middle = (start + end) / 2 + 1;
            int elephant = bucket.elephants[middle];
            if (elephantToPosition[elephant] < position) {
                start = middle;
            } else {
                end = middle - 1;
            }
        }
        return start + 1;
    }

    private static int query() {
        int cameras = 0;
        int currentPosition = -1;

        for (int i = 0; i < bucketsCreated; i++) {
            Bucket bucket = buckets[i];
            if (bucket.position > 0) {
                int lastBucketPosition = bucket.position - 1;
                int lastElephant = bucket.elephants[lastBucketPosition];
                if (elephantToPosition[lastElephant] > currentPosition) {
                    int nextElephant = findFirstElephantAfterPosition(bucket, currentPosition);
                    cameras += camerasNeededFromElephantToEndOfBucket[nextElephant];
                    currentPosition = elephantToPosition[leftmostElephantInRightmostBucketCamera[nextElephant]]
                            + cameraLength;
                }
            }
        }
        return cameras;
    }

    private static int findFirstElephantAfterPosition(Bucket bucket, int position) {
        int start = 0;
        int end = bucket.position - 1;

        while (start != end) {
            int middle = (start + end) / 2;
            int elephant = bucket.elephants[middle];
            if (elephantToPosition[elephant] > position) {
                end = middle;
            } else {
                start = middle + 1;
            }
        }
        return bucket.elephants[start];
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
