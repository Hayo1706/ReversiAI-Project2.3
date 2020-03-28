package communication.commands;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class GetGameList extends Command {
    @Override
    public void execute() {
        try {
            sendCommand("get", "gamelist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
