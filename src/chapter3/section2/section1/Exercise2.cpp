#include <iostream>
#include <algorithm>
#include <unordered_set>

using namespace std;

unordered_set<string> getKOutOfCCombinations(int k, int c) {
    int numbers[c];
    for (int i = 1; i <= c; i++) {
        numbers[i - 1] = i;
    }

    unordered_set<string> uniqueValues;
    do {
        string values;
        for (int i = 0; i < k; i++) {
            values += to_string(numbers[i]);
            if (i != k - 1) {
                values += " ";
            }
        }
        uniqueValues.insert(values);
    } while (next_permutation(numbers, numbers + c));

    return uniqueValues;
}

int main() {
    unordered_set<string> kCombinations = getKOutOfCCombinations(3, 6);

    for (const string& kCombination : kCombinations) {
        cout<< kCombination << "\n";
    }
}