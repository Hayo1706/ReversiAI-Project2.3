package reversi;


import communication.CommunicationManager;
import javafx.application.Application;
import reversi.view.BoardSetup;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        Application.launch(BoardSetup.class,args);

//        CommunicationManager communication = CommunicationManager.getInstance();
//        try {
//            communication.getState().connect("localhost", 7789);
//            communication.getState().login("yeey");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
