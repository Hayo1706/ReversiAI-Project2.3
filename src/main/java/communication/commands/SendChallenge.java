package communication.commands;

import communication.GameClient;
import communication.StrategicGameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class SendChallenge extends Command {
    String player;
    String game;

    public SendChallenge(GameClient client, String player, String game) {
        super(client);
        this.player = player;
        this.game = game;
    }

    @Override
    public void execute() {
        try {
            sendCommand("challenge", String.format("\"%s\" \"%s\"", player, game));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
