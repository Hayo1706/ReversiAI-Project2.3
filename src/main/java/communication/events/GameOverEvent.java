package communication.events;

public abstract class GameOverEvent extends Event {
    public abstract String getPlayerOneScore();
    public abstract String getPlayerTwoScore();
    public abstract String getComment();
}
