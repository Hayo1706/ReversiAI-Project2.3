package communication.states;

import communication.StrategicGameClient;
import communication.events.ReceivedChallenge;
import org.json.JSONArray;
import org.json.JSONObject;

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
    public void logout() { System.out.println("Not logged in!"); }

    @Override
    public JSONArray getGameList() { return client.getGameList(); }

    @Override
    public JSONArray getPlayerList() { return client.getPlayerList(); }

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
