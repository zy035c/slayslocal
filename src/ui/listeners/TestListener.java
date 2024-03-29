package ui.listeners;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestListener extends MouseAdapter {

    Component pane;

    public TestListener(Component pane) {
        this.pane = pane;
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        // System.out.println("dragging");
        pane.setLocation(
                pane.getX() + evt.getX() - 50,
                pane.getY() + evt.getY() - 50
        );
        // pane.setBackground(GameColors.green2);
    }


//    @Override
//    public void mouseEntered(MouseEvent e) {
//        pane.setBackground(GameColors.green1);
//    }

//    @Override
//    public void mouseExited(MouseEvent e) {
//        pane.setBackground(GameColors.grey1);
//    }

}

