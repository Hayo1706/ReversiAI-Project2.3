package view;

import com.sun.webkit.Timer;
import communication.StrategicGameClient;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Model;

import java.io.IOException;

public class LoginView extends SceneView {
    public LoginView(GameClient client) {
        super(client);
    }

    @Override
    public void CreateScene() {
        super.CreateScene();

        title.setText("Login");

        ComboBox<String> playMode = new ComboBox<>();
        playMode.getItems().addAll("Choose playmode", "Online", "Offline");
        playMode.setValue("Choose playmode");

        StackPane loginPane = new StackPane();
        loginPane.setAlignment(Pos.BASELINE_CENTER);

        VBox loginBox = CreateLogin();
        VBox offlineBox = CreateOffline();

        playMode.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(newValue);
            loginPane.getChildren().clear();
            switch (newValue) {
                default:
                    return;
                case "Online":
                    loginPane.getChildren().add(loginBox);
                    break;
                case "Offline":
                    loginPane.getChildren().add(offlineBox);
                    break;
            }
        });

        rootVBox.getChildren().add(playMode);
        rootVBox.getChildren().add(loginPane);
    }

    private VBox CreateLogin() {
        Label titleLabel = CreateLabel("Login:");

        Label nameLabel = CreateLabel("Name:");
        Label ipLabel = CreateLabel("Ip:");
        Label portLabel = CreateLabel("Port:");


        TextField nameField = new TextField();
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

        Label errorLabel = CreateLabel("");

        Button StartButton = CreateButton("Start");
        StartButton.setOnMouseClicked(e -> {
            try {
                String playerName = nameField.getText();
                if (playerName.isEmpty() || playerName.isBlank()){
                    errorLabel.setText("please fill in you names");
                    return;
                }

                int Port = Integer.parseInt(portField.getText());

                StrategicGameClient.getInstance().connect(ipField.getText(), Port);
                StrategicGameClient.getInstance().login(playerName);

                Model.username = playerName;

                client.SwitchScene(GameClient.Scenes.GAMESONLINE);
            } catch (IOException exception) {
                errorLabel.setText("Cannot connect to ip and port");
                return;
            } catch (NumberFormatException exception) {
                errorLabel.setText("port must be an integer");
                return;
            }
        });

        VBox loginVBox = new VBox(titleLabel, nameBox, ipBox, errorLabel, StartButton);
        loginVBox.setAlignment(Pos.TOP_CENTER);
        return loginVBox;
    }

    private VBox CreateOffline() {
        Label titleLabel = CreateLabel("Play offline");

        Label nameLabel1 = CreateLabel("player 1:");

        TextField nameField1 = new TextField();
        nameField1.setPromptText("player1");


        Label nameLabel2 = CreateLabel("player 2:");

        TextField nameField2 = new TextField();
        nameField2.setPromptText("player2");

        HBox nameBox1 = new HBox(nameLabel1, nameField1);
        nameBox1.setAlignment(Pos.TOP_CENTER);

        HBox nameBox2 = new HBox(nameLabel2, nameField2);
        nameBox2.setAlignment(Pos.TOP_CENTER);

        Label errorLabel = CreateLabel("");

        Button StartButton = CreateButton("Start");
        StartButton.setOnMouseClicked(e -> {
            String name1 = nameField1.getText();
            String name2 = nameField2.getText();

            if (name1.isBlank() || name1.isEmpty() ||
                    name2.isBlank() || name2.isEmpty()){
                errorLabel.setText("please fill in you names");
                return;
            }

            Model.username = name1;
//            Model.username = name1; How about name 2?

            client.SwitchScene(GameClient.Scenes.GAMESOFFLINE);
        });

        VBox offlineVBox = new VBox(titleLabel, nameBox1, nameBox2,errorLabel,StartButton);
        offlineVBox.setAlignment(Pos.TOP_CENTER);
        return offlineVBox;
    }
}
