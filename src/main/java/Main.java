import communication.DebugBar;
import communication.StrategicGameClient;
import javafx.application.Application;
import org.json.JSONArray;
import view.BoardSetup;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        try {
//            StrategicGameClient.getInstance().getState().connect("localhost", 7789);
//            StrategicGameClient.getInstance().getState().login("Dylan");
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
//
//            StrategicGameClient.getInstance().getState().startWaiting();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Application.launch(DebugBar.class);

        Application.launch(BoardSetup.class);
    }
}
