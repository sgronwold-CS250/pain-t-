import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class TabSwitcher implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
        System.out.println("Switching the tab");
        Button button;
        if (e.getSource() instanceof Button) {
            button = (Button) e.getSource();
        } else {
            // otherwise there's nothing for us to do
            return;
        }

        // sometimes we just need to switch tabs
        if (button.getId().substring(0,3).equals("tab")) {
            int nextTabNum = Integer.parseInt(button.getId().substring(3));
            PaintTab.setCurrentTab(nextTabNum);
        }
    }

}
