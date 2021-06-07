package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/06/21.
 */
public class OrphanBackups {

    private static class FileName implements Comparable<FileName> {
        String backupImage;
        String fullName;

        public FileName(String fullName, boolean isImage) {
            this.fullName = fullName;
            if (isImage) {
                backupImage = fullName;
            } else {
                int backupImageEndIndex = getBackupImageEndIndex(fullName);
                backupImage = fullName.substring(0, backupImageEndIndex);
            }
        }

        private static int getBackupImageEndIndex(String fullName) {
            int underscoresFound = 0;

            for (int i = fullName.length() - 1; i >= 0; i--) {
                if (fullName.charAt(i) == '_') {
                    underscoresFound++;
                    if (underscoresFound == 2) {
                        return i;
                    }
                }
            }
            return -1;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            FileName fileName = (FileName) other;
            return Objects.equals(backupImage, fileName.backupImage);
        }

        @Override
        public int hashCode() {
            return Objects.hash(backupImage);
        }

        @Override
        public int compareTo(FileName other) {
            return fullName.compareTo(other.fullName);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();

        Set<FileName> backupImages = new HashSet<>();
        Set<FileName> fileNames = new HashSet<>();
        Map<FileName, List<FileName>> equivalentFileNames = new HashMap<>();

        String backupImageLine = FastReader.getLine();
        while (!backupImageLine.equals("")) {
            backupImages.add(new FileName(backupImageLine, true));
            backupImageLine = FastReader.getLine();
        }

        String fileNameLine = FastReader.getLine();
        while (fileNameLine != null) {
            FileName fileName = new FileName(fileNameLine, false);
            if (fileNames.contains(fileName)) {
                if (!equivalentFileNames.containsKey(fileName)) {
                    equivalentFileNames.put(fileName, new ArrayList<>());
                }
                equivalentFileNames.get(fileName).add(fileName);
            } else {
                fileNames.add(fileName);
            }
            fileNameLine = FastReader.getLine();
        }
        computeOrphanFiles(backupImages, fileNames, equivalentFileNames);
    }

    private static void computeOrphanFiles(Set<FileName> backupImages, Set<FileName> fileNames,
                                           Map<FileName, List<FileName>> equivalentFileNames) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<FileName> orphanBackupImages = new ArrayList<>();
        List<FileName> orphanFileNames = new ArrayList<>();

        for (FileName backupImage : backupImages) {
            if (!fileNames.contains(backupImage)) {
                orphanBackupImages.add(backupImage);
            }
        }
        for (FileName fileName : fileNames) {
            if (!backupImages.contains(fileName)) {
                orphanFileNames.add(fileName);
                if (equivalentFileNames.containsKey(fileName)) {
                    orphanFileNames.addAll(equivalentFileNames.get(fileName));
                }
            }
        }

        if (orphanBackupImages.isEmpty() && orphanFileNames.isEmpty()) {
            outputWriter.printLine("No mismatches.");
        } else {
            Collections.sort(orphanBackupImages);
            Collections.sort(orphanFileNames);

            for (FileName fileName : orphanFileNames) {
                outputWriter.printLine("F " + fileName.fullName);
            }
            for (FileName backupImage : orphanBackupImages) {
                outputWriter.printLine("I " + backupImage.fullName);
            }
        }
        outputWriter.flush();
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
