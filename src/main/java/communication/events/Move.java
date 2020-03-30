package communication.events;

import org.json.JSONObject;

public class Move extends Event {
    private String player;
    private String details;
    private String move;

    public Move(JSONObject json) {
        player = json.getString("PLAYER");
        details = json.getString("DETAILS");
        move = json.getString("MOVE");
    }

    public String getPlayer() {
        return player;
    }

    public String getDetails() {
        return details;
    }

    public String getMove() {
        return move;
    }
}
