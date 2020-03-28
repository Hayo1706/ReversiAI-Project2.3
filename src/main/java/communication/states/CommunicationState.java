package communication.states;

import communication.CommunicationManager;
import org.json.JSONArray;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public abstract class CommunicationState {
    protected CommunicationManager communication;

    public CommunicationState(CommunicationManager communication) {
        this.communication = communication;
    }

    public abstract void connect(String host, Integer port) throws IOException;
    public abstract void login(String username);
    public abstract JSONArray getGameList();
    public abstract JSONArray getPlayerList();
}
