import communication.CommunicationManager;
import javafx.application.Application;
import view.BoardSetup;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CommunicationManager.getInstance().connect("localhost", 7789);
            CommunicationManager.getInstance().login("Dylan");
        } catch (IOException e) {
            e.printStackTrace();
        }


        Application.launch(BoardSetup.class);
    }
}
