package communication;

import communication.events.Event;
import communication.events.ReceivedChallenge;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.util.Arrays;

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
                String[] command = line.split(" ");
                System.out.println("Received command: " + Arrays.toString(command));

                if(command.length > 3 && command[0].equals("SVR") && command[1].equals("GAME")) {
                    switch (command[2]) {
                        case "CHALLENGE":
                            if(command[3].equals("CANCELLED")) {
                                System.out.println("sent challenge canceled"); // THIS IS NOT ALWAYS SENT!!!!!
                                StrategicGameClient.getInstance().denyChallenge();
                            } else {
                                JSONObject data = new JSONObject(line.substring(line.indexOf('{')));
                                StrategicGameClient.getInstance().challenged(data);
                            }

                            break;
                        case "MATCH":
                            /**
                             * TODO:
                             *  - Sent event over event bus when we are in a match and update state
                             *  - Go back to listeningMode after game ends
                             */

                            break;
                    }
                }
            }
        } while (!Thread.interrupted());
    }
}
