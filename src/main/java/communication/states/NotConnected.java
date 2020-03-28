package communication.states;

import communication.StrategicGameClient;
import org.json.JSONArray;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class NotConnected extends CommunicationState {

    public NotConnected(StrategicGameClient client) {
        super(client);
    }

    @Override
    public void connect(String host, Integer port) throws IOException {
        client.connect(host, port);
    }

    @Override
    public void login(String username) {
        System.out.println("Not connected yet");
    }


    @Override
    public void logout() { System.out.println("Not connected!"); }

    @Override
    public JSONArray getGameList() {
        System.out.println("Not connected yet");
        return new JSONArray();
    }

    @Override
    public JSONArray getPlayerList() {
        System.out.println("Not connected yet");
        return new JSONArray();
    }
}
