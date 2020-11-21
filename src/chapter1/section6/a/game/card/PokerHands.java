package chapter1.section6.a.game.card;

import java.util.*;

/**
 * Created by Rene Argento on 10/10/20.
 */
public class PokerHands {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String[] black = readCards(scanner);
            String[] white = readCards(scanner);
            int[] blackCardsFrequency = cardsFrequency(black);
            int[] whiteCardsFrequency = cardsFrequency(white);
            List<Integer> blackList = getCardValuesList(black);
            List<Integer> whiteList = getCardValuesList(white);

            String winner;
            List<Integer> blackPart1Score = getPartialScore1(black, blackCardsFrequency);
            List<Integer> whitePart1Score = getPartialScore1(white, whiteCardsFrequency);

            winner = compareValues(blackPart1Score, whitePart1Score);
            if (winner == null) {
                int blackFlush = isFlush(black);
                int whiteFlush = isFlush(white);
                if (blackFlush != -1 && whiteFlush != -1) {
                    winner = compareValues(blackList, whiteList);
                } else {
                    List<Integer> blackPart2Score = getPartialScore2(black, blackCardsFrequency);
                    List<Integer> whitePart2Score = getPartialScore2(white, whiteCardsFrequency);
                    winner = compareValues(blackPart2Score, whitePart2Score);
                    if (winner == null) {
                        List<Integer> blackTwoPairs = isTwoPairs(blackCardsFrequency);
                        List<Integer> whiteTwoPairs = isTwoPairs(whiteCardsFrequency);
                        if (blackTwoPairs.size() > 2 && whiteTwoPairs.size() > 2) {
                            winner = compareValues(blackTwoPairs, whiteTwoPairs);
                        } else if (blackTwoPairs.size() > 2) {
                            winner = "Black";
                        } else if (whiteTwoPairs.size() > 2) {
                            winner = "White";
                        } else {
                            List<Integer> blackPair = isPair(blackCardsFrequency);
                            List<Integer> whitePair = isPair(whiteCardsFrequency);
                            if (blackPair.size() > 0 && whitePair.size() > 0) {
                                winner = compareValues(blackPair, whitePair);
                            } else if (blackPair.size() > 0) {
                                winner = "Black";
                            } else if (whitePair.size() > 0) {
                                winner = "White";
                            } else {
                                winner = compareValues(blackList, whiteList);
                            }
                        }
                    }
                }
            }
            if (winner == null) {
                System.out.println("Tie.");
            } else {
                System.out.println(winner + " wins.");
            }
        }
    }

    private static String[] readCards(Scanner scanner) {
        String[] cards = new String[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = scanner.next();
        }
        return cards;
    }

    private static List<Integer> getPartialScore1(String[] cards, int[] cardsFrequency) {
        List<Integer> score = new ArrayList<>();
        score.add(isStraightFlush(cards));
        score.add(isFourOfAKind(cardsFrequency));
        score.add(isFullHouse(cardsFrequency));
        score.add(isFlush(cards));
        return score;
    }

    private static List<Integer> getPartialScore2(String[] cards, int[] cardsFrequency) {
        List<Integer> score = new ArrayList<>();
        score.add(isStraight(cards));
        score.add(isThreeOfAKind(cardsFrequency));
        return score;
    }

    private static int getValue(char charValue) {
        if (Character.isDigit(charValue)) {
            return Character.getNumericValue(charValue);
        }
        switch (charValue) {
            case 'T': return 10;
            case 'J': return 11;
            case 'Q': return 12;
            case 'K': return 13;
            default: return 14;
        }
    }

    private static int[] cardsFrequency(String[] cards) {
        int[] values = new int[15];
        for (String card : cards) {
            int value = getValue(card.charAt(0));
            values[value]++;
        }
        return values;
    }

    private static int isStraightFlush(String[] cards) {
        int isStraight = isStraight(cards);
        int isFlush = isFlush(cards);
        if (isStraight != -1 && isFlush != -1) {
            return isFlush;
        }
        return -1;
    }

    private static int getHighestCard(String[] cards) {
        int highestCard = -1;
        for (String card : cards) {
            int value = getValue(card.charAt(0));
            highestCard = Math.max(highestCard, value);
        }
        return highestCard;
    }

    private static int isFourOfAKind(int[] cardsFrequency) {
        for (int i = 0; i < cardsFrequency.length; i++) {
            if (cardsFrequency[i] == 4) {
                return i;
            }
        }
        return -1;
    }

    private static int isFullHouse(int[] cardsFrequency) {
        boolean has3OfSameValue = false;
        int fullHouseValue = -1;
        boolean hasPair = false;
        for (int i = 0; i < cardsFrequency.length; i++) {
            if (cardsFrequency[i] == 3) {
                has3OfSameValue = true;
                fullHouseValue = i;
            } else if (cardsFrequency[i] == 2) {
                hasPair = true;
            }
        }
        if (has3OfSameValue && hasPair) {
            return fullHouseValue;
        }
        return -1;
    }

    private static int isFlush(String[] cards) {
        boolean isFlush = true;
        for (int i = 1; i < cards.length; i++) {
            if (cards[i].charAt(1) != cards[i - 1].charAt(1)) {
                isFlush = false;
                break;
            }
        }
        if (isFlush) {
            return 1;
        }
        return -1;
    }

    private static int isStraight(String[] cards) {
        boolean isStraight = true;
        List<Integer> values = new ArrayList<>();
        for (String card : cards) {
            values.add(getValue(card.charAt(0)));
        }
        Collections.sort(values);
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) != values.get(i - 1) + 1) {
                isStraight = false;
                break;
            }
        }
        if (isStraight) {
            return getHighestCard(cards);
        }
        return -1;
    }

    private static int isThreeOfAKind(int[] cardsFrequency) {
        for (int i = 0; i < cardsFrequency.length; i++) {
            if (cardsFrequency[i] == 3) {
                return i;
            }
        }
        return -1;
    }

    private static List<Integer> isTwoPairs(int[] cardsFrequency) {
        List<Integer> values = new ArrayList<>();
        int pairsCount = 0;
        for (int i = 0; i < cardsFrequency.length; i++) {
            if (cardsFrequency[i] == 2) {
                values.add(i);
                pairsCount++;
            }
        }
        if (pairsCount == 2) {
            if (values.get(0) < values.get(1)) {
                int aux = values.get(0);
                values.set(0, values.get(1));
                values.set(1, aux);
            }
            for (int i = 0; i < cardsFrequency.length; i++) {
                if (cardsFrequency[i] == 1) {
                    values.add(i);
                    break;
                }
            }
        }
        return values;
    }

    private static List<Integer> isPair(int[] cardsFrequency) {
        boolean hasPair = false;
        int pairValue = -1;
        for (int i = 0; i < cardsFrequency.length; i++) {
            if (cardsFrequency[i] == 2) {
                hasPair = true;
                pairValue = i;
            }
        }
        List<Integer> values = new ArrayList<>();
        if (hasPair) {
            for (int i = 0; i < cardsFrequency.length; i++) {
                if (cardsFrequency[i] != 0 && cardsFrequency[i] != 2) {
                    values.add(i);
                }
            }
            values.sort(Collections.reverseOrder());
            values.add(0, pairValue);
        }
        return values;
    }

    private static String compareValues(List<Integer> values1, List<Integer> values2) {
        for (int i = 0; i < values1.size(); i++) {
            if (values1.get(i) > values2.get(i)) {
                return "Black";
            } else if (values1.get(i) < values2.get(i)) {
                return "White";
            }
        }
        return null;
    }

    private static List<Integer> getCardValuesList(String[] cards) {
        List<Integer> cardValues = new ArrayList<>();
        for (String card : cards) {
            cardValues.add(getValue(card.charAt(0)));
        }
        cardValues.sort(Collections.reverseOrder());
        return cardValues;
    }
}
