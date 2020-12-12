package chapter1.section6.m.input.parsing.iterative;

import java.util.*;

/**
 * Created by Rene Argento on 10/12/20.
 */
public class GenealogicalResearch {

    private static class Person {
        String name;
        String birth;
        String death;
        List<String> ancestors;
        List<String> descendants;

        public Person(String name, String birth, List<String> ancestors) {
            this.name = name;
            this.birth = birth;
            this.ancestors = ancestors;
            descendants = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Person> personMap = new HashMap<>();
        boolean isFirstPrint = true;

        while (true) {
            String line = scanner.nextLine();
            String[] values = line.split(":");

            if (values[0].charAt(0) == 'Q') {
                break;
            }

            List<String> commandAndName = getCommandAndName(values[0]);
            String command = commandAndName.get(0);
            String name = commandAndName.get(1);

            switch (command) {
                case "BIRTH":
                    String birthDate = values[1].trim();
                    String parent1 = values[2].trim();
                    String parent2 = values[3].trim();

                    List<String> ancestors = new ArrayList<>();
                    ancestors.add(parent1);
                    ancestors.add(parent2);
                    Person person = new Person(name, birthDate, ancestors);
                    personMap.put(name, person);

                    if (personMap.containsKey(parent1)) {
                        personMap.get(parent1).descendants.add(name);
                    }
                    if (personMap.containsKey(parent2)) {
                        personMap.get(parent2).descendants.add(name);
                    }
                    break;
                case "DEATH":
                    personMap.get(name).death = values[1].trim();
                    break;
                case "ANCESTORS":
                    if (isFirstPrint) {
                        isFirstPrint = false;
                    } else {
                        System.out.println();
                    }

                    System.out.printf("ANCESTORS of %s\n", name);
                    printRelatives(name, personMap, true);
                    break;
                case "DESCENDANTS":
                    if (isFirstPrint) {
                        isFirstPrint = false;
                    } else {
                        System.out.println();
                    }

                    System.out.printf("DESCENDANTS of %s\n", name);
                    printRelatives(name, personMap, false);
                    break;
            }
        }
    }

    private static void printRelatives(String name, Map<String, Person> personMap, boolean isAncestors) {
        Person person = personMap.get(name);

        List<String> relatives = isAncestors ? person.ancestors : person.descendants;
        if (relatives == null) {
            return;
        }

        Collections.sort(relatives);
        for (String relativeName : relatives) {
            printRelatives(relativeName, personMap, "  ", isAncestors);
        }
    }

    private static void printRelatives(String name, Map<String, Person> personMap, String prefix, boolean isAncestors) {
        System.out.print(prefix + name);

        if (!personMap.containsKey(name)) {
            System.out.println();
            return;
        }

        Person person = personMap.get(name);
        System.out.print(" " + person.birth + " -");

        if (person.death != null) {
            System.out.print(" " + person.death);
        }
        System.out.println();

        List<String> relatives = isAncestors ? person.ancestors : person.descendants;
        if (relatives == null) {
            return;
        }

        Collections.sort(relatives);
        for (String relativeName : relatives) {
            printRelatives(relativeName, personMap, prefix + "  ", isAncestors);
        }
    }

    private static List<String> getCommandAndName(String values) {
        String command = "";
        String name = "";

        StringBuilder commandCharacters = new StringBuilder();
        for (int i = 0; i < values.length(); i++) {
            char symbol = values.charAt(i);

            if (symbol == ' ') {
                command = commandCharacters.toString();
                name = values.substring(i + 1).trim();
                break;
            }
            commandCharacters.append(symbol);
        }

        List<String> commandAndName = new ArrayList<>();
        commandAndName.add(command);
        commandAndName.add(name);
        return commandAndName;
    }
}
