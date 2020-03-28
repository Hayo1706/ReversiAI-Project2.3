package communication.commands;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class GetPlayerList extends Command {
    @Override
    public void execute() {
        try {
            sendCommand("get", "playerlist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
