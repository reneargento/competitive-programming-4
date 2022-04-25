#include <iostream>
#include<unordered_map>
#include <vector>
#include <set>
#include <algorithm>

using namespace std;

// C++ solution for Race
/**
 * Created by Rene Argento on 24/04/22.
 */
const int MAX_VALUE = 1000000000;
set<pair<int, int>> adjacencyList[200000];
int subtreeSizes[200000];
bool deleted [200000];

int dfsToGetPath(int lengthRequired, unordered_map<int, int> &lengthToEdgesMap,
                 vector<pair<int, int>> &entriesToAdd, int vertex, int parent,int currentLength,
                 int currentNumberOfEdges) {
    int bestPathNumberOfEdges = MAX_VALUE;

    for (pair<int, int> edge : adjacencyList[vertex]) {
        int neighbor = edge.first;
        if (neighbor != parent && !deleted[neighbor]) {
            int newLength = currentLength + edge.second;
            int newNumberOfEdges = currentNumberOfEdges + 1;
            pair<int, int> entry(newLength, newNumberOfEdges);
            entriesToAdd.push_back(entry);

            int lengthRemaining = lengthRequired - newLength;
            if (lengthRemaining == 0) {
                bestPathNumberOfEdges = min(bestPathNumberOfEdges, newNumberOfEdges);
            } else if (lengthRemaining > 0) {
                if (lengthToEdgesMap.count(lengthRemaining)) {
                    int totalNumberOfEdges = newNumberOfEdges + lengthToEdgesMap[lengthRemaining];
                    bestPathNumberOfEdges = min(bestPathNumberOfEdges, totalNumberOfEdges);
                }
                int pathNumberOfEdges = dfsToGetPath(lengthRequired,lengthToEdgesMap,entriesToAdd,
                                                     neighbor, vertex, newLength, newNumberOfEdges);
                bestPathNumberOfEdges = min(bestPathNumberOfEdges, pathNumberOfEdges);
            }
        }

        if (parent == -1) {
            for (pair<int, int> lengthAndNumberOfEdges : entriesToAdd) {
                int length = lengthAndNumberOfEdges.first;
                int numberOfEdges = lengthAndNumberOfEdges.second;
                if (!lengthToEdgesMap.count(length)
                    || lengthToEdgesMap[length] > numberOfEdges) {
                    lengthToEdgesMap[length] = numberOfEdges;
                }
            }
            entriesToAdd.clear();
        }
    }
    return bestPathNumberOfEdges;
}

int dfsToGetCentroid(int totalSize, int vertex, int parent) {
    for (pair<int,int> edge : adjacencyList[vertex]) {
        int neighbor = edge.first;
        if (neighbor != parent && !deleted[neighbor]) {
            if (subtreeSizes[neighbor] > totalSize / 2) {
                return dfsToGetCentroid(totalSize, neighbor, vertex);
            }
        }
    }
    return vertex;
}

void computeSubtreeSizes(int vertex, int parent) {
    subtreeSizes[vertex] = 1;
    for (pair<int,int> edge : adjacencyList[vertex]) {
        int neighbor = edge.first;
        if (neighbor != parent && !deleted[neighbor]) {
            computeSubtreeSizes(neighbor, vertex);
            subtreeSizes[vertex] += subtreeSizes[neighbor];
        }
    }
}

int getCentroid(int root) {
    computeSubtreeSizes(root, -1);
    int totalSize = subtreeSizes[root];
    return dfsToGetCentroid(totalSize, root, -1);
}

int process(int vertex, int lengthRequired) {
    int bestPathNumberOfEdges = MAX_VALUE;
    int centroid = getCentroid(vertex);
    deleted[centroid] = true;

    for (pair<int,int> edge : adjacencyList[centroid]) {
        int neighbor = edge.first;
        if (!deleted[neighbor]) {
            int childPathNumberOfEdges = process(neighbor, lengthRequired);
            bestPathNumberOfEdges = min(bestPathNumberOfEdges, childPathNumberOfEdges);
        }
    }

    unordered_map<int, int> lengthToEdgesMap;
    vector<pair<int, int>> entriesToAdd;
    int pathNumberOfEdges = dfsToGetPath(lengthRequired, lengthToEdgesMap,entriesToAdd,
                                         centroid, -1, 0, 0);

    deleted[centroid] = false;
    return min(bestPathNumberOfEdges, pathNumberOfEdges);
}

int centroidDecomposition(int lengthRequired) {
    return process(0, lengthRequired);
}

void buildAdjacencyLists(vector<vector<int>> &highways, vector<int> &highwayLengths) {
    for (unsigned int i = 0; i < highways.size(); i++) {
        int city1 = highways[i][0];
        int city2 = highways[i][1];
        adjacencyList[city1].insert({ city2, highwayLengths[i] });
        adjacencyList[city2].insert({ city1, highwayLengths[i] });
    }
}

int bestPath(int cities, int lengthRequired, vector<vector<int>> &highways, vector<int> &highwayLengths) {
    buildAdjacencyLists(highways, highwayLengths);
    return centroidDecomposition(lengthRequired);
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int cities;
    cin>> cities;
    int lengthRequired;
    cin>> lengthRequired;
    vector<vector<int>> highways(cities - 1);
    vector<int> highwayLengths(highways.size());

    for (unsigned int i = 0; i < highways.size(); i++) {
        int city1;
        cin>> city1;
        highways[i].push_back(city1);

        int city2;
        cin>> city2;
        highways[i].push_back(city2);

        int highwayLength;
        cin>> highwayLength;
        highwayLengths[i] = highwayLength;
    }

    int minimumNumberOfHighways = bestPath(cities, lengthRequired, highways, highwayLengths);
    if (minimumNumberOfHighways == MAX_VALUE) {
        cout << -1 << "\n";
    } else {
        cout << minimumNumberOfHighways << "\n";
    }
}