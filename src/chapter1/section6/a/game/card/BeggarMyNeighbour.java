package chapter1.section6.a.game.card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * Created by Rene Argento on 08/10/20.
 */
public class BeggarMyNeighbour {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String card = scanner.next();

        while (!card.equals("#")) {
            Deque<Character> player1Deck = new ArrayDeque<>();
            Deque<Character> player2Deck = new ArrayDeque<>();

            player2Deck.push(card.charAt(1));
            boolean player1Turn = true;

            for (int i = 0; i < 51; i++) {
                char nextCard = scanner.next().charAt(1);

                if (player1Turn) {
                    player1Deck.push(nextCard);
                } else {
                    player2Deck.push(nextCard);
                }
                player1Turn = !player1Turn;
            }

            int winner = 0;
            int cardsInDeck = 0;
            player1Turn = false;
            boolean gameOver = false;
            Deque<Character> heap = new ArrayDeque<>();

            while (true) {
                if (gameOver) {
                    break;
                }

                char currentCard;
                if (player1Turn) {
                    if (player1Deck.isEmpty()) {
                        winner = 2;
                        cardsInDeck = player2Deck.size();
                        break;
                    } else {
                        currentCard = player1Deck.pop();
                    }
                } else {
                    if (player2Deck.isEmpty()) {
                        winner = 1;
                        cardsInDeck = player1Deck.size();
                        break;
                    } else {
                        currentCard = player2Deck.pop();
                    }
                }

                heap.push(currentCard);

                if (isFaceCard(currentCard)) {

                    while (true) {
                        int cardsToPlay = cardsToPlay(currentCard);

                        if (player1Turn) {
                            boolean newFaceCard = false;

                            for (int i = 0; i < cardsToPlay; i++) {
                                if (player2Deck.isEmpty()) {
                                    winner = 1;
                                    cardsInDeck = player1Deck.size();
                                    gameOver = true;
                                    break;
                                }

                                char cardToPlaceInHeap = player2Deck.pop();
                                heap.push(cardToPlaceInHeap);

                                if (isFaceCard(cardToPlaceInHeap)) {
                                    currentCard = cardToPlaceInHeap;
                                    newFaceCard = true;
                                    player1Turn = !player1Turn;
                                    break;
                                }
                            }

                            if (gameOver) {
                                break;
                            }

                            if (!newFaceCard) {
                                moveHeapUnderDeck(heap, player1Deck);
                                player1Turn = false;
                                break;
                            }
                        } else {
                            boolean newFaceCard = false;

                            for (int i = 0; i < cardsToPlay; i++) {
                                if (player1Deck.isEmpty()) {
                                    winner = 2;
                                    cardsInDeck = player2Deck.size();
                                    gameOver = true;
                                    break;
                                }

                                char cardToPlaceInHeap = player1Deck.pop();
                                heap.push(cardToPlaceInHeap);

                                if (isFaceCard(cardToPlaceInHeap)) {
                                    currentCard = cardToPlaceInHeap;
                                    newFaceCard = true;
                                    player1Turn = !player1Turn;
                                    break;
                                }
                            }

                            if (gameOver) {
                                break;
                            }

                            if (!newFaceCard) {
                                moveHeapUnderDeck(heap, player2Deck);
                                player1Turn = true;
                                break;
                            }
                        }
                    }
                }

                player1Turn = !player1Turn;
            }

            System.out.printf("%d%3d\n", winner, cardsInDeck);
            card = scanner.next();
        }
    }

    private static boolean isFaceCard(char card) {
        return card == 'A' || card == 'K' || card == 'Q' || card == 'J';
    }

    private static int cardsToPlay(char card) {
        switch (card) {
            case 'A': return 4;
            case 'K': return 3;
            case 'Q': return 2;
            case 'J': return 1;
        }
        return 0;
    }

    private static void moveHeapUnderDeck(Deque<Character> heap, Deque<Character> deck) {
        while (!heap.isEmpty()) {
            deck.addLast(heap.pollLast());
        }
    }

}
