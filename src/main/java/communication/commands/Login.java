package communication.commands;

import communication.NotOKResponseException;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class Login extends Command {
    private String username;

    public Login(String username) {
        super();
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
