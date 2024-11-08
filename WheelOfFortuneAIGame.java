import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Represents an AI-based Wheel of Fortune game where multiple AI players
 * attempt to guess phrases from a provided file.
 */
public class WheelOfFortuneAIGame extends WheelOfFortune {
    private List<WheelOfFortunePlayer> players;
    private AllGamesRecord allGamesRecord;
    private List<String> originalPhrases;

    /**
     * Initializes the AI game, setting up AI players and loading phrases from a file.
     */
    public WheelOfFortuneAIGame() {
        this.players = Arrays.asList(new Random1Guesser(), new Random2Guesser(), new Random3Guesser());
        this.allGamesRecord = new AllGamesRecord();
        this.originalPhrases = loadPhrasesFromFile("WOFPhrases.txt");
    }

    /**
     * Loads phrases from a specified file into a list.
     *
     * @param fileName the name of the file containing phrases.
     * @return a list of phrases loaded from the file.
     */
    private List<String> loadPhrasesFromFile(String fileName) {
        List<String> phrases = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                phrases.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error loading phrases: " + e.getMessage());
        }
        return phrases;
    }

    /**
     * Placeholder for the abstract `play` method; this method is overridden
     * by the main play logic that accepts specific players and phrases.
     *
     * @return null as this method is not used in this implementation.
     */
    @Override
    protected GameRecord play() {
        return null; // Placeholder to fulfill abstract requirement
    }

    /**
     * Executes a game round for a specific player attempting to guess a specific phrase.
     *
     * @param player the AI player making guesses.
     * @param phrase the phrase the AI player tries to guess.
     * @return a GameRecord with the player's score.
     */
    protected GameRecord play(WheelOfFortunePlayer player, String phrase) {
        StringBuilder hiddenPhrase = new StringBuilder(getHiddenPhrase(phrase, new ArrayList<>()));
        List<Character> guesses = new ArrayList<>();
        int guessCount = 0;
        player.reset(); // Reset player state before starting a new game

        System.out.println("AI Player " + player.playerId() + " is guessing!");

        // Loop until the phrase is fully guessed or attempts are exhausted
        while (hiddenPhrase.toString().contains("*")) {
            char guess = player.nextGuess();
            guesses.add(guess);

            if (processGuess(guess, phrase, hiddenPhrase)) {
                System.out.println("Good guess! The phrase now: " + hiddenPhrase);
            } else {
                System.out.println("Incorrect guess.");
            }
            guessCount++;
        }

        int score = 100 - guessCount; // Example scoring: fewer guesses yield a higher score
        System.out.println("Debug: Player " + player.playerId() + " completed with score: " + score);
        return new GameRecord(score, player.playerId());
    }

    /**
     * Placeholder to fulfill the abstract `getGuess` method from the superclass.
     * The AI players provide their own guessing mechanisms.
     *
     * @param previousGuesses a string of previously guessed letters.
     * @return a character guessed by the player (for this implementation, returns first AI guesser's guess).
     */
    @Override
    protected char getGuess(String previousGuesses) {
        return players.get(0).nextGuess(); // Placeholder to meet method requirements
    }

    /**
     * Determines if there are more phrases to guess.
     *
     * @return true if there are unguessed phrases, false otherwise.
     */
    @Override
    protected boolean playNext() {
        return !originalPhrases.isEmpty();
    }

    /**
     * Plays through all phrases for each player independently, storing each game's record.
     *
     * @return an AllGamesRecord containing the results for all games played.
     */
    @Override
    public AllGamesRecord playAll() {
        for (WheelOfFortunePlayer player : players) {
            List<String> phrasesCopy = new ArrayList<>(originalPhrases);
            for (String phrase : phrasesCopy) {
                GameRecord record = play(player, phrase);
                allGamesRecord.add(record);
                System.out.println("Debug: Player " + player.playerId() + " finished with a score of: " + record.getScore());
            }
        }
        return allGamesRecord;
    }

    /**
     * Main method to initialize and run the AI Wheel of Fortune game,
     * print game statistics and high scores.
     *
     * @param args command-line arguments, not used in this program.
     */
    public static void main(String[] args) {
        WheelOfFortuneAIGame aiGame = new WheelOfFortuneAIGame();
        AllGamesRecord record = aiGame.playAll(); // Run the full game set for all players

        System.out.println("\nThe Average Score of All Games: " + record.average());
        System.out.println("High Scores by Player:");
        for (WheelOfFortunePlayer player : aiGame.players) {
            System.out.println(player.playerId() + ": " + record.highGameList(player.playerId(), 3));
        }
    }
}
