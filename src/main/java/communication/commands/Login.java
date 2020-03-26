package communication.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Dylan Hiemstra
 */
public class Login extends Command {
    private String username;

    public Login(DataOutputStream outputStream, String username) {
        super(outputStream);
        this.username = username;
    }

    @Override
    public void execute() {
        try {
            sendCommand("login", username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
