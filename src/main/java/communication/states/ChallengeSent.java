package communication.states;

import communication.StrategicGameClient;
import communication.events.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class ChallengeSent extends CommunicationState {
    public ChallengeSent(StrategicGameClient client) {
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
        System.out.println("Cannot subscribe, challenge sent!");
    }

    @Override
    public void challenge(String player, String game) {
        System.out.println("Already sent challenge!");
    }

    @Override
    public void challenged(JSONObject data) {
        System.out.println("Already sent a challenged. Can't be challenged by someone now!");
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
}
