package chapter1.section6.o.time.waster.problems.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/12/20.
 */
public class WindowManager {
    private static class Window {
        long startRow;
        long startColumn;
        long width;
        long height;

        public Window(long startRow, long startColumn, long width, long height) {
            this.startRow = startRow;
            this.startColumn = startColumn;
            this.width = width;
            this.height = height;
        }
    }

    private static class IntersectionSearch {
        Set<Window> intersectionWindows;
        long moves;

        public IntersectionSearch(Set<Window> intersectionWindows, long moves) {
            this.intersectionWindows = intersectionWindows;
            this.moves = moves;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        long width = FastReader.nextInt();
        long height = FastReader.nextInt();
        int commandId = 1;
        List<Window> windows = new LinkedList<>();
        String line = FastReader.getLine();

        while (line != null) {
            List<String> cleanInput = getWords(line);
            if (Character.isDigit(cleanInput.get(0).charAt(0))) {
                printFinalWindows(windows, outputWriter);
                windows = new LinkedList<>();
                commandId = 1;
                width = Integer.parseInt(cleanInput.get(0));
                height = Integer.parseInt(cleanInput.get(1));
                line = FastReader.getLine();
                if (line == null) {
                    break;
                }
                continue;
            }

            String command = cleanInput.get(0);
            int column = Integer.parseInt(cleanInput.get(1));
            int row = Integer.parseInt(cleanInput.get(2));
            if (command.equals("CLOSE")) {
                closeWindow(windows, column, row, commandId, outputWriter);
            } else {
                int parameter3 = Integer.parseInt(cleanInput.get(3));
                int parameter4 = Integer.parseInt(cleanInput.get(4));
                if (command.equals("OPEN")) {
                    openOrResizeWindow(windows, column, row, parameter3, parameter4, width, height, commandId,
                            "OPEN", null, outputWriter);
                } else if (command.equals("RESIZE")) {
                    Window windowToResize = getAssociatedWindow(windows, column, row);
                    if (windowToResize != null) {
                        openOrResizeWindow(windows, windowToResize.startColumn, windowToResize.startRow, parameter3,
                                parameter4, width, height, commandId, "RESIZE", windowToResize, outputWriter);
                    } else {
                        outputWriter.printLine("Command " + commandId + ": RESIZE - no window at given position");
                    }
                } else {
                    moveWindow(windows, column, row, parameter3, parameter4, width, height, commandId, outputWriter);
                }
            }
            commandId++;
            line = FastReader.getLine();
        }
        printFinalWindows(windows, outputWriter);
        outputWriter.flush();
        outputWriter.close();
    }

    private static void closeWindow(List<Window> windows, long column, long row, int commandId, OutputWriter outputWriter) {
        Window windowToClose = getAssociatedWindow(windows, column, row);
        if (windowToClose != null) {
            windows.remove(windowToClose);
        } else {
            outputWriter.printLine("Command " + commandId + ": CLOSE - no window at given position");
        }
    }

    private static void openOrResizeWindow(List<Window> windows, long column, long row, long width, long height,
                                           long totalWidth, long totalHeight, int commandId, String command,
                                           Window windowToResize, OutputWriter outputWriter) {
        long newWindowEndRow = row + height;
        long newWindowEndColumn = column + width;
        List<Window> intersectionWindows = getIntersectionWindows(windows, column, row, newWindowEndRow,
                newWindowEndColumn, windowToResize);
        if (!intersectionWindows.isEmpty() || newWindowEndRow < 0 || newWindowEndRow > totalHeight
                || newWindowEndColumn < 0 || newWindowEndColumn > totalWidth) {
            outputWriter.printLine("Command " + commandId + ": " + command + " - window does not fit");
        } else {
            if (windowToResize != null) {
                windowToResize.width = width;
                windowToResize.height = height;
            } else {
                windows.add(new Window(row, column, width, height));
            }
        }
    }

