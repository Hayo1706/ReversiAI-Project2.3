package communication.states;

import communication.StrategicGameClient;
import org.json.JSONArray;

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
}
