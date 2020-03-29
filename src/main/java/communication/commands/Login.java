package communication.commands;

import communication.GameClient;
import communication.NotOKResponseException;
import communication.StrategicGameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Login extends Command {
    private String username;

    public Login(GameClient client, String username) {
        super(client);
        this.username = username;
    }

    @Override
    public void execute() {
        try {
            sendCommand("login", username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
