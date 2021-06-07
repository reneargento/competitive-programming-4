package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/06/21.
 */
@SuppressWarnings("unchecked")
public class IntrospectiveCaching {

    private static class CacheObject implements Comparable<CacheObject> {
        int object;
        int nextAccess;

        public CacheObject(int object, int nextAccess) {
            this.object = object;
            this.nextAccess = nextAccess;
        }

        @Override
        public int compareTo(CacheObject other) {
            return Integer.compare(nextAccess, other.nextAccess);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cacheSize = FastReader.nextInt();
        int systemObjects = FastReader.nextInt();
        int accessesNumber = FastReader.nextInt();

        LinkedList<Integer>[] objectAccesses = new LinkedList[systemObjects];
        int[] accesses = new int[accessesNumber];

        for (int i = 0; i < accesses.length; i++) {
            int object = FastReader.nextInt();
            if (objectAccesses[object] == null) {
                objectAccesses[object] = new LinkedList<>();
            }
            objectAccesses[object].offer(i);
            accesses[i] = object;
        }

        TreeSet<CacheObject> objectsInCache = new TreeSet<>();
        int objectsReadIntoCache = 0;

        for (int i = 0; i < accesses.length; i++) {
            int object = accesses[i];
            CacheObject currentObject = new CacheObject(object, i);
            objectAccesses[object].poll();

            int nextAccess;
            if (!objectAccesses[object].isEmpty()) {
                nextAccess = objectAccesses[object].peekFirst();
            } else {
                nextAccess = Integer.MAX_VALUE;
            }

            CacheObject nextCacheObject = new CacheObject(object, nextAccess);

            if (objectsInCache.contains(currentObject)) {
                objectsInCache.remove(currentObject);
                objectsInCache.add(nextCacheObject);
            } else {
                if (objectsInCache.size() == cacheSize) {
                    objectsInCache.pollLast();
                }
                objectsInCache.add(nextCacheObject);
                objectsReadIntoCache++;
            }
        }
        outputWriter.printLine(objectsReadIntoCache);
        outputWriter.flush();
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
