package communication.commands;

import java.io.IOException;

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
