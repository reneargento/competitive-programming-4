package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.*;

/**
 * Created by Rene Argento on 07/11/20.
 */
public class ConsanguineCalculations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] data = scanner.nextLine().split(" ");
        int caseNumber = 1;

        while (!data[0].equals("E")) {
            data = clearInput(data);

            String parent1BloodType = null;
            String parent1RhFactor = null;
            String parent2BloodType = null;
            String parent2RhFactor = null;
            String childBloodType = null;
            String childRhFactor = null;

            if (data[0].length() == 2) {
                parent1BloodType = String.valueOf(data[0].charAt(0));
                parent1RhFactor = String.valueOf(data[0].charAt(1));
            } else if (data[0].length() == 3) {
                parent1BloodType = data[0].substring(0, 2);
                parent1RhFactor = String.valueOf(data[0].charAt(2));
            }

            if (data[1].length() == 2) {
                parent2BloodType = String.valueOf(data[1].charAt(0));
                parent2RhFactor = String.valueOf(data[1].charAt(1));
            } else if (data[1].length() == 3) {
                parent2BloodType = data[1].substring(0, 2);
                parent2RhFactor = String.valueOf(data[1].charAt(2));
            }

            if (data[2].length() == 2) {
                childBloodType = String.valueOf(data[2].charAt(0));
                childRhFactor = String.valueOf(data[2].charAt(1));
            } else if (data[2].length() == 3) {
                childBloodType = data[2].substring(0, 2);
                childRhFactor = String.valueOf(data[2].charAt(2));
            }

            if (parent1BloodType == null) {
                List<String> bloodTypes = getParentBloodTypes(parent2BloodType, childBloodType);
                if (bloodTypes == null) {
                    data[0] = "IMPOSSIBLE";
                } else {
                    data[0] = addRhFactorToParent(bloodTypes, parent2RhFactor, childRhFactor);
                }
            } else if (parent2BloodType == null) {
                List<String> bloodTypes = getParentBloodTypes(parent1BloodType, childBloodType);
                if (bloodTypes == null) {
                    data[1] = "IMPOSSIBLE";
                } else {
                    data[1] = addRhFactorToParent(bloodTypes, parent1RhFactor, childRhFactor);
                }
            } else {
                List<String> bloodTypes = getChildBloodTypes(parent1BloodType, parent2BloodType);
                data[2] = addRhFactor(bloodTypes, parent1RhFactor, parent2RhFactor);
            }
            System.out.printf("Case %d: %s %s %s\n", caseNumber, data[0], data[1], data[2]);

            data = scanner.nextLine().split(" ");
            caseNumber++;
        }
    }

    private static String[] clearInput(String[] input) {
        String[] data = new String[3];
        int dataIndex = 0;
        for (int i = 0; i < input.length; i++) {
            if (!input[i].equals("")) {
                data[dataIndex++] = input[i];
            }
        }
        return data;
    }

    private static List<String> getChildBloodTypes(String parent1BloodType, String parent2BloodType) {
        List<String> bloodTypes = new ArrayList<>();

        if (parent1BloodType.equals("O") && parent2BloodType.equals("O")) {
            bloodTypes.add("O");
        } else if ((parent1BloodType.equals("AB") && parent2BloodType.equals("O"))
                ||(parent1BloodType.equals("O") && parent2BloodType.equals("AB"))) {
            bloodTypes.add("A");
            bloodTypes.add("B");
        } else if (parent1BloodType.equals("AB") || parent2BloodType.equals("AB")) {
            bloodTypes = getABBloodTypes();
        } else if (parent1BloodType.equals("A") && parent2BloodType.equals("A")) {
            bloodTypes.add("A");
            bloodTypes.add("O");
        } else if (parent1BloodType.equals("B") && parent2BloodType.equals("B")) {
            bloodTypes.add("B");
            bloodTypes.add("O");
        } else if ((parent1BloodType.equals("A") && parent2BloodType.equals("O"))
                || (parent1BloodType.equals("O") && parent2BloodType.equals("A"))) {
            bloodTypes.add("A");
            bloodTypes.add("O");
        } else if ((parent1BloodType.equals("B") && parent2BloodType.equals("O"))
                || (parent1BloodType.equals("O") && parent2BloodType.equals("B"))) {
            bloodTypes.add("B");
            bloodTypes.add("O");
        } else {
            bloodTypes = getABBloodTypes();
            bloodTypes.add("O");
        }
        return bloodTypes;
    }

    private static String addRhFactor(List<String> bloodTypes, String parent1RhFactor, String parent2RhFactor) {
        boolean positive = !parent1RhFactor.equals("-") || !parent2RhFactor.equals("-");

        StringJoiner finalBloodTypes = new StringJoiner(", ");
        for (String bloodType : bloodTypes) {
            if (positive) {
                finalBloodTypes.add(bloodType + "+");
            }
            finalBloodTypes.add(bloodType + "-");
        }

        if (bloodTypes.size() > 1
                || (bloodTypes.size() == 1 && positive)) {
            return "{" + finalBloodTypes.toString() + "}";
        } else {
            return finalBloodTypes.toString();
        }
    }

    private static List<String> getParentBloodTypes(String otherParentBloodType, String childBloodType) {
        List<String> bloodTypes = new ArrayList<>();

        if (otherParentBloodType.equals("O")) {
            if (childBloodType.equals("O")) {
                bloodTypes.add("A");
                bloodTypes.add("B");
                bloodTypes.add("O");
            } else if (childBloodType.equals("AB")) {
                return null;
            } else if (childBloodType.equals("A")) {
                bloodTypes.add("A");
                bloodTypes.add("AB");
            } else if (childBloodType.equals("B")) {
                bloodTypes.add("B");
                bloodTypes.add("AB");
            }
        } else if (otherParentBloodType.equals("AB")) {
            if (childBloodType.equals("O")) {
                return null;
            } else if (childBloodType.equals("AB")) {
                bloodTypes = getABBloodTypes();
            } else {
                bloodTypes = getABBloodTypes();
                bloodTypes.add("O");
            }
        } else if (otherParentBloodType.equals("A")) {
            if (childBloodType.equals("O")) {
                bloodTypes.add("A");
                bloodTypes.add("B");
                bloodTypes.add("O");
            } else if (childBloodType.equals("A")) {
                bloodTypes = getABBloodTypes();
                bloodTypes.add("O");
            } else if (childBloodType.equals("AB")) {
                bloodTypes.add("B");
                bloodTypes.add("AB");
            } else if (childBloodType.equals("B")) {
                bloodTypes.add("B");
                bloodTypes.add("AB");
            }
        } else if (otherParentBloodType.equals("B")) {
            if (childBloodType.equals("O")) {
                bloodTypes.add("A");
                bloodTypes.add("B");
                bloodTypes.add("O");
            } else if (childBloodType.equals("B")) {
                bloodTypes = getABBloodTypes();
                bloodTypes.add("O");
            } else if (childBloodType.equals("AB")) {
                bloodTypes.add("A");
                bloodTypes.add("AB");
            } else if (childBloodType.equals("A")) {
                bloodTypes.add("A");
                bloodTypes.add("AB");
            }
        }
        return bloodTypes;
    }

    private static List<String> getABBloodTypes() {
        List<String> bloodTypes = new ArrayList<>();
        bloodTypes.add("A");
        bloodTypes.add("B");
        bloodTypes.add("AB");
        return bloodTypes;
    }

    private static String addRhFactorToParent(List<String> bloodTypes, String otherParentRhFactor, String childRhFactor) {
        boolean onlyPositive = otherParentRhFactor.equals("-") && childRhFactor.equals("+");

        StringJoiner finalBloodTypes = new StringJoiner(", ");
        for (String bloodType : bloodTypes) {
            if (onlyPositive) {
                finalBloodTypes.add(bloodType + "+");
            } else {
                finalBloodTypes.add(bloodType + "+");
                finalBloodTypes.add(bloodType + "-");
            }
        }
        if (bloodTypes.size() > 1
                || (bloodTypes.size() == 1 && !onlyPositive)) {
            return "{" + finalBloodTypes.toString() + "}";
        } else {
            return finalBloodTypes.toString();
        }
    }
}
