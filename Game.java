public abstract class Game {
    public AllGamesRecord playAll() {
        AllGamesRecord allRecords = new AllGamesRecord();
        while (playNext()) {
            GameRecord record = play();
            allRecords.add(record);
        }
        return allRecords;
    }

    protected abstract GameRecord play();

    protected abstract boolean playNext();
}