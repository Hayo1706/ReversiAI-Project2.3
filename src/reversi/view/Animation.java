package reversi.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import reversi.model.Peg;

public class Animation{
    Peg[][] pegs;
    public Animation(Peg[][] pegs) {
        this.pegs = pegs;
    }

    public void start() {
        Timeline TIMER;
            for (int row = 0; row < 8;row++) {
                for (int column = 7; column >= 0; column--) {
                    int finalRow = row;
                    int finalColumn = column;
                    if (!(column == 4 && row == 4) && !(column == 3 && row == 4) && !(column == 4 && row == 3) && !(column == 3 && row == 3)) {
                        if (column % 2 == 0 ^ row % 2 == 0) {
                            TIMER = new Timeline(
                                    new KeyFrame(Duration.seconds(0), ae -> pegs[finalColumn][finalRow].setBlack()),
                                    new KeyFrame(Duration.seconds(.2), ae -> pegs[finalColumn][finalRow].reset())
                            );
                            TIMER.setCycleCount(column % 2 == 0 ? column + 1 : 7 - column + 1);
                        } else {
                            TIMER = new Timeline(
                                    new KeyFrame(Duration.seconds(0), ae -> pegs[finalColumn][finalRow].setWhite()),
                                    new KeyFrame(Duration.seconds(.2), ae -> pegs[finalColumn][finalRow].reset())
                            );
                            TIMER.setCycleCount(column % 2 == 0 ? 7 - column + 1 : column + 1);
                        }
                        TIMER.play();
                    }
                }
            }
    }

}
