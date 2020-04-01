package communication;

import communication.events.*;
import communication.states.CommunicationState;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;


/**
 * Created by Dylan Hiemstra
 */
public class StrategicGameClientProxy implements GameClient {
    private GameClient realGameClient;

    public StrategicGameClientProxy(GameClient realGameClient) {
        this.realGameClient = realGameClient;
    }

    @Override
    public void connect(String host, Integer port) throws IOException {
        realGameClient.getState().connect(host, port);
    }

    @Override
    public void login(String username) {
        realGameClient.getState().login(username);
    }

    @Override
    public void logout() {
        realGameClient.getState().logout();
    }

    @Override
    public JSONArray getGameList() {
        return realGameClient.getState().getGameList();
    }

    @Override
    public JSONArray getPlayerList() {
        return realGameClient.getState().getPlayerList();
    }

    @Override
    public void startWaiting() {
        realGameClient.getState().startWaiting();
    }

    @Override
    public void challenge(String player, String game) {
        realGameClient.getState().challenge(player, game);
    }

    @Override
    public void subscribe(String game) {
        realGameClient.getState().subscribe(game);
    }

    @Override
    public void challenged(JSONObject data) {
        realGameClient.getState().challenged(data);
    }

    @Override
    public void acceptChallenge(ReceivedChallenge event) {
        realGameClient.getState().acceptChallenge(event);
    }

    @Override
    public void denyChallenge() {
        realGameClient.getState().denyChallenge();
    }

    @Override
    public CommunicationState getState() {
        return realGameClient.getState();
    }

    @Override
    public void matchStarted(MatchStarted event) { realGameClient.getState().matchStarted(event); }

    @Override
    public void yourTurn(YourTurn event) {
        realGameClient.getState().yourTurn(event);
    }

    @Override
    public void move(Move event) {
        realGameClient.getState().move(event);
    }

    @Override
    public void win(Win event) {
        realGameClient.getState().win(event);
    }

    @Override
    public void draw(Draw event) {
        realGameClient.getState().draw(event);
    }

    @Override
    public void loss(Loss event) {
        realGameClient.getState().loss(event);
    }

    @Override
    public void doMove(int index) {
        realGameClient.getState().doMove(index);
    }

    @Override
    public void forfeit() {
        realGameClient.getState().forfeit();
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public Observable<Event> getEventBus() {
        return realGameClient.getEventBus();
    }

    @Override
    public BlockingQueue<Move> getMoveQueue() {
        return realGameClient.getMoveQueue();
    }

    @Override
    public BlockingQueue<Win> getWinQueue() { return realGameClient.getWinQueue(); }

    @Override
    public BlockingQueue<Loss> getLossQueue() { return realGameClient.getLossQueue(); }
}
