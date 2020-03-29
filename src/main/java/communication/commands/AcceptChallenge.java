package communication.commands;

import java.io.IOException;

public class AcceptChallenge extends Command {
    private int challengeNumber;

    public AcceptChallenge(int challengeNumber) {
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
