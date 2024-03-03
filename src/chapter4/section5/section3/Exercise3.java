package chapter4.section5.section3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 22/02/24.
 */
public class Exercise3 {

    public static void main(String[] args) {
        double[][] distances = {
                { 0, 15.1, 4.2, 10.2, 20.5 },
                { 1.1, 0, 10.3, 12.2, 2.2 },
                { 0.21, 10.9, 0, 4.2, 5 },
                { 2.2, 1.4, 12.2, 0, 5.1 },
                { 8.1, 1.1, 5, 2.2, 0 }
        };
        List<Integer> sourceVertices = new ArrayList<>();
        sourceVertices.add(0);
        sourceVertices.add(3);

        floydWarshall(distances, sourceVertices);
        for (int sourceVertex : sourceVertices) {
            System.out.print("Distances from " + sourceVertex + ":");
            for (int otherVertex = 0; otherVertex < distances.length; otherVertex++) {
                System.out.print(" " + distances[sourceVertex][otherVertex]);
            }
            System.out.println();
        }
    }

    // O(V^2 * K), where K is the number of sources
    public static void floydWarshall(double[][] distances, List<Integer> sourceVertices) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 : sourceVertices) {
                for (int vertex3 = 0; vertex3 < distances.length; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
    }
}
