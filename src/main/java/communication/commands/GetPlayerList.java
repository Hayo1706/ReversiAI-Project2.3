package communication.commands;

import communication.GameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class GetPlayerList extends Command {
    public GetPlayerList(GameClient client) {
        super(client);
    }

    @Override
    public void execute() {
        try {
            sendCommand("get", "playerlist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
