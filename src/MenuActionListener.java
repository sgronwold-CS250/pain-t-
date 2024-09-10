import java.io.File;
import java.io.IOException;
import java.awt.image.RenderedImage;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

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

        Scene scene = button.getScene();

        GridPane grid = (GridPane) scene.getRoot();

        Canvas canvasCandidate = null;
        Label instructionLabel = null;
        for(Node child: grid.getChildren()) {
            if(child instanceof Canvas) {
                canvasCandidate = (Canvas) child;
            }
            if(child instanceof Label && child.getId().equals("title")) {
                instructionLabel = (Label) child;
            }
        }

        if(canvasCandidate == null) {
            // then we don't have a canvas for some reason?
            return;
        }
        // otherwise we have a final canvas
        final Canvas canvas = canvasCandidate;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        switch(button.getId()) {
            case "open":
            // prompt the user for a file path
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(scene.getWindow());

            Image img = new Image("file://"+file.getAbsolutePath());

            canvas.setWidth(img.getWidth());
            canvas.setHeight(img.getHeight());
            gc.clearRect(0,0,img.getWidth(),img.getHeight());
            gc.drawImage(img, 0, 0);
            break;

            case "saveas":
            fc = new FileChooser();
            file = fc.showSaveDialog(scene.getWindow());

            // getting the file extension
            String fname = file.getName();
            int i = fname.lastIndexOf(".");
            System.out.println(i);
            String extension;
            if(i == -1) {
                extension = "png";
            } else {
                extension = fname.substring(i);
                switch(extension) {
                    case "jpg":
                    case "jpeg":
                    case "png":
                    case "bmp":
                    // all of these are acceptable extensions, do nothing
                    break;
                    default:
                    // if we have an unacceptable extension, just export a png
                    extension = "png";
                    break;
                }
            }
            System.out.println(extension);
            System.out.println(fname);

            // begin stack overflow code to save image
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, writableImage);
            BufferedImage bi = new BufferedImage((int) canvas.getWidth(), (int) canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, bi);
            try {
                ImageIO.write(renderedImage, extension, file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            // end stack overflow code to save image
            break;
            case "drawline":
            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new LineDrawer(canvas, instructionLabel));
            break;
            case "help":
            new HelpScreen();
            break;
        }


    }

}
