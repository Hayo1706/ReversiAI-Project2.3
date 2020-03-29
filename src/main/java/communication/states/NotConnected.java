package communication.states;

import communication.StrategicGameClient;
import communication.events.ReceivedChallenge;
import org.json.JSONArray;
import org.json.JSONObject;

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

    @Override
    public void startWaiting() {
        System.out.println("Need to login first!");
    }

    @Override
    public void subscribe(String game) {
        System.out.println("Need to login first!");
    }

    @Override
    public void challenge(String player, String game) {
        System.out.println("Need to login first!");
    }

    @Override
    public void challenged(JSONObject data) {
        System.out.println("This should never happen");
    }

    @Override
    public void acceptChallenge(ReceivedChallenge event) {
        System.out.println("Need to be challenged!");
    }

    @Override
    public void denyChallenge(ReceivedChallenge event) {
        System.out.println("Need to be challenged!");
    }
}
