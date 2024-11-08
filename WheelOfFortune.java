import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public abstract class WheelOfFortune extends Game {
    protected List<String> phrases;
    private Random random = new Random();

    public WheelOfFortune() {
        this.phrases = new ArrayList<>();
        loadPhrasesFromFile("WOFPhrases.txt");
    }

    // Load phrases from a file
    private void loadPhrasesFromFile(String fileName) {
        try {
            List<String> filePhrases = Files.readAllLines(Paths.get(fileName));
            phrases.addAll(filePhrases);
            System.out.println("Loaded phrases from file: " + filePhrases.size() + " phrases added.");
        } catch (IOException e) {
            System.err.println("Error reading phrases file: " + e.getMessage());
        }
    }

    // Abstract method play that needs to be implemented in subclasses
    protected abstract GameRecord play();

    // Abstract method playNext that also needs to be implemented
    protected abstract boolean playNext();

    // Method to get a random phrase
    protected String randomPhrase() {
        if (phrases.isEmpty()) {
            return null;
        }
        return phrases.remove(random.nextInt(phrases.size()));
    }

    // Example processGuess method
    protected boolean processGuess(char guess, String phrase, StringBuilder hiddenPhrase) {
        boolean found = false;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == guess) {
                hiddenPhrase.setCharAt(i, guess);
                found = true;
            }
        }
        return found;
    }

    // Example getHiddenPhrase method
    protected String getHiddenPhrase(String phrase, List<Character> guesses) {
        StringBuilder hidden = new StringBuilder();
        for (char c : phrase.toCharArray()) {
            if (Character.isLetter(c) && !guesses.contains(c)) {
                hidden.append('*');
            } else {
                hidden.append(c);
            }
        }
        return hidden.toString();
    }

    protected abstract char getGuess(String previousGuesses);
}
