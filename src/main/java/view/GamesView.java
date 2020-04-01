package view;

import com.sun.javafx.scene.control.LabeledText;
import communication.StrategicGameClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;

import java.util.List;
import java.util.stream.Collectors;

public class GamesView extends SceneView {
    public GamesView(GameClient client) {
        super(client);
    }


    ObservableList<String> gamesList= FXCollections.observableArrayList();
    ObservableList<String> playerGameList = FXCollections.observableArrayList("mark", "peter", "balk");

    @Override
    public void CreateScene() {
        super.CreateScene();

        title.setText("available Games");

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

        VBox GamesVBox = new VBox(gamesListLabel,gamesListView);
        VBox playerVBox = new VBox(playerGameListLabel,playerGameListView);
        HBox hBox = new HBox(GamesVBox,playerVBox);
        hBox.setAlignment(Pos.TOP_CENTER);

        rootVBox.getChildren().add(hBox);
    }

    private void UpdateListView(){
        JSONArray jsonGameList = StrategicGameClient.getInstance().getGameList();
        gamesList.clear();
        List<String> listOfGames = JsonArrayToObservable(jsonGameList);
        gamesList.addAll(listOfGames);

        JSONArray jsonPlayerList = StrategicGameClient.getInstance().getPlayerList();
        playerGameList.clear();
        for (String player : JsonArrayToObservable(jsonPlayerList))
        {
            for (String game : listOfGames){
                playerGameList.add(player + ", " + game);
            }
        }
    }

    private List<String> JsonArrayToObservable(JSONArray jsonArray){
        List<String> items =  jsonArray.toList().stream()
                .map(object->(String)object)
                .collect(Collectors.toList());

        return items;
    }



    @Override
    public Scene getScene(){
        UpdateListView();

        System.out.println(gamesList.toString());
        System.out.println(playerGameList.toString());

        return super.getScene();
    }
}
