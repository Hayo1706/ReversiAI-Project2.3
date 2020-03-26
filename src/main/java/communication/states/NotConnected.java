package communication.states;

import communication.CommunicationManager;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class NotConnected extends CommunicationState {

    public NotConnected(CommunicationManager communication) {
        super(communication);
    }

    @Override
    public void connect(String host, Integer port) throws IOException {
        communication.connect(host, port);
    }

    @Override
    public void login(String username) {
        System.out.println("Not connected yet");
    }

    @Override
    public void getGameList() {
        System.out.println("Not connected yet");
    }
}
