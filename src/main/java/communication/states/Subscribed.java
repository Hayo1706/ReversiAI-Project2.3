package communication.states;

import communication.StrategicGameClient;
import org.json.JSONArray;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Subscribed extends CommunicationState {
    public Subscribed(StrategicGameClient client) {
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
        System.out.println("Need to be in normal mode first!");
    }

    @Override
    public JSONArray getGameList() {
        System.out.println("Need to be in normal mode first!");
        return null;
    }

    @Override
    public JSONArray getPlayerList() {
        System.out.println("Need to be in normal mode first!");
        return null;
    }

    @Override
    public void startWaiting() {
        System.out.println("Already in listening mode!");
    }

    @Override
    public void subscribe(String game) {
        System.out.println("Cannot subscribe, already subscribed!");
    }

    @Override
    public void challenge(String player, String game) {
        System.out.println("Cannot challenge, already subscribed!");
    }
}
