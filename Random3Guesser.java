import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Random3Guesser implements WheelOfFortunePlayer {
    private List<Character> commonLetters = new ArrayList<>(Arrays.asList(
            'e', 'a', 'o', 'i', 'n', 's', 'h', 'r', 't', 'l', 'd', 'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'));
    private List<Character> guessedLetters = new ArrayList<>();

    @Override
    public char nextGuess() {
        for (char letter : commonLetters) {
            if (!guessedLetters.contains(letter)) {
                guessedLetters.add(letter);
                return letter;
            }
        }
        return ' '; // Should never reach here; failsafe
    }

    @Override
    public String playerId() {
        return "SmarterGuesser";
    }

    @Override
    public void reset() {
        guessedLetters.clear(); // Clear guessed letters at the start of each game
    }
}
