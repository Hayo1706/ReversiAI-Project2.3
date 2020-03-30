package communication.events;

import org.json.JSONObject;

public class MatchStarted extends Event {
    private String gameType;
    private String playerToMove;
    private String opponent;

    public MatchStarted(JSONObject json) {
        gameType = json.getString("GAMETYPE");
        playerToMove = json.getString("PLAYERTOMOVE");
        opponent = json.getString("OPPONENT");
    }

    public String getGameType() {
        return gameType;
    }

    public String getPlayerToMove() {
        return playerToMove;
    }

    public String getOpponent() {
        return opponent;
    }

    @Override
    public String toString() {
        return "MatchStarted{" +
                "gameType='" + gameType + '\'' +
                ", playerToMove='" + playerToMove + '\'' +
                ", opponent='" + opponent + '\'' +
                '}';
    }
}
