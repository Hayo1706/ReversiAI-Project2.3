package communication.states;

import communication.StrategicGameClient;
import communication.events.*;
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
    public void subscribe(String game) {
        client.subscribe(game);
    }

    @Override
    public void challenge(String player, String game) {
        client.challenge(player, game);
    }

    @Override
    public void challenged(JSONObject data) {
        client.challenged(data);
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
        client.matchStarted(event);
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
