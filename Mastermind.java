import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the Mastermind code-breaking game where a player tries to guess
 * a color code sequence within a limited number of attempts.
 */
public class Mastermind extends GuessingGame {
    private String code;
    private int codeLength;
    private List<String> colors = Arrays.asList("Red", "Blue", "Green", "Yellow");

    /**
     * Constructs a Mastermind game instance with specified max attempts and code length.
     *
     * @param maxAttempts maximum number of attempts a player has to guess the code.
     * @param codeLength the length of the color code.
     */
    public Mastermind(int maxAttempts, int codeLength) {
        super(maxAttempts);
        this.codeLength = codeLength;
        this.code = loadCode("Mastermind.txt"); // Load the code from the file
    }

    /**
     * Loads the code series from the "Mastermind.txt" file. If the file is empty or not found,
     * generates a fallback code.
     *
     * @param fileName the name of the file containing the code.
     * @return the loaded or generated code series.
     */
    private String loadCode(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            if (line != null) {
                System.out.println("Code loaded from file: " + line.trim());
                return line.trim();
            } else {
                System.err.println("Error: The file is empty.");
            }
        } catch (IOException e) {
            System.err.println("Error loading code series: " + e.getMessage());
        }
        return generatebackCode(); // Fallback if the file is not found or empty
    }

    /**
     * Generates a fallback code sequence in case the code file is not found or empty.
     *
     * @return a default code sequence as a string.
     */
    private String generatebackCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            code.append(colors.get(i % colors.size())).append(" ");
        }
        return code.toString().trim();
    }

    /**
     * Executes a single playthrough of the Mastermind game, where the player attempts to
     * guess the code within the maximum number of attempts.
     *
     * @return a GameRecord representing the player's score and performance.
     */
    @Override
    protected GameRecord play() {
        int attempts = 0;
        System.out.println("Mastermind Codebreaker game started!");

        while (attempts < maxAttempts) {
            System.out.println("Enter your guess (e.g., R G Y B): ");
            String guess = new Scanner(System.in).nextLine().toUpperCase(); // Standardize input to uppercase

            if (validateGuess(guess)) {
                System.out.println("Congratulations! You've broken the code.");
                return new GameRecord(100 - attempts, "Player"); // Example scoring logic
            } else {
                System.out.println("Incorrect guess. Try again.");
            }
            attempts++;
        }
        System.out.println("Game over! The correct code was: " + code);
        return new GameRecord(0, "Player"); // Player ran out of attempts
    }

    /**
     * Validates the player's guess by comparing it to the actual code. Displays feedback
     * indicating the number of colors guessed in the correct position and incorrect position.
     *
     * @param guess the player's guessed code sequence.
     * @return true if the guess matches the code; false otherwise.
     */
    @Override
    protected boolean validateGuess(String guess) {
        String[] guessColors = guess.split(" ");
        String[] codeColors = code.split(" ");

        // Check if the player's guess has the correct number of colors
        if (guessColors.length != codeLength) {
            System.out.println("Error: Your guess must have exactly " + codeLength + " colors.");
            return false;
        }

        int correctPosition = 0; // Colors in correct position
        int correctColor = 0;    // Colors in wrong position

        boolean[] guessedPositions = new boolean[codeLength];
        boolean[] correctPositions = new boolean[codeLength];

        // First pass: check for correct colors in the correct positions
        for (int i = 0; i < codeLength; i++) {
            if (guessColors[i].equals(codeColors[i])) {
                correctPosition++;
                guessedPositions[i] = true;
                correctPositions[i] = true;
            }
        }

        // Second pass: check for correct colors in the wrong positions
        for (int i = 0; i < codeLength; i++) {
            if (!guessedPositions[i]) {  // Skip colors already correctly positioned
                for (int j = 0; j < codeLength; j++) {
                    if (!correctPositions[j] && guessColors[i].equals(codeColors[j])) {
                        correctColor++;
                        correctPositions[j] = true;
                        break;
                    }
                }
            }
        }

        // Provide feedback to the player
        System.out.println("Colors in correct position: " + correctPosition);
        System.out.println("Colors in wrong position: " + correctColor);

        return correctPosition == codeLength;
    }

    /**
     * The main method to start the Mastermind game and display the results.
     *
     * @param args command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        // Initialize Mastermind game
        Mastermind mastermindGame = new Mastermind(10, 4);
        AllGamesRecord mastermindRecord = mastermindGame.playAll();

        // Display results for Mastermind
        System.out.println("\nMastermind Game Results:");
        for (GameRecord gameRecord : mastermindRecord.highGameList(5)) {
            System.out.println(gameRecord);
        }

        System.out.println("\nAverage Score for Mastermind: " + mastermindRecord.average());
    }
}
