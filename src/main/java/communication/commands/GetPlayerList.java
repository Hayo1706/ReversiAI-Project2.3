package communication.commands;

import java.io.IOException;

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
