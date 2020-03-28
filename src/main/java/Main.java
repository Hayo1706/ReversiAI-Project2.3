import communication.CommunicationManager;
import javafx.application.Application;
import org.json.JSONArray;
import view.BoardSetup;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CommunicationManager.getInstance().connect("localhost", 7789);
            CommunicationManager.getInstance().login("Dylan");
            JSONArray gameList = CommunicationManager.getInstance().getGameList();

            for(Object game : gameList) {
                System.out.println((String) game);
            }

            JSONArray playerList = CommunicationManager.getInstance().getPlayerList();

            for(Object player : playerList) {
                System.out.println((String) player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Application.launch(BoardSetup.class);
    }
}