    private static void moveWindow(List<Window> windows, long column, long row, int deltaX, int deltaY,
                                   long totalWidth, long totalHeight, int commandId, OutputWriter outputWriter) {
        Window windowToMove = getAssociatedWindow(windows, column, row);
        if (windowToMove == null) {
            outputWriter.printLine("Command " + commandId + ": MOVE - no window at given position");
            return;
        }
        boolean isHorizontalMove = deltaX != 0;
        int targetDistanceToMove = isHorizontalMove ? deltaX : deltaY;
        boolean isMoveNegative = targetDistanceToMove < 0;
        int actualDistanceMoved = 0;

        List<Window> movingWindows = new ArrayList<>();
        movingWindows.add(windowToMove);

        while (true) {
            IntersectionSearch intersectionSearch = getNearestIntersectionWindow(windows, movingWindows, deltaY, deltaX,
                    isHorizontalMove, isMoveNegative);
            long distanceToMove = intersectionSearch.moves;
            if (intersectionSearch.intersectionWindows.isEmpty()
                    || !canMove(movingWindows, isHorizontalMove, isMoveNegative, distanceToMove, totalWidth, totalHeight)) {
                break;
            }
            updateWindowPositions(movingWindows, isHorizontalMove, distanceToMove, isMoveNegative);
            actualDistanceMoved += distanceToMove;
            if (actualDistanceMoved == Math.abs(targetDistanceToMove)) {
                break;
            }
            movingWindows.addAll(intersectionSearch.intersectionWindows);
            if (isHorizontalMove) {
                if (isMoveNegative) {
                    deltaX = targetDistanceToMove + actualDistanceMoved;
                } else {
                    deltaX = targetDistanceToMove - actualDistanceMoved;
                }
            } else {
                if (isMoveNegative) {
                    deltaY = targetDistanceToMove + actualDistanceMoved;
                } else {
                    deltaY = targetDistanceToMove - actualDistanceMoved;
                }
            }
        }

        Window firstWindow = getFirstWindow(movingWindows, isHorizontalMove);
        Window lastWindow = getLastWindow(movingWindows, isHorizontalMove);
        if (actualDistanceMoved != Math.abs(targetDistanceToMove)) {
            long remainingMove = Math.abs(targetDistanceToMove) - actualDistanceMoved;
            long distanceToMove;
            if (isHorizontalMove) {
                if (isMoveNegative) {
                    distanceToMove = Math.min(remainingMove, firstWindow.startColumn);
                } else {
                    distanceToMove = Math.min(remainingMove, totalWidth - (lastWindow.startColumn + lastWindow.width));
                }
            } else {
                if (isMoveNegative) {
                    distanceToMove = Math.min(remainingMove, firstWindow.startRow);
                } else {
                    distanceToMove = Math.min(remainingMove, totalHeight - (lastWindow.startRow + lastWindow.height));
                }
            }
            updateWindowPositions(movingWindows, isHorizontalMove, distanceToMove, isMoveNegative);
            actualDistanceMoved += distanceToMove;
        }

        if (actualDistanceMoved != Math.abs(targetDistanceToMove)) {
            outputWriter.printLine("Command " + commandId + ": MOVE - moved " + actualDistanceMoved
                    + " instead of " + Math.abs(targetDistanceToMove));
        }
    }

