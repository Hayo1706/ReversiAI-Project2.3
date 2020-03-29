package communication.commands;

import communication.GameClient;
import communication.StrategicGameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Logout extends Command {

    public Logout(GameClient client) {
        super(client);
    }

    @Override
    public void execute() {
        try {
            sendCommand("quit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
