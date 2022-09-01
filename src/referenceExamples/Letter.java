package referenceExamples;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

class Letter extends JLabel {

    private Font font1;
    private Font font2;
    private final FontRenderContext fontRenderContext1;
    private final FontRenderContext fontRenderContext2;

    public Letter(final String letter) {
        super(letter);
        setFocusable(true);
        setBackground(Color.RED);
        font1 = getFont();
        font2 = font1.deriveFont(48f);
        fontRenderContext1 = getFontMetrics(font1).getFontRenderContext();
        fontRenderContext2 = getFontMetrics(font2).getFontRenderContext();
        MouseInputAdapter mouseHandler = new MouseInputAdapter() {

            @Override
            public void mouseEntered(final MouseEvent e) {
                Letter.this.setOpaque(true);
                setFont(font2);
                Rectangle bounds = getBounds();
                Rectangle2D stringBounds = font2.getStringBounds(getText(), fontRenderContext2);
                bounds.width = (int) stringBounds.getWidth();
                bounds.height = (int) stringBounds.getHeight();
                setBounds(bounds);
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                Letter.this.setOpaque(false);
                setFont(font1);
                Rectangle bounds = getBounds();
                Rectangle2D stringBounds = font1.getStringBounds(getText(), fontRenderContext1);
                bounds.width = (int) stringBounds.getWidth();
                bounds.height = (int) stringBounds.getHeight();
                setBounds(bounds);
            }
        };
        addMouseListener(mouseHandler);
    }
}