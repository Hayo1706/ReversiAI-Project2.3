package communication.events;

import communication.StrategicGameClient;
import org.json.JSONObject;

public class YourTurn extends Event {
    private String turnMessage;

    public YourTurn(JSONObject json) {
        turnMessage = json.getString("TURNMESSAGE");
    }

    public String getTurnMessage() {
        return turnMessage;
    }

    public void doMove(int index) {
        StrategicGameClient.getInstance().doMove(index);
    }
}
