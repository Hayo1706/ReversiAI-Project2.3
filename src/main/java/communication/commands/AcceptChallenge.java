package communication.commands;

import communication.GameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class AcceptChallenge extends Command {
    private int challengeNumber;

    public AcceptChallenge(GameClient client, int challengeNumber) {
        super(client);
        this.challengeNumber = challengeNumber;
    }


    @Override
    public void execute() {
        try {
            sendCommand("challenge", "accept", String.valueOf(challengeNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
