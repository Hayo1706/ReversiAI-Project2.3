package communication.states;

import communication.StrategicGameClient;
import communication.events.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class InGame extends CommunicationState {
    public InGame(StrategicGameClient client) {
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
        System.out.println("Already waiting");
    }

    @Override
    public void subscribe(String game) {
        System.out.println("Already in match");
    }

    @Override
    public void challenge(String player, String game) {
        System.out.println("Already in match");
    }

    @Override
    public void challenged(JSONObject data) {
        System.out.println("Already in match");
    }

    @Override
    public void acceptChallenge(ReceivedChallenge event) {
        System.out.println("Already in match");
    }

    @Override
    public void denyChallenge() {
        System.out.println("Already in match");
    }

    @Override
    public void matchStarted(MatchStarted event) {
        System.out.println("Already in match");
    }

    @Override
    public void yourTurn(YourTurn event) {
        client.yourTurn(event);
    }

    @Override
    public void move(Move event) {
        client.move(event);
    }

    @Override
    public void win(Win event) {
        client.win(event);
    }

    @Override
    public void loss(Loss event) {
        client.loss(event);
    }

    @Override
    public void draw(Draw event) {
        client.draw(event);
    }

    @Override
    public void doMove(int index) {
        client.doMove(index);
    }
}
