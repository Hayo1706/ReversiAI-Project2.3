package communication.states;

import communication.CommunicationManager;

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
    public void getGameList() {
        System.out.println("Need to login first!"); // Do you?
    }

}
