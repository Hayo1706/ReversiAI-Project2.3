package communication;

import communication.events.*;
import communication.states.CommunicationState;;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Dylan Hiemstra
 */
public interface GameClient {
    void connect(String host, Integer port) throws IOException;

    void login(String username);

    void logout();

    JSONArray getGameList();

    JSONArray getPlayerList();

    void challenge(String player, String game);

    void subscribe(String game);

    void challenged(JSONObject data);

    void acceptChallenge(ReceivedChallenge event);

    void denyChallenge();

    void matchStarted(MatchStarted event);

    void yourTurn(YourTurn event);

    void move(Move event);

    void win(Win event);

    void draw(Draw event);

    void loss(Loss event);

    void doMove(int index);

    void forfeit();


    CommunicationState getState();

    Connection getConnection();

    Observable<Event> getEventBus();
}
