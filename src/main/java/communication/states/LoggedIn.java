package communication.states;

import communication.StrategicGameClient;
import communication.events.ReceivedChallenge;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class LoggedIn extends CommunicationState {
    public LoggedIn(StrategicGameClient client) {
        super(client);
    }

    @Override
    public void connect(String host, Integer port) throws IOException {
        System.out.println("Already connected");
    }

    @Override
    public void login(String username) {
        System.out.println("Already logged in");
    }

    @Override
    public void logout() {
        client.logout();
    }

    @Override
    public JSONArray getGameList() {
        return client.getGameList();
    }

    @Override
    public JSONArray getPlayerList() {
        return client.getPlayerList();
    }

    @Override
    public void startWaiting() {
        client.startWaiting();
    }

    @Override
    public void subscribe(String game) {
        System.out.println("Need to be in waiting mode first!");
    }

    @Override
    public void challenge(String player, String game) {
        System.out.println("Need to be in waiting mode first!");
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
    public void denyChallenge() {
        System.out.println("Need to be challenged!");
    }
}
