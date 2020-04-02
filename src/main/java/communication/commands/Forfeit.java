package communication.commands;

import communication.GameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Forfeit extends Command {

    public Forfeit(GameClient client) {
        super(client);
    }

    @Override
    public void execute() {
        try {
            sendCommand("forfeit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
