package communication.events;

import org.json.JSONObject;

public class Win extends Event {
    private String playerOneScore;
    private String playerTwoScore;
    private String comment;

    public Win(JSONObject json) {
        playerOneScore = json.getString("PLAYERONESCORE");
        playerTwoScore = json.getString("PLAYERTWOSCORE");
        comment = json.getString("COMMENT");
    }

    public String getPlayerOneScore() {
        return playerOneScore;
    }

    public String getPlayerTwoScore() {
        return playerTwoScore;
    }

    public String getComment() {
        return comment;
    }
}
