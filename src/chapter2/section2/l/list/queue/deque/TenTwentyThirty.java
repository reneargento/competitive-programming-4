package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/04/21.
 */
public class TenTwentyThirty {

    @SuppressWarnings("unchecked")
    private static class GameState {
        ArrayDeque<Integer>[] piles;
        Queue<Integer> deck;

        public GameState(Queue<Integer> deck) {
            piles = new ArrayDeque[7];
            for (int i = 0; i < piles.length; i++) {
                piles[i] = new ArrayDeque<>();
            }
            this.deck = deck;
        }

        GameState copy() {
            GameState copy = new GameState(null);
            for (int i = 0; i < piles.length; i++) {
                copy.piles[i] = new ArrayDeque<>(piles[i]);
            }
            copy.deck = new LinkedList<>(deck);
            return copy;
        }

        boolean isEqual(GameState otherGameState) {
            for (int i = 0; i < piles.length; i++) {
                ArrayDeque<Integer>[] otherPiles = otherGameState.piles;

                if (piles[i].size() != otherPiles[i].size()
                        || deck.size() != otherGameState.deck.size()) {
                    return false;
                }

                ArrayDeque<Integer> copy1 = new ArrayDeque<>(piles[i]);
                ArrayDeque<Integer> copy2 = new ArrayDeque<>(otherPiles[i]);

                while (!copy1.isEmpty()) {
                    int element1 = copy1.pop();
                    int element2 = copy2.pop();
                    if (element1 != element2) {
                        return false;
                    }
                }
            }
            return deck.equals(otherGameState.deck);
        }

        boolean isGameWon() {
            for (ArrayDeque<Integer> pile : piles) {
                if (!pile.isEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class GameResult {
        String result;
        int cardsDealt;

        public GameResult(String result, int cardsDealt) {
            this.result = result;
            this.cardsDealt = cardsDealt;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int firstCard = FastReader.nextInt();

        while (firstCard != 0) {
            Queue<Integer> cards = new LinkedList<>();
            cards.offer(firstCard);

            for (int i = 1; i < 52; i++) {
                cards.offer(FastReader.nextInt());
            }
            GameResult gameResult = playGame(cards);
            outputWriter.printLine(String.format("%-4s: %d", gameResult.result, gameResult.cardsDealt));

            firstCard = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static GameResult playGame(Queue<Integer> cards) {
        GameState gameState = new GameState(cards);

        // Initial cards
        for (int i = 0; i < 7; i++) {
            gameState.piles[i].push(gameState.deck.poll());
        }

        int dealtCards = 7;
        List<GameState> gameStatesHistory = new ArrayList<>();
        gameStatesHistory.add(gameState.copy());
        int currentPile = 0;

        while (true) {
            if (gameState.deck.isEmpty()) {
                return new GameResult("Loss", dealtCards);
            }

            while (gameState.piles[currentPile].isEmpty()) {
                currentPile++;
                currentPile %= 7;
            }
            int card = gameState.deck.poll();
            dealtCards++;
            ArrayDeque<Integer> pile = gameState.piles[currentPile];

            pile.addLast(card);
            while (pile.size() >= 3) {
                boolean has4PlusCards = pile.size() >= 4;
                boolean has5PlusCards = pile.size() >= 5;

                boolean removed = false;

                int firstCard = pile.removeFirst();
                int secondCard = pile.removeFirst();
                int lastCard = pile.removeLast();
                int secondLastCard = -1;
                int thirdLastCard = -1;

                if (cardsMatch(firstCard, secondCard, lastCard)) {
                    putCardsOnDeckBottom(gameState.deck, firstCard, secondCard, lastCard);
                    removed = true;
                } else {
                    if (has4PlusCards) {
                        secondLastCard = pile.removeLast();
                    } else {
                        secondLastCard = secondCard;
                    }

                    if (cardsMatch(firstCard, secondLastCard, lastCard)) {
                        putCardsOnDeckBottom(gameState.deck, firstCard, secondLastCard, lastCard);
                        if (has4PlusCards) {
                            pile.addFirst(secondCard);
                        }
                        removed = true;
                    } else {
                        if (has5PlusCards) {
                            thirdLastCard = pile.removeLast();
                        } else if (has4PlusCards) {
                            thirdLastCard = secondCard;
                        } else {
                            thirdLastCard = firstCard;
                        }

                        if (cardsMatch(thirdLastCard, secondLastCard, lastCard)) {
                            putCardsOnDeckBottom(gameState.deck, thirdLastCard, secondLastCard, lastCard);
                            if (has5PlusCards) {
                                pile.addFirst(secondCard);
                            }
                            if (has4PlusCards) {
                                pile.addFirst(firstCard);
                            }
                            removed = true;
                        }
                    }
                }

                if (!removed) {
                    // Re-add cards to the pile
                    if (has5PlusCards) {
                        pile.offer(thirdLastCard);
                    }
                    if (has4PlusCards) {
                        pile.offer(secondLastCard);
                    }
                    pile.addLast(lastCard);
                    pile.addFirst(secondCard);
                    pile.addFirst(firstCard);
                    break;
                }
            }

            if (isDraw(gameState, gameStatesHistory)) {
                return new GameResult("Draw", dealtCards);
            }
            if (gameState.isGameWon()) {
                return new GameResult("Win", dealtCards);
            }
            gameStatesHistory.add(gameState.copy());
            currentPile++;
            currentPile %= 7;
        }
    }

    private static boolean cardsMatch(int card1, int card2, int card3) {
        int sum = card1 + card2 + card3;
        return sum == 10 || sum == 20 || sum == 30;
    }

    private static void putCardsOnDeckBottom(Queue<Integer> deck, int card1, int card2, int card3) {
        deck.offer(card1);
        deck.offer(card2);
        deck.offer(card3);
    }

    private static boolean isDraw(GameState gameState, List<GameState> gameStatesHistory) {
        for (GameState previousGameState : gameStatesHistory) {
            if (gameState.isEqual(previousGameState)) {
                return true;
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
