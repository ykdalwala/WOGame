import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Random2Guesser implements WheelOfFortunePlayer {
    private Random random = new Random();
    private Set<Character> guessedLetters = new HashSet<>();

    @Override
    public char nextGuess() {
        char guess;
        do {
            guess = (char) ('a' + random.nextInt(26));
        } while (guessedLetters.contains(guess)); // Ensure no repeat guesses
        guessedLetters.add(guess);
        return guess;
    }

    @Override
    public String playerId() {
        return "SmartGuesser";
    }

    @Override
    public void reset() {
        guessedLetters.clear(); // Clear guessed letters at the start of each game
    }
}
