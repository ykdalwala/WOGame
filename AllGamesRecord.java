import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllGamesRecord {
    private List<GameRecord> records;

    public AllGamesRecord() {
        records = new ArrayList<>();
    }

    public void add(GameRecord record) {
        records.add(record);
    }

    public double average() {
        int totalScore = 0;
        for (GameRecord record : records) {
            totalScore += record.getScore();
        }
        return records.isEmpty() ? 0.0 : (double) totalScore / records.size();
    }

    public double average(String playerId) {
        int totalScore = 0;
        int count = 0;
        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                totalScore += record.getScore();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) totalScore / count;
    }

    public List<GameRecord> highGameList(int n) {
        List<GameRecord> sortedRecords = new ArrayList<>(records);
        sortedRecords.sort(Collections.reverseOrder());
        return sortedRecords.subList(0, Math.min(n, sortedRecords.size()));
    }

    public List<GameRecord> highGameList(String playerId, int n) {
        List<GameRecord> playerRecords = new ArrayList<>();
        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                playerRecords.add(record);
            }
        }
        playerRecords.sort(Collections.reverseOrder());
        return playerRecords.subList(0, Math.min(n, playerRecords.size()));
    }
}
