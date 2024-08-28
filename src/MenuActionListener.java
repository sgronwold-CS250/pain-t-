import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class MenuActionListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
        System.out.println("Button clicked");
        Button button;
        if (e.getSource() instanceof Button) {
            button = (Button) e.getSource();
        } else {
            // otherwise there's nothing for us to do
            return;
        }

        // right now we only have one button
        // TODO make a way to differentiate between the buttons... maybe their text?
        Scene scene = button.getScene();

        GridPane grid = (GridPane) scene.getRoot();

        Canvas canvas = null;
        for(Node child: grid.getChildren()) {
            if(child instanceof Canvas) {
                canvas = (Canvas) child;
            }
        }

        if(canvas == null) {
            // then we don't have a canvas for some reason?
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // prompt the user for a file path
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(scene.getWindow());

        System.out.println(file.getAbsolutePath());

        Image img = new Image("/mnt/c/Users/samue/Documents/GitHub/pain-t-/hawks.png");
        canvas.setWidth(img.getWidth());
        canvas.setHeight(img.getHeight());
        gc.drawImage(img, 0, 0);

    }

}
