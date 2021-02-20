package chapter2.section2.d.twod.array.manipulation.harder;

import java.util.*;

/**
 * Created by Rene Argento on 09/02/21.
 */
public class TheBlocksProblem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int blocks = scanner.nextInt();
        scanner.nextLine();
        Deque<Integer>[] stacks = new ArrayDeque[blocks];
        int[] parents = new int[blocks];

        for (int i = 0; i < blocks; i++) {
            stacks[i] = new ArrayDeque<>();
            stacks[i].push(i);
            parents[i] = i;
        }

        while (scanner.hasNext()) {
            String[] commandData = scanner.nextLine().split(" ");
            String command = commandData[0];
            if (command.equals("quit")) {
                break;
            }

            int block1 = Integer.parseInt(commandData[1]);
            int block2 = Integer.parseInt(commandData[3]);
            int parent1 = parents[block1];
            int parent2 = parents[block2];

            if (parent1 == parent2) {
                continue;
            }

            String command2 = commandData[2];

            if (command.equals("move")) {
                returnBlocksToOriginalPositions(stacks, parents, block1, parent1);

                if (command2.equals("onto")) {
                    returnBlocksToOriginalPositions(stacks, parents, block2, parent2);
                    stacks[parent2].push(block2);
                }
                parents[block1] = parent2;
                stacks[parent2].push(block1);
            } else {
                if (command2.equals("onto")) {
                    returnBlocksToOriginalPositions(stacks, parents, block2, parent2);
                    stacks[parent2].push(block2);
                }
                moveStack(stacks, parents, block1, parent1, parent2);
            }
        }
        printBlocks(stacks);
    }

    private static void returnBlocksToOriginalPositions(Deque<Integer>[] stacks, int[] parent, int block,
                                                        int parentBlock) {
        Deque<Integer> stack = stacks[parentBlock];

        while (!stack.isEmpty() && stack.peek() != block) {
            int childBlock = stack.pop();
            parent[childBlock] = childBlock;
            stacks[childBlock].push(childBlock);
        }
        stack.pop();
    }

    private static void moveStack(Deque<Integer>[] stacks, int[] parent, int block1, int parent1, int parent2) {
        Deque<Integer> auxStack = new ArrayDeque<>();
        Deque<Integer> parent1Stack = stacks[parent1];

        while (!parent1Stack.isEmpty() && parent1Stack.peek() != block1) {
            int block = parent1Stack.pop();
            auxStack.push(block);
        }
        auxStack.push(parent1Stack.pop());

        while (!auxStack.isEmpty()) {
            int block = auxStack.pop();
            stacks[parent2].push(block);
            parent[block] = parent2;
        }
    }

    private static void printBlocks(Deque<Integer>[] stacks) {
        for (int i = 0; i < stacks.length; i++) {
            LinkedList<Integer> blocks = new LinkedList<>();

            while (!stacks[i].isEmpty()) {
                blocks.addFirst(stacks[i].pop());
            }

            StringJoiner joiner = new StringJoiner(" ");
            for (int block : blocks) {
                joiner.add(String.valueOf(block));
            }

            System.out.print(i + ":");
            if (!blocks.isEmpty()) {
                System.out.print(" " + joiner);
            }
            System.out.println();
        }
    }
}
