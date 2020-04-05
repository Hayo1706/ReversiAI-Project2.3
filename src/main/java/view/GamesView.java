package view;

import com.sun.javafx.scene.control.LabeledText;
import communication.StrategicGameClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Model;
import org.json.JSONArray;

import java.util.List;
import java.util.stream.Collectors;

public class GamesView extends SceneView {
    public GamesView(GameClient client) {
        super(client);
    }

    ObservableList<String> gamesList = FXCollections.observableArrayList();
    ObservableList<String> playerGameList = FXCollections.observableArrayList("mark", "peter", "balk");

    @Override
    public void CreateScene() {
        super.CreateScene();

        title.setText("available Games");

        Label aILabel = CreateLabel("Let the ai play?");
        CheckBox aICheckbox = new CheckBox();
        aICheckbox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue){
                Model.mode = Model.AI_VS_SERVER;
            }else{
                Model.mode = Model.HUMAN_VS_SERVER;
            }
        });

        HBox aIHBox = new HBox(aILabel,aICheckbox);
        aIHBox.setAlignment(Pos.CENTER);

        Label gamesListLabel = CreateLabel("Games available:");
        Label playerGameListLabel = CreateLabel("Players ready to play game:");

        ListView gamesListView = new ListView();
        gamesListView.setItems(gamesList);
        gamesListView.setOnMouseClicked(mouseEvent -> {
            StrategicGameClient.getInstance().subscribe(gamesListView.getSelectionModel().getSelectedItem().toString());
        });

        ListView playerGameListView = new ListView();
        playerGameListView.setItems(playerGameList);
        playerGameListView.setOnMouseClicked(mouseEvent -> {
            System.out.println(playerGameListView.getSelectionModel().getSelectedItem());
            var splitted = playerGameListView.getSelectionModel().getSelectedItem().toString().split(",");
            var player = splitted[0];
            var game = splitted[1];
            StrategicGameClient.getInstance().challenge(player, game);
        });

        VBox GamesVBox = new VBox(gamesListLabel, gamesListView);
        VBox playerVBox = new VBox(playerGameListLabel, playerGameListView);
        HBox hBox = new HBox(GamesVBox, playerVBox);
        hBox.setAlignment(Pos.TOP_CENTER);

        var refreshButton = CreateButton("refresh");
        refreshButton.setOnMouseClicked((e) -> UpdateListView());

        Button backButton = CreateButton("Back to login screen");
        backButton.setOnMouseClicked((e) -> client.SwitchScene(GameClient.Scenes.LOGIN));

        HBox buttonBox = new HBox(refreshButton,backButton);
        buttonBox.setAlignment(Pos.CENTER);

        rootVBox.getChildren().add(aIHBox);
        rootVBox.getChildren().add(hBox);
        rootVBox.getChildren().add(buttonBox);
    }

    private void UpdateListView() {
        JSONArray jsonGameList = StrategicGameClient.getInstance().getGameList();
        gamesList.clear();

        List<String> listOfGames = JsonArrayToObservable(jsonGameList);

        gamesList.addAll(listOfGames);

        JSONArray jsonPlayerList = StrategicGameClient.getInstance().getPlayerList();

        playerGameList.clear();
        for (String player : JsonArrayToObservable(jsonPlayerList)) {
            if (!player.equals(Model.username)) {
                for (String game : listOfGames) {
                    playerGameList.add(player + ", " + game);
                }
            }
        }
    }

    private List<String> JsonArrayToObservable(JSONArray jsonArray) {
        List<String> items = jsonArray.toList().stream()
                .map(object -> (String) object)
                .collect(Collectors.toList());

        return items;
    }


    @Override
    public Scene getScene() {
        UpdateListView();

        return super.getScene();
    }

}
