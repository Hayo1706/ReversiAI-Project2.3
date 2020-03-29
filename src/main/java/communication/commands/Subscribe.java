package communication.commands;

import communication.GameClient;
import communication.StrategicGameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Subscribe extends Command {
    String game;

    public Subscribe(GameClient client, String game) {
        super(client);
        this.game = game;
    }

    @Override
    public void execute() {
        try {
            sendCommand("subscribe", game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
