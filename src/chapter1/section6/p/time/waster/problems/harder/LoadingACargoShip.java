package chapter1.section6.p.time.waster.problems.harder;

import java.util.*;

/**
 * Created by Rene Argento on 27/12/20.
 */
@SuppressWarnings("unchecked")
public class LoadingACargoShip {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testNumber = 1;

        while (scanner.hasNext()) {
            if (testNumber > 1) {
                System.out.println();
            }

            int weightCapacity = 0;
            int containers = scanner.nextInt();
            int[] maxWeight = new int[containers];
            for (int c = 0; c < containers; c++) {
                maxWeight[c] = scanner.nextInt();
                weightCapacity += maxWeight[c];
            }

            int totalPackageWeight = 0;
            int packagesNumber = scanner.nextInt();
            int[] packages = new int[packagesNumber];
            for (int p = 0; p < packages.length; p++) {
                packages[p] = scanner.nextInt();
                totalPackageWeight += packages[p];
            }

            int cargoWeight = 0;
            Deque<Integer>[] loadedPackages = createDeque(containers);
            for (int packageWeight : packages) {
                int containerIndex = getContainerIndexToLoad(loadedPackages, maxWeight, packageWeight);
                if (containerIndex != -1) {
                    loadedPackages[containerIndex].push(packageWeight);
                    maxWeight[containerIndex] -= packageWeight;
                    cargoWeight += packageWeight;
                } else {
                    break;
                }
            }

            printContainerContents(loadedPackages);
            System.out.println();
            System.out.printf("cargo weight: %d\n", cargoWeight);
            System.out.printf("unused weight: %d\n", weightCapacity - cargoWeight);
            System.out.printf("unloaded weight: %d\n", totalPackageWeight - cargoWeight);
            testNumber++;
        }
    }

    private static int getContainerIndexToLoad(Deque<Integer>[] loadedPackages, int[] maxWeight, int packageWeight) {
        List<Integer> containersWithLessPackages = new ArrayList<>();
        int minPackagesFound = Integer.MAX_VALUE;

        for (int i = 0; i < loadedPackages.length; i++) {
            if (loadedPackages[i].size() <= minPackagesFound) {
                if (loadedPackages[i].size() < minPackagesFound) {
                    containersWithLessPackages = new ArrayList<>();
                    minPackagesFound = loadedPackages[i].size();
                }
                containersWithLessPackages.add(i);
            }
        }

        int selectedContainer = 0;
        int maxWeightAvailability = 0;
        for (int containerIndex : containersWithLessPackages) {
            if (maxWeight[containerIndex] > maxWeightAvailability) {
                selectedContainer = containerIndex;
                maxWeightAvailability = maxWeight[containerIndex];
            }
        }

        if (maxWeight[selectedContainer] >= packageWeight) {
            return selectedContainer;
        } else {
            return -1;
        }
    }

    private static void printContainerContents(Deque<Integer>[] loadedPackages) {
        int rows = 0;
        for (Deque<Integer> container : loadedPackages) {
            rows = Math.max(rows, container.size());
        }

        for (int r = 0; r < rows; r++) {
            int rowNumber = rows - r;

            for (int c = 0; c < loadedPackages.length; c++) {
                if (c > 0) {
                    System.out.print(" ");
                }
                if (rowNumber == loadedPackages[c].size()) {
                    int packageWeight = loadedPackages[c].pop();
                    System.out.print(packageWeight);
                } else {
                    System.out.print(":");
                }
            }
            System.out.println();
        }

        for (int i = 0; i < 2; i++) {
            for (int c = 0; c < loadedPackages.length; c++) {
                if (c > 0) {
                    if (i == 0) {
                        System.out.print("=");
                    } else {
                        System.out.print(" ");
                    }
                }

                if (i == 0) {
                    System.out.print("=");
                } else {
                    System.out.print(c + 1);
                }
            }
            System.out.println();
        }
    }

    private static Deque<Integer>[] createDeque(int size) {
        Deque<Integer>[] deque = new ArrayDeque[size];
        for (int i = 0; i < size; i++) {
            deque[i] = new ArrayDeque<>();
        }
        return deque;
    }
}
