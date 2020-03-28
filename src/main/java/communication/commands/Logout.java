package communication.commands;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Logout extends Command {
    @Override
    public void execute() {
        try {
            sendCommand("quit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
