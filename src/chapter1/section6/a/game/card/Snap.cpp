#include <iostream>

using namespace std;

/**
 * Created by Rene Argento on 05/10/20.
 */
 // Coded in c++ due to the call to random() function
void reverseStringInPlace(string &stringToReverse) {
    int numCharacters = stringToReverse.length();
    for (int i = 0; i < numCharacters / 2; i++) {
        swap(stringToReverse[i], stringToReverse[numCharacters - i - 1]);
    }
}

int main() {
    int tests;
    cin >> tests;

    for (int t = 0; t < tests; t++) {
        if (t > 0) {
            cout << "\n";
        }

        string janeCardsDown;
        cin >> janeCardsDown;
        string johnCardsDown;
        cin >> johnCardsDown;

        string janeCardsUp = "";
        string johnCardsUp = "";

        int play = 0;

        while (play < 1000
               && (janeCardsDown.length() + janeCardsUp.length() != 0)
               && (johnCardsDown.length() + johnCardsUp.length() != 0)) {
            if (janeCardsDown.length() == 0) {
                reverseStringInPlace(janeCardsUp);
                janeCardsDown = janeCardsUp;
                janeCardsUp = "";
            }
            if (johnCardsDown.length() == 0) {
                reverseStringInPlace(johnCardsUp);
                johnCardsDown = johnCardsUp;
                johnCardsUp = "";
            }

            char janeCard = janeCardsDown[0];
            char johnCard = johnCardsDown[0];

            janeCardsDown = janeCardsDown.substr(1);
            johnCardsDown = johnCardsDown.substr(1);

            janeCardsUp = janeCard + janeCardsUp;
            johnCardsUp = johnCard + johnCardsUp;

            if (janeCard == johnCard) {
                if (random() / 141 % 2) {
                    johnCardsUp = janeCardsUp + johnCardsUp;
                    janeCardsUp = "";

                    cout << "Snap! for John: " + johnCardsUp + "\n";
                } else {
                    janeCardsUp = johnCardsUp + janeCardsUp;
                    johnCardsUp = "";

                    cout << "Snap! for Jane: " + janeCardsUp + "\n";
                }
            }

            play++;
        }

        if (play == 1000) {
            cout << "Keeps going and going ...\n";
        } else if (janeCardsUp.length() == 0 && janeCardsDown.length() == 0) {
            cout << "John wins.\n";
        } else {
            cout << "Jane wins.\n";
        }
    }

    return 0;
}