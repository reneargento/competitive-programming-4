package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/08/22.
 */
public class Assemble {

    private static class Component implements Comparable<Component> {
        String type;
        String name;
        int price;
        int quality;

        public Component(String type, String name, int price, int quality) {
            this.type = type;
            this.name = name;
            this.price = price;
            this.quality = quality;
        }

        @Override
        public int compareTo(Component other) {
            return Integer.compare(price, other.price);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Component[] components = new Component[FastReader.nextInt()];
            int budget = FastReader.nextInt();

            for (int i = 0; i < components.length; i++) {
                components[i] = new Component(FastReader.next(), FastReader.next(), FastReader.nextInt(),
                        FastReader.nextInt());
            }
            int minimumSelectedQuality = computeQuality(components, budget);
            outputWriter.printLine(minimumSelectedQuality);
        }
        outputWriter.flush();
    }

    private static int computeQuality(Component[] components, int budget) {
        int minimumSelectedQuality = 0;
        Map<String, List<Component>> typeToComponentsMap = buildTypeToComponentsMap(components);

        PriorityQueue<Component> componentsPQ = new PriorityQueue<>(new Comparator<Component>() {
            @Override
            public int compare(Component component1, Component component2) {
                return Integer.compare(component1.quality, component2.quality);
            }
        });
        Map<String, Integer> typeToIndexMap = new HashMap<>();

        for (String type : typeToComponentsMap.keySet()) {
            Component component = typeToComponentsMap.get(type).get(0);
            budget -= component.price;
            componentsPQ.offer(component);
            typeToIndexMap.put(type, 1);
        }

        while (!componentsPQ.isEmpty()) {
            Component minimumQualityComponent = componentsPQ.poll();
            minimumSelectedQuality = minimumQualityComponent.quality;
            List<Component> componentList = typeToComponentsMap.get(minimumQualityComponent.type);

            int typeIndex = typeToIndexMap.get(minimumQualityComponent.type);
            if (typeIndex < componentList.size()) {
                Component nextComponent = componentList.get(typeIndex);
                typeToIndexMap.put(minimumQualityComponent.type, typeIndex + 1);

                int budgetWithoutItem = budget + minimumQualityComponent.price;
                if (budgetWithoutItem >= nextComponent.price) {
                    if (nextComponent.quality > minimumQualityComponent.quality) {
                        budget = budgetWithoutItem - nextComponent.price;
                        componentsPQ.offer(nextComponent);
                    } else {
                        componentsPQ.offer(minimumQualityComponent);
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return minimumSelectedQuality;
    }

    private static Map<String, List<Component>> buildTypeToComponentsMap(Component[] components) {
        Map<String, List<Component>> typeToComponentsMap = new HashMap<>();

        for (Component component : components) {
            if (!typeToComponentsMap.containsKey(component.type)) {
                typeToComponentsMap.put(component.type, new ArrayList<>());
            }
            typeToComponentsMap.get(component.type).add(component);
        }

        for (String type : typeToComponentsMap.keySet()) {
            Collections.sort(typeToComponentsMap.get(type));
        }
        return typeToComponentsMap;
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
