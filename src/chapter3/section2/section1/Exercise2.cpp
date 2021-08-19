#include <algorithm>
#include <vector>

using namespace std;

void printKOutOfCCombinations(int k, int c) {
    vector<int> taken(c, 0);
    for (int i = c - k; i < c; i++) {
        taken[i] = 1;
    }

    do {
        for (int i = 0; i < c; i++) {
            if (taken[i]) {
                printf("%d ", i);
            }
        }
        printf("\n");
    } while (next_permutation(taken.begin(), taken.end()));
}

int main() {
    printKOutOfCCombinations(3, 6);
}