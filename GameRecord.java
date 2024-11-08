public class GameRecord implements Comparable<GameRecord> {
    private int score;
    private String playerId;

    public GameRecord(int score, String playerId) {
        this.score = score;
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public int compareTo(GameRecord other) {
        return Integer.compare(this.score, other.score);
    }

    @Override
    public String toString() {
        return "Player: " + playerId + ", Score: " + score;
    }
}