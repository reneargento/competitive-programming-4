#include <iostream>
#include <vector>
#include <queue>

using namespace std;

// C++ solution for TwoFourSixGreaaat
/**
 * Created by Rene Argento on 29/04/24.
 */
// Based on https://github.com/BrandonTang89/Competitive_Programming_4_Solutions/blob/main/Chapter_4_Graph/Special_Graphs/kattis_246greaaat.cpp

int INFINITE = 1e8;

void printSequence(vector<pair<int, int>> cheers, vector<int> parent, int targetEnthusiasm, int meetInTheMiddleNode) {
    vector<int> sequence;
    int currentEnthusiasm = meetInTheMiddleNode;

    while (currentEnthusiasm != 0) {
        sequence.emplace_back(parent[currentEnthusiasm] + 1);
        auto [deltaEnthusiasm, difficulty] = cheers[parent[currentEnthusiasm]];
        currentEnthusiasm -= deltaEnthusiasm;
    }

    currentEnthusiasm = targetEnthusiasm - meetInTheMiddleNode;
    while (currentEnthusiasm != 0) {
        sequence.emplace_back(parent[currentEnthusiasm] + 1);
        auto [deltaEnthusiasm, difficulty] = cheers[parent[currentEnthusiasm]];
        currentEnthusiasm -= deltaEnthusiasm;
    }

    cout<< sequence.size() << "\n";
    for (int cheerID : sequence) {
        cout<< cheerID << " ";
    }
    cout<< "\n";
}

void computeCheersSequence(vector<pair<int, int>> cheers, int targetEnthusiasm, int minimumCheer) {
    vector<int> distance;
    vector<int> distanceFromEnd;
    vector<int> parent;
    vector<queue<int>> nodesQueue;

    int maxNode = targetEnthusiasm - minimumCheer;
    distance.assign(maxNode + 1, INFINITE);
    distanceFromEnd.assign(maxNode + 1, INFINITE);
    parent.assign(maxNode + 1, -1);
    nodesQueue.assign(maxNode + 1, queue<int>());

    distance[0] = 0;
    parent[0] = -1;
    nodesQueue[0].emplace(0);

    int meetInTheMiddleNode = 0;
    // totalDistance = targetEnthusiasm + 1 ensures that parent array is properly updated in edge cases
    int minimumDistance = targetEnthusiasm + 1;

    for (int enthusiasm = 0; enthusiasm < nodesQueue.size(); enthusiasm += nodesQueue[enthusiasm].empty()) {
        if (nodesQueue[enthusiasm].empty()) {
            continue;
        }
        int currentNode = nodesQueue[enthusiasm].front();
        nodesQueue[enthusiasm].pop();

        if (distance[currentNode] >= minimumDistance
            || currentNode == targetEnthusiasm) {
            break;
        }
        if (enthusiasm > distance[currentNode]) {
            // Inferior vertex
            continue;
        }

        if (targetEnthusiasm > currentNode) {
            distanceFromEnd[targetEnthusiasm - currentNode] = distance[currentNode];
        }
        if (distanceFromEnd[currentNode] != INFINITE) {
            if (distance[currentNode] + distanceFromEnd[currentNode] < minimumDistance) {
                minimumDistance = distance[currentNode] + distanceFromEnd[currentNode];
                meetInTheMiddleNode = currentNode;
            }
            continue;
        }

        for (int cheerID = 0; cheerID < cheers.size(); cheerID++) {
            int candidateNode = currentNode + cheers[cheerID].first;
            if (candidateNode > maxNode) {
                break;
            }
            if (candidateNode <= 0) {
                continue;
            }

            int candidateEnthusiasm = distance[currentNode] + cheers[cheerID].second;
            if (candidateEnthusiasm >= distance[candidateNode]
                || candidateEnthusiasm > minimumDistance) {
                continue;
            }

            if (distanceFromEnd[candidateNode] != INFINITE
                && distanceFromEnd[candidateNode] + candidateEnthusiasm > minimumDistance) {
                continue;
            }

            distance[candidateNode] = candidateEnthusiasm;
            parent[candidateNode] = cheerID;
            nodesQueue[candidateEnthusiasm].emplace(candidateNode);
        }
    }
    printSequence(cheers, parent, targetEnthusiasm, meetInTheMiddleNode);
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int nonStandardCheers;
    int targetEnthusiasm;
    int minimumCheer = 0;
    cin>> nonStandardCheers;
    cin>> targetEnthusiasm;

    vector<pair<int, int>> cheers;
    cheers.reserve(nonStandardCheers + 1);
    cheers.emplace_back(1, 1);

    for (int i = 1; i <= nonStandardCheers; i++) {
        int deltaEnthusiasm;
        int difficulty;
        cin>> deltaEnthusiasm;
        cin>> difficulty;

        // Prevent self-loops
        if (deltaEnthusiasm == 0) {
            difficulty = INFINITE;
        }
        cheers.emplace_back(deltaEnthusiasm, difficulty);
        minimumCheer = min(minimumCheer, deltaEnthusiasm);
    }
    computeCheersSequence(cheers, targetEnthusiasm, minimumCheer);
}