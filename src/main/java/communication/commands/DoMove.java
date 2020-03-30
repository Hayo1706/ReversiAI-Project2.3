package communication.commands;

import communication.GameClient;

import java.io.IOException;

/**
 * Created by Dylan Hiemstra
 */
public class DoMove extends Command {
    private int moveIndex;

    public DoMove(GameClient client, int moveIndex) {
        super(client);
        this.moveIndex = moveIndex;
    }


    @Override
    public void execute() {
        try {
            sendCommand("move", String.valueOf(moveIndex));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
