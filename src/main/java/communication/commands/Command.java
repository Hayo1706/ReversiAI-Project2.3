package communication.commands;

import communication.CommunicationManager;
import communication.NotOKResponseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Dylan Hiemstra
 */
public abstract class Command {

    public abstract void execute();

    protected void sendCommand(String... arguments) throws IOException {
        CommunicationManager.getInstance().getConnection().sendCommand(arguments);
    }
}
