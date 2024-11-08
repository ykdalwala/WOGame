public abstract class GuessingGame extends Game {
    protected int maxAttempts;

    public GuessingGame(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    // Method to start the guessing game
    protected abstract GameRecord play();

    // Abstract method to handle guesses (to be implemented by each game type)
    protected abstract boolean validateGuess(String guess);

    // playNext implementation to ensure that the game can continue
    @Override
    protected boolean playNext() {
        return maxAttempts > 0;
    }
}