    private static boolean canMove(List<Window> windows, boolean isHorizontalMove, boolean isMoveNegative, long moves,
                                   long totalWidth, long totalHeight) {
        for (Window window : windows) {
            if (isHorizontalMove) {
                if (isMoveNegative) {
                    if (window.startColumn - moves < 0) {
                        return false;
                    }
                } else {
                    if (window.startColumn + window.width + moves > totalWidth) {
                        return false;
                    }
                }
            } else {
                if (isMoveNegative) {
                    if (window.startRow - moves < 0) {
                        return false;
                    }
                } else {
                    if (window.startRow + window.height + moves > totalHeight) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static Window getFirstWindow(List<Window> windows, boolean horizontal) {
        long smallestRow = Long.MAX_VALUE;
        long smallestColumn = Long.MAX_VALUE;
        Window firstWindow = null;
        for (Window window : windows) {
            if (horizontal && window.startColumn < smallestColumn) {
                smallestColumn = window.startColumn;
                firstWindow = window;
            } else if (!horizontal && window.startRow < smallestRow) {
                smallestRow = window.startRow;
                firstWindow = window;
            }
        }
        return firstWindow;
    }

    private static Window getLastWindow(List<Window> windows, boolean horizontal) {
        long highestRow = Long.MIN_VALUE;
        long highestColumn = Long.MIN_VALUE;
        Window lastWindow = null;
        for (Window window : windows) {
            if (horizontal && window.startColumn + window.width > highestColumn) {
                highestColumn = window.startColumn + window.width;
                lastWindow = window;
            } else if (!horizontal && window.startRow + window.height > highestRow) {
                highestRow = window.startRow + window.height;
                lastWindow = window;
            }
        }
        return lastWindow;
    }

    private static long computeMoves(Window windowToMove, Window intersectionWindow, boolean isHorizontalMove,
                                     boolean isMoveNegative) {
        long distanceToMove;
        long intersectionWindowEndRow = intersectionWindow.startRow + intersectionWindow.height;
        long intersectionWindowEndColumn = intersectionWindow.startColumn + intersectionWindow.width;

        if (isHorizontalMove) {
            if (isMoveNegative) {
                distanceToMove = windowToMove.startColumn - intersectionWindowEndColumn;
            } else {
                distanceToMove = intersectionWindow.startColumn - (windowToMove.startColumn + windowToMove.width);
            }
        } else {
            if (isMoveNegative) {
                distanceToMove = windowToMove.startRow - intersectionWindowEndRow;
            } else {
                distanceToMove = intersectionWindow.startRow - (windowToMove.startRow + windowToMove.height);
            }
        }
        return distanceToMove;
    }

    private static IntersectionSearch getNearestIntersectionWindow(List<Window> windows, List<Window> movingWindows,
                                                                   int deltaY, int deltaX, boolean isHorizontalMove,
                                                                   boolean isMoveNegative) {
        Set<Window> nearestIntersectionWindows = new HashSet<>();
        long moves = Long.MAX_VALUE;
        for (Window windowToMove : movingWindows) {
            long windowToMoveStartRow;
            long windowToMoveEndRow;
            long windowToMoveStartColumn;
            long windowToMoveEndColumn;
            if (isHorizontalMove) {
                windowToMoveStartRow = windowToMove.startRow;
                windowToMoveEndRow = windowToMove.startRow + windowToMove.height;
                if (isMoveNegative) {
                    windowToMoveStartColumn = windowToMove.startColumn + deltaX;
                    windowToMoveEndColumn = windowToMove.startColumn + windowToMove.width;
                } else {
                    windowToMoveStartColumn = windowToMove.startColumn;
                    windowToMoveEndColumn = windowToMove.startColumn + windowToMove.width + deltaX;
                }
            } else {
                windowToMoveStartColumn = windowToMove.startColumn;
                windowToMoveEndColumn = windowToMove.startColumn + windowToMove.width;
                if (isMoveNegative) {
                    windowToMoveStartRow = windowToMove.startRow + deltaY;
                    windowToMoveEndRow = windowToMove.startRow + windowToMove.height;
                } else {
                    windowToMoveStartRow = windowToMove.startRow;
                    windowToMoveEndRow = windowToMove.startRow + windowToMove.height + deltaY;
                }
            }
            List<Window> intersectionWindows = getIntersectionWindows(windows, windowToMoveStartColumn,
                    windowToMoveStartRow, windowToMoveEndRow, windowToMoveEndColumn, windowToMove);

            for (Window intersectionWindow : intersectionWindows) {
                if (movingWindows.contains(intersectionWindow)) {
                    continue;
                }
                long movesToMake = computeMoves(windowToMove, intersectionWindow, isHorizontalMove, isMoveNegative);
                if (movesToMake <= moves) {
                    if (movesToMake < moves) {
                        nearestIntersectionWindows = new HashSet<>();
                    }
                    nearestIntersectionWindows.add(intersectionWindow);
                    moves = movesToMake;
                }
            }
        }
        return new IntersectionSearch(nearestIntersectionWindows, moves);
    }

    private static void updateWindowPositions(List<Window> movingWindows, boolean isHorizontalMove, long distanceToMove,
                                              boolean isMoveNegative) {
        for (Window window : movingWindows) {
            if (isHorizontalMove) {
                if (isMoveNegative) {
                    window.startColumn -= distanceToMove;
                } else {
                    window.startColumn += distanceToMove;
                }
            } else {
                if (isMoveNegative) {
                    window.startRow -= distanceToMove;
                } else {
                    window.startRow += distanceToMove;
                }
            }
        }
    }

    private static List<Window> getIntersectionWindows(List<Window> windows, long startColumn, long startRow,
                                                       long newWindowEndRow, long newWindowEndColumn, Window windowToSkip) {
        List<Window> intersectionWindow = new ArrayList<>();

        for (Window window : windows) {
            if (window == windowToSkip) {
                continue;
            }
            long endColumn = window.startColumn + window.width;
            long endRow = window.startRow + window.height;

            if (((window.startColumn <= startColumn && startColumn < endColumn)
                    || (window.startColumn < newWindowEndColumn && newWindowEndColumn < endColumn)
                    || (startColumn < window.startColumn && window.startColumn < newWindowEndColumn)
                    || (startColumn < endColumn && endColumn < newWindowEndColumn))
                    && ((window.startRow <= startRow && startRow < endRow)
                    || (window.startRow < newWindowEndRow && newWindowEndRow < endRow)
                    || (startRow < window.startRow && window.startRow < newWindowEndRow)
                    || (startRow < endRow && endRow < newWindowEndRow))) {
                intersectionWindow.add(window);
            }
        }
        return intersectionWindow;
    }

    private static Window getAssociatedWindow(List<Window> windows, long column, long row) {
        for (Window window : windows) {
            long endColumn = window.startColumn + window.width;
            long endRow = window.startRow + window.height;
            if (window.startColumn <= column && column < endColumn
                    && window.startRow <= row && row < endRow) {
                return window;
            }
        }
        return null;
    }

    private static void printFinalWindows(List<Window> windows, OutputWriter outputWriter) {
        outputWriter.printLine(windows.size() + " window(s):");
        for (Window window : windows) {
            outputWriter.printLine(window.startColumn + " " + window.startRow + " " + window.width
                    + " " + window.height);
        }
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
