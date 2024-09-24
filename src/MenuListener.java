import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public abstract class MenuListener {
    Canvas canvas;
    Labeled instructionLabel;


    // drawing tools; this is the global "current" drawer. it would be chaos if
    // the user was drawing a line at the same time as an ellipse :0
    static CanvasInterface currDrawer;

    public MenuListener(Canvas c) {
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

        // update the current filepath
        PaintTab.getCurrentTab().currPath = file;

        Image img = new Image("file://" + file.getAbsolutePath());

        canvas.setWidth(img.getWidth());
        canvas.setHeight(img.getHeight());
        gc.clearRect(0, 0, img.getWidth(), img.getHeight());
        gc.drawImage(img, 0, 0);

        PaintTab.getCurrentTab().resize();
    }

    public void undo() {
        undoRedoController(PaintTab.getCurrentTab().getUndoStack(), PaintTab.getCurrentTab().getRedoStack());
    }

    public void redo() {
        undoRedoController(PaintTab.getCurrentTab().getRedoStack(), PaintTab.getCurrentTab().getUndoStack());
    }

    private void undoRedoController(Stack<Canvas> stackToPopFrom, Stack<Canvas> stackToPushTo) {
        System.out.println("Trying to undo/redo");

        // we can't undo if there's no action to undo
        if (stackToPopFrom.isEmpty()) return;

        System.out.println("and we succeeded");

        // if there's another canvas then we pop the canvas,
        // switch it over to the redo stack
        Canvas popped = stackToPopFrom.pop();
        stackToPushTo.push(PaintTab.getCurrentTab().getCanvas());

        // refresh the tab
        PaintTab.getCurrentTab().setCanvas(popped);
        PaintTab.refreshCanvas();
        setCanvas(popped);
    }

    public void saveAs() {
        Scene scene = getScene();

        FileChooser fc = new FileChooser();
        PaintTab.getCurrentTab().currPath = fc.showSaveDialog(scene.getWindow());

        save();
    }

    public void save() {
        // save here if we haven't saved yet
        if (PaintTab.getCurrentTab().currPath == null) {
            saveAs();
            return;
        }


        // getting the file extension
        String fname = PaintTab.getCurrentTab().currPath.getName();
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
            ImageIO.write(renderedImage, extension, PaintTab.getCurrentTab().currPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // end stack overflow code to save image
        // finally trigger the canvas resize thingy
        PaintTab.getCurrentTab().resize();

        PaintTab.getCurrentTab().UNSAVED_CHANGES = false;
    }

    private GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    private Scene getScene() {
        return canvas.getScene();
    }

    public void clearAllDrawers() {
        // stop the current drawer's callback so it isn't callback chaos
        if(currDrawer != null) currDrawer.stopCanvasListener();
    }

    public void clearCanvas() {
        TextInputDialog tid = new TextInputDialog("Type \"Steve Harvey\" to confirm that you want to clear the canvas");
        String response = tid.showAndWait().get();
        
        if (response.equalsIgnoreCase("steve harvey")){
            PaintTab.getCurrentTab().clear();
            instructionLabel.setText("Canvas cleared");
        }
    }

    public void setCanvas() {
        setCanvas(PaintTab.getCurrentTab().getCanvas());
    }

    public void setCanvas(Canvas c) {
        canvas = c;
    }

    public void copy() {
        clearAllDrawers();

        currDrawer = new Copier(canvas, instructionLabel);
    }

    public void cut() {
        clearAllDrawers();

        currDrawer = new Copier(canvas, instructionLabel, true);
    }

    public void paste() {
        clearAllDrawers();

        currDrawer = new Paster(canvas, instructionLabel);
    }
}