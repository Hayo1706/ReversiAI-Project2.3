package communication.states;

import communication.StrategicGameClient;
import communication.events.*;
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
    public void logout() {
        System.out.println("Not logged in!");
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
    public void denyChallenge() {
        System.out.println("Need to be challenged!");
    }

    @Override
    public void matchStarted(MatchStarted event) {
        System.out.println("Not possible");
    }

    @Override
    public void yourTurn(YourTurn event) {

    }

    @Override
    public void move(Move event) {

    }

    @Override
    public void win(Win event) {

    }

    @Override
    public void loss(Loss event) {

    }

    @Override
    public void draw(Draw event) {

    }

    @Override
    public void doMove(int index) {

    }

    @Override
    public void forfeit() {

    }
}
