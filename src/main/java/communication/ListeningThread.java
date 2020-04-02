package communication;

import communication.events.*;
import org.json.JSONObject;

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
        while (!Thread.interrupted()) {
            if (connection.hasBytesToRead()) {
                String line = connection.readLine();
                String[] command = line.split(" ");
                System.out.println("Received command: " + Arrays.toString(command));


                JSONObject data;

                if (command.length > 3 && command[0].equals("SVR") && command[1].equals("GAME")) {
                    switch (command[2]) {
                        case "CHALLENGE":
                            if (command[3].equals("CANCELLED")) {
                                System.out.println("sent challenge canceled"); // THIS IS NOT ALWAYS SENT!!!!!
                                StrategicGameClient.getInstance().denyChallenge();
                            } else {
                                data = new JSONObject(line.substring(line.indexOf('{')));
                                StrategicGameClient.getInstance().challenged(data);
                            }

                            break;
                        case "MATCH":
                            data = new JSONObject(line.substring(line.indexOf('{')));
                            StrategicGameClient.getInstance().matchStarted(new MatchStarted(data));
                            break;
                        case "YOURTURN":
                             data = new JSONObject(line.substring(line.indexOf('{')));
                             StrategicGameClient.getInstance().yourTurn(new YourTurn(data));
                            break;
                        case "MOVE":
                            data = new JSONObject(line.substring(line.indexOf('{')));
                            StrategicGameClient.getInstance().move(new Move(data));
                            break;
                        case "WIN":
                            data = new JSONObject(line.substring(line.indexOf('{')));
                            StrategicGameClient.getInstance().win(new Win(data));
                            break;
                        case "DRAW":
                            data = new JSONObject(line.substring(line.indexOf('{')));
                            StrategicGameClient.getInstance().draw(new Draw(data));
                            break;
                        case "LOSS":
                            data = new JSONObject(line.substring(line.indexOf('{')));
                            StrategicGameClient.getInstance().loss(new Loss(data));
                            break;
                    }
                }
            }
        }
    }
}
