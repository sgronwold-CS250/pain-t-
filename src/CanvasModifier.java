import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public abstract class CanvasModifier {
    Canvas canvas;
    Labeled instructionLabel;

    // drawing tools; this is the global "current" drawer. it would be chaos if
    // the user was drawing a line at the same time as an ellipse :0
    static CanvasInterface currDrawer;

    public CanvasModifier(Canvas c) {
        canvas = c;

        Scene scene = canvas.getScene();

        GridPane grid = (GridPane) scene.getRoot();

        instructionLabel = null;
        for (Node child : grid.getChildren()) {
            if (child instanceof Labeled && child.getId().equals("title")) {
                instructionLabel = (Labeled) child;
            }
        }
    }

    public void open() {
        GraphicsContext gc = getGraphicsContext();
        Scene scene = getScene();

        // prompt the user for a file path
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(scene.getWindow());

        Image img = new Image("file://" + file.getAbsolutePath());

        canvas.setWidth(img.getWidth());
        canvas.setHeight(img.getHeight());
        gc.clearRect(0, 0, img.getWidth(), img.getHeight());
        gc.drawImage(img, 0, 0);

        Main.refreshCanvasDims(canvas);
    }

    public void saveAs() {
        Scene scene = getScene();

        FileChooser fc = new FileChooser();
        File file = fc.showSaveDialog(scene.getWindow());

        // getting the file extension
        String fname = file.getName();
        int i = fname.lastIndexOf(".");
        System.out.println(i);
        String extension;
        if (i == -1) {
            extension = "png";
        } else {
            extension = fname.substring(i);
            switch (extension) {
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

        // scale the canvas to 100%
        canvas.setScaleX(1);
        canvas.setScaleY(1);
        // begin stack overflow code to save image
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);
        BufferedImage bi = new BufferedImage((int) canvas.getWidth(), (int) canvas.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, bi);
        try {
            ImageIO.write(renderedImage, extension, file);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // end stack overflow code to save image
        // finally trigger the canvas resize thingy
        canvas.setWidth(canvas.getWidth() - 1);
    }

    private GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    private Scene getScene() {
        return canvas.getScene();
    }
}
