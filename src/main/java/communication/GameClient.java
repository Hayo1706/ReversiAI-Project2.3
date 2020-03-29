package communication;

import communication.events.Event;
import communication.events.ReceivedChallenge;
import communication.states.CommunicationState;
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

    void startWaiting();

    void challenge(String player, String game);

    void subscribe(String game);

    void challenged(JSONObject data);

    void acceptChallenge(ReceivedChallenge event);

    void denyChallenge();

    CommunicationState getState();

    Connection getConnection();

    BlockingQueue<Event> getEventBus();
}
