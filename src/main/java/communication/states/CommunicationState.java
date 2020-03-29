package communication.states;

import communication.StrategicGameClient;
import communication.events.ReceivedChallenge;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public abstract class CommunicationState {
    protected StrategicGameClient client;

    public CommunicationState(StrategicGameClient client) {
        this.client = client;
    }

    public abstract void connect(String host, Integer port) throws IOException;

    public abstract void login(String username);

    public abstract void logout();

    public abstract JSONArray getGameList();

    public abstract JSONArray getPlayerList();

    public abstract void startWaiting();

    public abstract void subscribe(String game);

    public abstract void challenge(String player, String game);

    public abstract void challenged(JSONObject data);

    public abstract void acceptChallenge(ReceivedChallenge event);

    public abstract void denyChallenge();
}
