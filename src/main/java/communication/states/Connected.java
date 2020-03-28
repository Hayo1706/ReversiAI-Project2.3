package communication.states;

import communication.StrategicGameClient;
import org.json.JSONArray;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Connected extends CommunicationState {
    public Connected(StrategicGameClient client) {
        super(client);
    }

    @Override
    public void connect(String host, Integer port) throws IOException {
        System.out.println("Already connected");
    }

    @Override
    public void login(String username) {
        client.login(username);
    }

    @Override
    public JSONArray getGameList() { return client.getGameList(); }

    @Override
    public JSONArray getPlayerList() { return client.getPlayerList(); }
}
