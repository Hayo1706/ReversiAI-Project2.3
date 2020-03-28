package communication.states;

import communication.CommunicationManager;
import org.json.JSONArray;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Connected extends CommunicationState {
    public Connected(CommunicationManager communication) {
        super(communication);
    }

    @Override
    public void connect(String host, Integer port) throws IOException {
        System.out.println("Already connected");
    }

    @Override
    public void login(String username) {
        communication.login(username);
    }

    @Override
    public JSONArray getGameList() { return communication.getGameList(); }

    @Override
    public JSONArray getPlayerList() { return communication.getPlayerList(); }
}
