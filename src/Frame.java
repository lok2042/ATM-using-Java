package _new;

import javax.swing.*;
import java.awt.*;

/**
 * @author lok2042
 * Sets up a commonly-used frame
 */
public class Frame extends JFrame {
    public Frame(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750, 500);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(50, 50, 50));
        this.setIconImage(new ImageIcon("img/money.png").getImage());
        this.setLocationRelativeTo(null);
    }
}
