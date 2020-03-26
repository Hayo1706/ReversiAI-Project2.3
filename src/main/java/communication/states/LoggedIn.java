package communication.states;

import communication.CommunicationManager;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class LoggedIn extends CommunicationState {
    public LoggedIn(CommunicationManager communication) {
        super(communication);
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
    public void getGameList() {

    }
}
