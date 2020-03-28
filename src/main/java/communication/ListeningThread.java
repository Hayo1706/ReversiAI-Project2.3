package communication;

import java.io.DataInputStream;

/**
 * Created by Dylan Hiemstra
 */
public class ListeningThread implements Runnable {
    private Connection connection;


    public ListeningThread(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        do {
            if(connection.hasBytesToRead()) {
                String line = connection.readLine();
                // Parse line

                /**
                 * TODO:
                 *  - Send event over event bus when someone challenged the player and update state
                 *  - Sent event over event bus when we are in a match and update state
                 *  - Go back to listeningMode after game ends
                 */
            }
        } while (!Thread.interrupted());


        System.out.println("STOP");
    }
}
