package chapter3.section2.section2;

/**
 * Created by Rene Argento on 21/08/21.
 */
// If we have to print out just one valid N-queens solution given N, we can do it based on the value of N % 6,
// as shown in the method getAnyNQueensSolution().
// Based on https://en.wikipedia.org/wiki/Eight_queens_puzzle#Existence_of_solutions
public class Exercise2 {

    private static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    private static Node getAnyNQueensSolution(int boardSize) {
        if (boardSize == 3) {
            return null;
        }

        boolean isRemainder2 = boardSize % 6 == 2;
        boolean isRemainder3 = boardSize % 6 == 3;

        Node rootNode = new Node(-1);

        Node endEvenNode = generateEvenList(rootNode, boardSize, isRemainder3);
        Node startOddNode = generateOddList(boardSize, isRemainder2, isRemainder3);

        endEvenNode.next = startOddNode;
        return rootNode.next;
    }

    private static Node generateEvenList(Node rootNode, int boardSize, boolean isRemainder3) {
        int value;

        if (!isRemainder3) {
            value = 2;
        } else if (4 <= boardSize) {
            value = 4;
        } else {
            Node node = new Node(2);
            rootNode.next = node;
            return node;
        }

        Node node = new Node(value);
        rootNode.next = node;

        value += 2;
        while (value <= boardSize) {
            node.next = new Node(value);
            node = node.next;
            value += 2;
        }

        if (isRemainder3) {
            node.next = new Node(2);
            node = node.next;
        }
        return node;
    }

    private static Node generateOddList(int boardSize, boolean isRemainder2, boolean isRemainder3) {
        Node startNode;
        Node node = null;
        int value;

        if (isRemainder2) {
            startNode = new Node(3);
            node = new Node(1);
            startNode.next = node;
            value = 7;
        } else if (isRemainder3) {
            if (boardSize < 5) {
                startNode = new Node(1);
                startNode.next = new Node(3);
                return startNode;
            } else {
                startNode = new Node(5);
                value = 7;
            }
        } else {
            startNode = new Node(1);
            node = new Node(3);
            startNode.next = node;
            value = 5;
        }

        while (value <= boardSize) {
            if (node == null) {
                node = new Node(value);
                startNode.next = node;
            } else {
                node.next = new Node(value);
                node = node.next;
            }
            value += 2;
        }

        if (isRemainder2 && 5 <= boardSize) {
            node.next = new Node(5);
        } else if (isRemainder3) {
            node.next = new Node(1);
            node.next.next = new Node(3);
        }
        return startNode;
    }

    public static void main(String[] args) {
        int[] boardSizes = { 15, 1000, 100000 };

        for (int boardSize : boardSizes) {
            System.out.println("Solution for N = " + boardSize);
            Node solution = getAnyNQueensSolution(boardSize);
            printSolution(solution, boardSize);
            System.out.println();
        }
    }

    private static void printSolution(Node solution, int boardSize) {
        if (solution == null) {
            System.out.println("There is no possible solution for N = " + boardSize);
            return;
        }

        while (solution != null) {
            System.out.print(solution.value);

            if (solution.next != null) {
                System.out.print(" ");
            }
            solution = solution.next;
        }
        System.out.println();
    }
}
