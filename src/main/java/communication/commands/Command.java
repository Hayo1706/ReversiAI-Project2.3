package communication.commands;

import communication.StrategicGameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public abstract class Command {

    public abstract void execute();

    protected void sendCommand(String... arguments) throws IOException {
        StrategicGameClient.getInstance().getConnection().sendCommand(arguments);
    }
}
