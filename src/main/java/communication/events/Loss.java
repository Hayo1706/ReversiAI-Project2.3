package communication.events;

import org.json.JSONObject;

public class Loss extends GameOverEvent {
    private String playerOneScore;
    private String playerTwoScore;
    private String comment;

    public Loss(JSONObject json) {
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
