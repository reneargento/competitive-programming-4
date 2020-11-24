package chapter1.section6.d.games.other.harder;

import java.util.*;

/**
 * Created by Rene Argento on 24/10/20.
 */
public class TheSpotGame {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Cell cell = (Cell) other;
            return row == cell.row &&
                    column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static class Game {
        Set<Cell> filledCells;

        public Game() {
            filledCells = new HashSet<>();
        }

        private void add(int row, int column) {
            filledCells.add(new Cell(row, column));
        }

        private void remove(int row, int column) {
            filledCells.remove(new Cell(row, column));
        }

        private boolean isTheSameGame(Game game) {
            if (filledCells.size() != game.filledCells.size()) {
                return false;
            }

            boolean hasSameCells = true;

            for (Cell cell : filledCells) {
                if (!game.filledCells.contains(cell)) {
                    hasSameCells = false;
                    break;
                }
            }
            return hasSameCells;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int boardSize = scanner.nextInt();

        while (boardSize != 0) {
            List<Game> previousGames = new ArrayList<>();
            Game game = new Game();
            int winner = -1;
            int winMove = -1;

            for (int i = 0; i < 2 * boardSize; i++) {
                int row = scanner.nextInt() - 1;
                int column = scanner.nextInt() - 1;
                char move = scanner.next().charAt(0);

                if (winner != -1) {
                    continue;
                }

                Game copyGame = copyGame(game);
                if (move == '+') {
                    copyGame.add(row, column);
                } else {
                    copyGame.remove(row, column);
                }

                List<Game> gameVariations = getGameVariations(copyGame, boardSize);
                if (containsAnyGame(previousGames, gameVariations)) {
                    winner = i % 2 == 0 ? 2 : 1;
                    winMove = i + 1;
                }
                previousGames.addAll(gameVariations);

                game = copyGame;
            }

            if (winner == -1) {
                System.out.println("Draw");
            } else {
                System.out.printf("Player %d wins on move %d\n", winner, winMove);
            }
            boardSize = scanner.nextInt();
        }
    }

    private static Game copyGame(Game game) {
        Game gameCopy = new Game();
        for (Cell cell : game.filledCells) {
            gameCopy.filledCells.add(new Cell(cell.row, cell.column));
        }
        return gameCopy;
    }

    private static List<Game> getGameVariations(Game game, int boardSize) {
        List<Game> gameVariations = new ArrayList<>();
        gameVariations.add(game);

        Game game2 = new Game();
        for (Cell cell : game.filledCells) {
            game2.filledCells.add(new Cell(cell.column, cell.row));
        }
        gameVariations.add(game2);

        Game game3 = new Game();
        for (Cell cell : game.filledCells) {
            game3.filledCells.add(new Cell(boardSize - cell.row - 1, cell.column));
        }
        gameVariations.add(game3);

        Game game4 = new Game();
        for (Cell cell : game.filledCells) {
            game4.filledCells.add(new Cell(cell.row, boardSize - cell.column - 1));
        }
        gameVariations.add(game4);

        return gameVariations;
    }

    private static boolean containsAnyGame(List<Game> previousGames, List<Game> gameVariations) {
        boolean containsAny = false;

        for (Game game : gameVariations) {
            for (Game previousGame : previousGames) {
                if (game.isTheSameGame(previousGame)) {
                    containsAny = true;
                    break;
                }
            }
            if (containsAny) {
                break;
            }
        }
        return containsAny;
    }
}
