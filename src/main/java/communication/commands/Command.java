package communication.commands;

import communication.GameClient;
import communication.StrategicGameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public abstract class Command {

    private GameClient client;

    public Command(GameClient client) {
        this.client = client;
    }

    public abstract void execute();

    protected void sendCommand(String... arguments) throws IOException {
        client.getConnection().sendCommand(arguments);
    }
}
