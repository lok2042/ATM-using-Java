package _new;

import javax.swing.*;
import java.awt.*;

/**
 * @author lok2042
 * Sets up a menu button
 */
public class MenuButton extends JButton {

    // Font
    private Font buttonFont;

    public MenuButton(String text) {
        buttonFont = new Font("Verdana", Font.BOLD, 14);

        this.setText(text);
        this.setFocusable(false);
        this.setFont(buttonFont);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setForeground(Color.BLACK);
        this.setBackground(Color.LIGHT_GRAY);
    }
}
