package _new;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author lok2042
 * A GUI page for a user to sign up a new account
 * Upon successful sign up, an account is added to accounts.txt,
 * and user would be directed to the sign in page
 */
public class SignUp implements ActionListener {

    private static final int MINIMUM_INITIAL_DEPOSIT = 50;

    // Accounts Manager
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

    // General details - name & initial deposit amount
    private JLabel nameLabel;
    private JTextField nameText;
    private JLabel initialDepositLabel;
    private JTextField initialDepositText;

    // Security details - username & PIN
    private JLabel usernameLabel;
    private JTextField usernameText;
    private JLabel pinLabel;
    private JPasswordField pinText;
    private JLabel rePinLabel;
    private JPasswordField rePinText;

    // Buttons
    private JPanel buttonPanel;
    private JButton submitButton;
    private JButton resetButton;
    private JButton cancelButton;

    /**
     * Constructs an instance of SignUp and sets up GUI components
     * @param am        A copy of AccountsManager object
     */
    public SignUp(AccountsManager am) {
        this.am = am;

        frame = new Frame("ATM | Sign In");
        frame.setLayout(new BorderLayout());

        // --------- TITLE -----------
        titleLabel = new JLabel("Sign Up a New Account", JLabel.CENTER);
        titleLabel.setIcon(new ImageIcon("img/sign-up.png"));
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        titleLabel.setPreferredSize(new Dimension(740, 150));
        titleLabel.setForeground(Color.WHITE);
        // --------------------------

        // --------- FORM ----------
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(0, 25, 0, 25));
        formPanel.setOpaque(false);
        // --------------------------

        // --------- FONTS ----------
        formLabelFont = new Font("Verdana", Font.PLAIN, 18);
        formTextFont = new Font("Verdana", Font.PLAIN, 16);
        buttonFont = new Font("Verdana", Font.BOLD, 18);
        // --------------------------

        // ----- GENERAL SECTION -----
        nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(formLabelFont);

        nameText = new JTextField();
        nameText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nameText.setHorizontalAlignment(JTextField.CENTER);
        nameText.setFont(formTextFont);

        initialDepositLabel = new JLabel("Initial Deposit (Minimum: $" + MINIMUM_INITIAL_DEPOSIT + ")");
        initialDepositLabel.setForeground(Color.WHITE);
        initialDepositLabel.setFont(formLabelFont);

        initialDepositText = new JTextField();
        initialDepositText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        initialDepositText.setHorizontalAlignment(JTextField.CENTER);
        initialDepositText.setFont(formTextFont);

        formPanel.add(nameLabel);
        formPanel.add(nameText);
        formPanel.add(initialDepositLabel);
        formPanel.add(initialDepositText);
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

        rePinLabel = new JLabel("Re-enter PIN");
        rePinLabel.setForeground(Color.WHITE);
        rePinLabel.setFont(formLabelFont);

        rePinText = new JPasswordField(6);
        rePinText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rePinText.setHorizontalAlignment(JTextField.CENTER);
        rePinText.setFont(formTextFont);

        formPanel.add(usernameLabel);
        formPanel.add(usernameText);
        formPanel.add(pinLabel);
        formPanel.add(pinText);
        formPanel.add(rePinLabel);
        formPanel.add(rePinText);
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

            // Checks name
            boolean check1 = true;
            String name = nameText.getText().trim();
            if(name.length() < 5 || name.length() > 30) {
                JOptionPane.showMessageDialog(null,
                        "Name must have a length of between 5 and 30 characters", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                check1 = false;
            }

            // Checks initial deposit
            boolean check2 = true;
            double initialDeposit = 0;
            try {
                initialDeposit = Double.parseDouble(initialDepositText.getText());
                if(initialDeposit < 50) {
                    JOptionPane.showMessageDialog(null,
                            "Minimum amount of initial deposit is $50.", "Invalid Initial Deposit Amount", JOptionPane.ERROR_MESSAGE);
                    initialDepositText.setText("");
                    check2 = false;
                }
            } catch(NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,
                        "Amount must not contain any letters or symbols.", "Invalid Initial Deposit Amount", JOptionPane.ERROR_MESSAGE);
                initialDepositText.setText("");
                check2 = false;
            }

            // Checks username
            boolean check3 = true;
            String username = usernameText.getText().trim();
            if(username.length() < 5 || username.length() > 15) {
                JOptionPane.showMessageDialog(null,
                        "Username must have a length of between 5 and 15 characters", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                usernameText.setText("");
                check3 = false;
            }
            if(check3 && am.doesAccountExist(username) != null) {
                JOptionPane.showMessageDialog(null,
                        "Username has been taken. Choose another one", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                usernameText.setText("");
                check3 = false;
            }

            // Checks PIN
            boolean check4 = true;
            String pin = String.valueOf(pinText.getPassword());
            String rePin = String.valueOf(rePinText.getPassword());

            int validPin = 0;
            if(pin.length() == 6 && rePin.length() == 6) {
                if(!pin.equals(rePin)) {
                    JOptionPane.showMessageDialog(null,
                            "PINs do not match. Try again.", "Invalid PINs", JOptionPane.ERROR_MESSAGE);
                    pinText.setText("");
                    rePinText.setText("");
                    check4 = false;
                }
                else {
                    try {
                        validPin = Integer.parseInt(pin);
                    }
                    catch(NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null,
                                "PIN must be a 6-digit number.", "Invalid PIN", JOptionPane.ERROR_MESSAGE);
                        pinText.setText("");
                        rePinText.setText("");
                        check4 = false;
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Please ensure both PINs are a 6-digit number.", "Invalid PINs", JOptionPane.ERROR_MESSAGE);
                pinText.setText("");
                rePinText.setText("");
                check4 = false;
            }

            if(check1 && check2 && check3 && check4) {
                am.addNewAccount(username, validPin, name, initialDeposit);
                JOptionPane.showMessageDialog(null,
                        "You will now be directed to the Sign In page ...", "Account Created Successfully!", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new SignIn(am);
            }
        }
        if(e.getSource() == resetButton) {
            clearAllFields();
        }
        if(e.getSource() == cancelButton) {
            frame.dispose();
            new ATM(am);
        }
    }

    /**
     * Clears the contents in all text fields
     */
    private void clearAllFields() {
        nameText.setText("");
        initialDepositText.setText("");
        usernameText.setText("");
        pinText.setText("");
        rePinText.setText("");
    }
}
