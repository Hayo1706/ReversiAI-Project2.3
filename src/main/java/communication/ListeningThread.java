package communication;

import communication.events.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Dylan Hiemstra
 */
public class ListeningThread implements Runnable {
    private BlockingQueue<String> responses;
    private BufferedReader inputStream;

    public ListeningThread(BufferedReader inputStream, BlockingQueue<String> responses) {
        this.inputStream = inputStream;
        this.responses = responses;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                String line = inputStream.readLine();
                String[] command = line.split(" ");
                System.out.println("Received command: " + Arrays.toString(command));

                if (command.length > 3 && command[0].equals("SVR") && command[1].equals("GAME")) {
                    JSONObject data;

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
                } else {
                    responses.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
