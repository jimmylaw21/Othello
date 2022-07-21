package components;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *swing鼠标交互
 */
public abstract class BasicComponent extends JComponent {


    public BasicComponent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                onMouseReleased();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                onMouseClicked();
            }
        });
    }

    protected abstract void onMouseReleased();

    public abstract void onMouseClicked();
}