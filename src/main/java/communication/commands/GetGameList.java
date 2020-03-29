package communication.commands;

import communication.GameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class GetGameList extends Command {

    public GetGameList(GameClient client) {
        super(client);
    }

    @Override
    public void execute() {
        try {
            sendCommand("get", "gamelist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
