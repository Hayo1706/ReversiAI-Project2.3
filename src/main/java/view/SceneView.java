package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class SceneView implements View {

    protected GameClient client;

    protected Label title = new Label();

    protected VBox rootVBox = new VBox(title);

    private Scene scene;


    public SceneView(GameClient client){
        this.client = client;
    }

    public void CreateScene(){
        title.getStyleClass().clear();
        title.getStyleClass().add("title-label");
        rootVBox.setAlignment(Pos.TOP_CENTER);
        scene = new Scene(rootVBox,500,500);
        scene.getStylesheets().add("style.css");
    }

    @Override
    public void setText(String text){
        title.setText(text);
    }

    protected Button CreateButton(String text) {
        var button = new Button(text);

        button.getStyleClass().clear();
        button.getStyleClass().add("Client-button");

        return button;
    }

    protected Label CreateLabel(String text){
        var label = new Label(text);

        label.getStyleClass().clear();
        label.getStyleClass().add("default-label");

        return label;
    }

    public Scene getScene(){
        return scene;
    }
}
