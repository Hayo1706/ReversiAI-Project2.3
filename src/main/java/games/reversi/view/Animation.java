package games.reversi.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Peg;

/**
 * Created by Hayo Riem
 */
public class Animation {
    Peg[][] pegs;

    public Animation(Peg[][] pegs) {
        this.pegs = pegs;
    }

    public void start() {
        Timeline TIMER;
        for (int row = 0; row < 8; row++) {
            for (int column = 7; column >= 0; column--) {
                int finalRow = row;
                int finalColumn = column;
                if (!(column == 4 && row == 4) && !(column == 3 && row == 4) && !(column == 4 && row == 3) && !(column == 3 && row == 3)) {
                    if (column % 2 == 0 ^ row % 2 == 0) {
                        ImageView imageview = new ImageView(new Image("white.png"));
                        imageview.setFitWidth(40);
                        imageview.setFitHeight(40);
                        TIMER = new Timeline(
                                new KeyFrame(Duration.seconds(0), ae -> pegs[finalRow][finalColumn].setGraphic(imageview)),
                                new KeyFrame(Duration.seconds(.2), ae -> pegs[finalRow][finalColumn].setGraphic(null))
                        );
                        TIMER.setCycleCount(column % 2 == 0 ? column + 1 : 7 - column + 1);
                    }
                    else {
                        ImageView imageview = new ImageView(new Image("black.png"));
                        imageview.setFitWidth(40);
                        imageview.setFitHeight(40);
                        TIMER = new Timeline(
                                new KeyFrame(Duration.seconds(0), ae -> pegs[finalRow][finalColumn].setGraphic(imageview)),
                                new KeyFrame(Duration.seconds(.2), ae -> pegs[finalRow][finalColumn].setGraphic(null))
                        );
                        TIMER.setCycleCount(column % 2 == 0 ? 7 - column + 1 : column + 1);
                    }
                    TIMER.play();
                }
            }
        }
    }

}
