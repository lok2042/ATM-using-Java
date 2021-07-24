package _new;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author lok2042
 * ATM provides a GUI home page for users to either sign in an existing account or sign up a new account
 */
public class ATM implements ActionListener {

    // Copy of AccountsManager
    private AccountsManager am;

    // Frame
    private Frame frame;

    // Title
    private JLabel titleLabel;
    private ImageIcon javaIcon;

    // Main Panel
    private JPanel mainPanel;
    private JLabel imgLabel;

    // Buttons
    private JPanel buttonPanel;
    private JButton signIn;
    private JButton signUp;

    public ATM(AccountsManager am) {
        this.am = am;

        frame = new Frame("ATM | Home");
        frame.setLayout(new BorderLayout());

        // --------- TITLE -----------
        titleLabel = new JLabel("Bank of Java", JLabel.CENTER);
        javaIcon = new ImageIcon("img/java.png");

        titleLabel.setIcon(javaIcon);
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 36));
        titleLabel.setForeground(new Color(100,149,237));
        titleLabel.setBorder(new EmptyBorder(20, 25, 20, 25));
        // --------------------------

        // --------- MAIN -----------
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        try {
            BufferedImage image = ImageIO.read(new File("img/finance.jpg"));
            imgLabel = new JLabel(new ImageIcon(image));
            mainPanel.add(imgLabel);
        } catch (IOException e) {
            // Do nothing
        }
        // --------------------------

        // ----- BUTTON SECTION -----
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setOpaque(false);

        signIn = new JButton("Sign In");
        signIn.setPreferredSize(new Dimension(120, 50));
        signIn.setFont(new Font("Verdana", Font.BOLD, 18));
        signIn.setForeground(new Color(50, 50, 50));
        signIn.setBackground(new Color(100,149,237));
        signIn.setFocusable(false);
        signIn.addActionListener(this);

        signUp = new JButton("Sign Up");
        signUp.setPreferredSize(new Dimension(120, 50));
        signUp.setFont(new Font("Verdana", Font.BOLD, 18));
        signUp.setForeground(new Color(50, 50, 50));
        signUp.setBackground(new Color(100,149,237));
        signUp.setFocusable(false);
        signUp.addActionListener(this);

        buttonPanel.add(signIn);
        buttonPanel.add(signUp);
        // --------------------------

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == signIn) {
            frame.dispose();
            new SignIn(am);
        }
        else if(e.getSource() == signUp) {
            frame.dispose();
            new SignUp(am);
        }
    }
}
