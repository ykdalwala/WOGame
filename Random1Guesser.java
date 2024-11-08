import java.util.Random;

public class Random1Guesser implements WheelOfFortunePlayer {
    private Random random = new Random();

    @Override
    public char nextGuess() {
        // Generate a random letter from 'a' to 'z'
        return (char) ('a' + random.nextInt(26));
    }

    @Override
    public String playerId() {
        return "DumbGuesser";
    }

    @Override
    public void reset() {
        // No reset logic needed for Random1Guesser
    }
}
