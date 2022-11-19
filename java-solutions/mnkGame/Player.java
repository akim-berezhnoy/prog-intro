package mnkGame;

public abstract class Player {
    final int id;
    final boolean isBot;

    public Player(int id, boolean isBot) {
        this.id = id;
        this.isBot = isBot;
    }

    public int getId() {
        return id;
    }

    public abstract Move move(Position position);
}
