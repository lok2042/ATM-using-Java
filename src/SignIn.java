package _new;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lok2042
 * A GUI page for a user to sign in his/her existing account
 */
public class SignIn implements ActionListener {

    // Copy of AccountsManager
    private AccountsManager am;

    // Frame
    private Frame frame;

    // Title
    private JLabel titleLabel;

    // Form Panel
    private JPanel formPanel;

    // Fonts
    private Font formLabelFont;
    private Font formTextFont;
    private Font buttonFont;

    // Security details - username & PIN
    private JLabel usernameLabel;
    private JTextField usernameText;
    private JLabel pinLabel;
    private JPasswordField pinText;

    // Buttons
    private JPanel buttonPanel;
    private JButton submitButton;
    private JButton resetButton;
    private JButton cancelButton;

    /**
     * Constructs an instance of SignIn and sets up GUI components
     * @param am        A copy of AccountsManager object
     */
    public SignIn(AccountsManager am) {
        this.am = am;

        frame = new Frame("ATM | Sign In");
        frame.setLayout(new BorderLayout());

        // --------- TITLE -----------
        titleLabel = new JLabel("Sign In your Account", JLabel.CENTER);
        titleLabel.setIcon(new ImageIcon("img/sign-in.png"));
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        titleLabel.setPreferredSize(new Dimension(740, 150));
        titleLabel.setForeground(Color.WHITE);
        // --------------------------

        // --------- FORM ----------
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(60, 25, 60, 25));
        // --------------------------

        // --------- FONTS ----------
        formLabelFont = new Font("Verdana", Font.PLAIN, 18);
        formTextFont = new Font("Verdana", Font.PLAIN, 16);
        buttonFont = new Font("Verdana", Font.BOLD, 18);
        // --------------------------

        // ----- SECURITY SECTION -----
        usernameLabel = new JLabel("Account Number / Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(formLabelFont);

        usernameText = new JTextField();
        usernameText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usernameText.setHorizontalAlignment(JTextField.CENTER);
        usernameText.setFont(formTextFont);

        pinLabel = new JLabel("Enter PIN");
        pinLabel.setForeground(Color.WHITE);
        pinLabel.setFont(formLabelFont);

        pinText = new JPasswordField();
        pinText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pinText.setHorizontalAlignment(JTextField.CENTER);
        pinText.setFont(formTextFont);

        formPanel.add(usernameLabel);
        formPanel.add(usernameText);
        formPanel.add(pinLabel);
        formPanel.add(pinText);
        // ----------------------------

        // ----- BUTTON SECTION -----
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 25));
        buttonPanel.setPreferredSize(new Dimension(750, 100));
        buttonPanel.setOpaque(false);

        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.setFont(buttonFont);
        submitButton.setPreferredSize(new Dimension(120, 50));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(0,128,0));
        submitButton.addActionListener(this);

        resetButton = new JButton("Reset");
        resetButton.setFocusable(false);
        resetButton.setFont(buttonFont);
        resetButton.setPreferredSize(new Dimension(120, 50));
        resetButton.setForeground(Color.GRAY);
        resetButton.setBackground(Color.YELLOW);
        resetButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.setFont(buttonFont);
        cancelButton.setPreferredSize(new Dimension(120, 50));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.RED);
        cancelButton.addActionListener(this);

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);
        // --------------------------

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton) {

            Account account;
            String username = usernameText.getText();
            String pinStr = String.valueOf(pinText.getPassword());

            // Ensures both fields are not empty
            if(!username.isEmpty() && !pinStr.isEmpty()) {

                // Checks username
                boolean check1 = true;
                account = am.doesAccountExist(username);
                if(account == null) {
                    JOptionPane.showMessageDialog(null,
                            "Account does not exist", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                    check1 = false;
                    usernameText.setText("");
                }

                // Checks PIN
                boolean check2 = true;
                int pin = 0;
                try {
                    pin = Integer.parseInt(pinStr);
                }
                catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null,
                            "PIN must be a 6-digit number.", "Invalid PIN", JOptionPane.ERROR_MESSAGE);
                    check2 = false;
                    pinText.setText("");
                }

                // Verifies match for username and PIN
                if(check1 && check2) {
                    if(am.signInAccount(username, pin)) {
                        frame.dispose();
                        new Intermediary(am, account);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,
                                "Username and PIN mismatched! Try again.",
                                "Invalid Username or PIN", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Do ensure all fields are not empty before clicking SUBMIT.",
                        "Empty Fields", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == resetButton) {
            usernameText.setText("");
            pinText.setText("");
        }
        else if(e.getSource() == cancelButton) {
            frame.dispose();
            new ATM(am);
        }
    }
}
