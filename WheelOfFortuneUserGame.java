import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a user-based Wheel of Fortune game where players try to guess phrases.
 * Users can guess letters to reveal a hidden phrase within a limited number of incorrect guesses.
 */
public class WheelOfFortuneUserGame extends WheelOfFortune {
    private Scanner scanner = new Scanner(System.in);
    private String phrase;
    private StringBuilder hiddenPhrase;
    private List<Character> guessedLetters = new ArrayList<>();
    private int maxIncorrectGuesses = 5;
    private int remainingGuesses;
    private boolean firstGame = true;

    /**
     * Initializes a new instance of WheelOfFortuneUserGame and starts the first game.
     */
    public WheelOfFortuneUserGame() {
        super();
        resetGame();
    }

    /**
     * Resets the game for a new phrase, clears guessed letters, and resets remaining guesses.
     * This method also loads a new random phrase and sets up the hidden phrase representation.
     */
    private void resetGame() {
        guessedLetters.clear();
        remainingGuesses = maxIncorrectGuesses;
        phrase = randomPhrase();
        if (phrase != null) {
            hiddenPhrase = new StringBuilder(getHiddenPhrase(phrase, guessedLetters));
        }
    }

    /**
     * Plays a single game session of Wheel of Fortune. The player guesses letters until they either
     * reveal the entire phrase or exhaust their allowed incorrect guesses.
     *
     * @return a GameRecord containing the user's score and player ID.
     */
    @Override
    protected GameRecord play() {
        System.out.println("Welcome to Wheel of Fortune!");
        System.out.println("Try to guess the phrase: " + hiddenPhrase);

        while (remainingGuesses > 0 && hiddenPhrase.toString().contains("*")) {
            char guess = getGuess(previousGuesses());
            guessedLetters.add(guess);

            if (processGuess(guess, phrase, hiddenPhrase)) {
                System.out.println("Good guess! The phrase now: " + hiddenPhrase);
            } else {
                remainingGuesses--;
                System.out.println("Incorrect guess! Remaining guesses: " + remainingGuesses);
            }
        }

        if (remainingGuesses <= 0) {
            System.out.println("You lose! The phrase was: " + phrase);
        } else {
            System.out.println("Congratulations! You guessed the phrase: " + phrase);
        }

        int score = remainingGuesses * 10;
        return new GameRecord(score, "User");
    }

    /**
     * Builds a string of previously guessed letters for validation purposes.
     *
     * @return a string of previously guessed letters.
     */
    private String previousGuesses() {
        StringBuilder previous = new StringBuilder();
        for (char guess : guessedLetters) {
            previous.append(guess);
        }
        return previous.toString();
    }

    /**
     * Determines if the player can start a new game.
     * If it's not the first game, prompts the player to decide whether to continue.
     *
     * @return true if the player chooses to continue or it's the first game; false otherwise.
     */
    @Override
    protected boolean playNext() {
        if (firstGame) {
            firstGame = false;
            return true;
        } else {
            System.out.print("Play another game? (y/n): ");
            boolean playAgain = scanner.nextLine().trim().equalsIgnoreCase("y");
            if (playAgain && !phrases.isEmpty()) {
                resetGame();
            }
            return playAgain && !phrases.isEmpty();
        }
    }

    /**
     * Prompts the user to input a letter guess, validates the input, and ensures it hasn't been guessed already.
     *
     * @param previousGuesses a string of letters previously guessed by the player.
     * @return a valid, unique character guess entered by the player.
     */
    @Override
    protected char getGuess(String previousGuesses) {
        System.out.print("Enter your guess (one letter): ");
        String guessInput = scanner.nextLine().trim().toLowerCase();

        // Loop until we get a valid single, unguessed letter
        while (guessInput.length() != 1 || !Character.isLetter(guessInput.charAt(0)) || previousGuesses.contains(guessInput)) {
            if (guessInput.length() != 1) {
                System.out.print("Invalid input. Please enter only a single letter: ");
            } else if (!Character.isLetter(guessInput.charAt(0))) {
                System.out.print("Invalid input. Only letters are allowed: ");
            } else if (previousGuesses.contains(guessInput)) {
                System.out.print("Letter already guessed. Try a different letter: ");
            }
            guessInput = scanner.nextLine().trim().toLowerCase();
        }

        return guessInput.charAt(0);
    }

    /**
     * Main method to start and run the Wheel of Fortune game loop.
     * Allows the player to replay the game until they choose to stop.
     *
     * @param args command line arguments, not used in this program.
     */
    public static void main(String[] args) {
        boolean playAgain = true; // Initialize playAgain to true to enter the loop
        WheelOfFortuneUserGame game;

        while (playAgain) {
            game = new WheelOfFortuneUserGame();
            AllGamesRecord record = game.playAll();
            System.out.println(record);

            // Check if the user wants to play again
            playAgain = game.playNext(); // This updates playAgain based on user's input
        }
    }
}
