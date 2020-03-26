package communication.commands;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dylan Hiemstra
 */
public abstract class Command {

    private DataOutputStream outputStream;

    public Command(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public abstract void execute();

    protected void sendCommand(String... arguments) throws IOException {
        System.out.println("Executing: " + Arrays.toString(arguments));

        for(String argument : arguments) {
            for(char letter : argument.toCharArray()) {
                outputStream.writeByte((byte) letter);
            }

            outputStream.writeByte((byte) ' ');
        }

        outputStream.writeByte((byte) '\n');
    }
}
