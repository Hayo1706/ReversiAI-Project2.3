package view;

import com.sun.webkit.Timer;
import communication.StrategicGameClient;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Model;

import java.io.IOException;

public class LoginView extends SceneView {
    public LoginView(GameClient client) {
        super(client);
    }

    private TextField nameField;
    private Label errorLabel;

    @Override
    public void CreateScene() {

        super.CreateScene();

        title.setText("Login");
        Label nameLabel = CreateLabel("Name:");
        Label ipLabel = CreateLabel("Ip:");
        Label portLabel = CreateLabel("Port:");


        nameField = new TextField();
        nameField.setPromptText("Peter");

        TextField ipField = new TextField();
        ipField.setPromptText("localhost");
        // miss later voor checking
//        ipField.textProperty().addListener((observableValue, oldString, newString) -> {
//            if (!newString.matches("^\\d+(\\.\\d+)*$")){
//                ipField.setText(newString.replace("[^\\d|\\.]", ""));
//            }
//        });


        TextField portField = new TextField();
        portField.setPromptText("7789");
        // miss later voor checking
//        portField.textProperty().addListener((observableValue, oldString, newString) -> {
//                if (!newString.matches("\\d*")){
//                    portField.setText(newString.replace("[^\\d]", ""));
//                }
//        });

        HBox nameBox = new HBox(nameLabel, nameField);
        nameBox.setAlignment(Pos.TOP_CENTER);
        HBox ipBox = new HBox(ipLabel, ipField, portLabel, portField);
        ipBox.setAlignment(Pos.TOP_CENTER);

        errorLabel = new Label();
        errorLabel.getStyleClass().clear();
        errorLabel.getStyleClass().add("error");

        Button loginButton = CreateButton("Login");
        loginButton.setOnMouseClicked(e -> {
            try {
                int Port = Integer.parseInt(portField.getText());

                StrategicGameClient.getInstance().connect(ipField.getText(), Port);
                StrategicGameClient.getInstance().login(nameField.getText());
            } catch (IOException exception) {
                errorLabel.setText("Cannot connect to ip and port");
                return;
            } catch (NumberFormatException exception) {
                errorLabel.setText("port must be an integer");
                return;
            }

            client.SwitchScene(GameClient.Scenes.GAMES);
        });

        Button localButton = CreateButton("play local as Dylan");
        localButton.setOnMouseClicked(e -> {
            try {
                StrategicGameClient.getInstance().connect("localhost", 7789);
                StrategicGameClient.getInstance().login("Dylan");
                Model.mode = Model.HUMAN_VS_SERVER;
                Model.username = "Dylan";
            } catch (IOException exception) {
                errorLabel.setText("Cannot connect to ip and port");
                return;
            }
            client.SwitchScene(GameClient.Scenes.GAMES);
        });

        Button humanvsaireversi = CreatePlayOfflineButton("Human vs ai reversi",Model.HUMAN_VS_AI,"Dylan",0);
        Button humanvsaitictactoe = CreatePlayOfflineButton("Human vs ai tictactoe",Model.HUMAN_VS_AI,"Dylan",1);
        Button humanvshumanreversi = CreatePlayOfflineButton("Human vs human reversi",Model.HUMAN_VS_HUMAN,"Dylan",0);
        Button humanvshumantictactoe = CreatePlayOfflineButton("Human vs human tic-tac-toe",Model.HUMAN_VS_HUMAN,"Dylan",1);

        rootVBox.getChildren().add(nameBox);
        rootVBox.getChildren().add(ipBox);
        rootVBox.getChildren().add(loginButton);
        rootVBox.getChildren().add(localButton);
        //temporary for testing
        rootVBox.getChildren().add(humanvsaireversi);
        rootVBox.getChildren().add(humanvsaitictactoe);
        rootVBox.getChildren().add(humanvshumanreversi);
        rootVBox.getChildren().add(humanvshumantictactoe);
    }

    private Button CreatePlayOfflineButton(String text, int mode, String name, int GameToPlay) {

        Button button = CreateButton(text);
        button.setOnMouseClicked(e -> PlayLocal(mode, name, GameToPlay));

        return button;
    }

    private void PlayLocal(int mode, String name, int GameToPlay) {
        Model.mode = mode;

        if (nameField.getText().isEmpty() || nameField.getText().isBlank()){
            errorLabel.setText("please fill in your name");
            return;
        }

        Model.username = name;
        client.StartGame(GameToPlay);
    }
}
