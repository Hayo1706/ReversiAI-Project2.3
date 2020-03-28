import communication.StrategicGameClient;
import javafx.application.Application;
import org.json.JSONArray;
import view.BoardSetup;
import view.GameClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        try {
//            StrategicGameClient.getInstance().connect("localhost", 7789);
//            StrategicGameClient.getInstance().login("Dylan");
//            JSONArray gameList = StrategicGameClient.getInstance().getGameList();
//
//            for(Object game : gameList) {
//                System.out.println((String) game);
//            }
//
//            JSONArray playerList = StrategicGameClient.getInstance().getPlayerList();
//
//            for(Object player : playerList) {
//                System.out.println((String) player);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        Application.launch(GameClient.class);
    }
}
