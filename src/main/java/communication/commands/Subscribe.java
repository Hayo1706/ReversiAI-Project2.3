package communication.commands;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Subscribe extends Command {
    String game;

    public Subscribe(String game) {
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
