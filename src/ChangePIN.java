package _new;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lok2042
 * Allows users to change their PIN number
 */
public class ChangePIN implements ActionListener {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("H:mm:ss");

    // Copies of AccountsManager, TransactionsManager, HistoryLogsManager, Account, and account ID
    private AccountsManager am;
    private TransactionsManager tm;
    private HistoryLogsManager hlm;
    private Account account;
    private int accountID;

    // Frame
    private Frame frame;

    // Fonts
    private Font formLabelFont;
    private Font formTextFont;
    private Font buttonFont;

    // Title
    private JLabel titleLabel;

    // Form Section
    private JPanel formPanel;
    private JLabel currPinLabel;
    private JPasswordField currPinText;     // Current PIN
    private JLabel newPinLabel;
    private JPasswordField newPinText;      // New PIN
    private JLabel reNewPinLabel;
    private JPasswordField reNewPinText;    // Re-enter New PIN

    // Buttons
    private JPanel buttonPanel;
    private JButton submitButton;
    private JButton resetButton;
    private JButton cancelButton;

    public ChangePIN(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account) {
        this.am = am;
        this.tm = tm;
        this.hlm = hlm;
        this.account = account;
        this.accountID = account.getAccountID();

        frame = new Frame("ATM | Change PIN");
        frame.setLayout(new BorderLayout());

        // --------- TITLE -----------
        titleLabel = new JLabel("   Change PIN", JLabel.CENTER);
        titleLabel.setIcon(new ImageIcon("img/settings.png"));
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        titleLabel.setPreferredSize(new Dimension(740, 130));
        titleLabel.setForeground(Color.WHITE);
        // --------------------------

        // --------- FONTS ----------
        formLabelFont = new Font("Verdana", Font.PLAIN, 18);
        formTextFont = new Font("Verdana", Font.PLAIN, 16);
        buttonFont = new Font("Verdana", Font.BOLD, 18);
        // --------------------------

        // ------- FORM SECTION -------
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 100, 30, 100));
        formPanel.setOpaque(false);

        currPinLabel = new JLabel("Enter current PIN");
        currPinLabel.setForeground(Color.WHITE);
        currPinLabel.setFont(formLabelFont);

        currPinText = new JPasswordField();
        currPinText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        currPinText.setHorizontalAlignment(JTextField.CENTER);
        currPinText.setFont(formTextFont);

        newPinLabel = new JLabel("Enter new PIN");
        newPinLabel.setForeground(Color.WHITE);
        newPinLabel.setFont(formLabelFont);

        newPinText = new JPasswordField();
        newPinText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newPinText.setHorizontalAlignment(JTextField.CENTER);
        newPinText.setFont(formTextFont);

        reNewPinLabel = new JLabel("Re-enter new PIN");
        reNewPinLabel.setForeground(Color.WHITE);
        reNewPinLabel.setFont(formLabelFont);

        reNewPinText = new JPasswordField(6);
        reNewPinText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        reNewPinText.setHorizontalAlignment(JTextField.CENTER);
        reNewPinText.setFont(formTextFont);

        formPanel.add(currPinLabel);
        formPanel.add(currPinText);
        formPanel.add(newPinLabel);
        formPanel.add(newPinText);
        formPanel.add(reNewPinLabel);
        formPanel.add(reNewPinText);
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
            String currPinStr = String.valueOf(currPinText.getPassword());
            String newPinStr = String.valueOf(newPinText.getPassword());
            String reNewPinStr = String.valueOf(reNewPinText.getPassword());

            // Check #1 - All PINs must have a length of 6 and All input fields cannot be empty
            if(currPinStr.length() != 6 || newPinStr.length() != 6 || reNewPinStr.length() != 6) {
                JOptionPane.showMessageDialog(null,
                        "Make sure all PINs are of length 6 and not empty", "Invalid Input(s)", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // Check #4 - All entered PINs shall only contain numbers
                try {
                    int currPin = Integer.parseInt(currPinStr);

                    // Check #3 - Current PIN must be correct
                    if(am.verifyCurrentAccountPIN(accountID, currPin)) {

                        // Check #4 - New PIN and re-entered New PIN must match
                        if(newPinStr.equals(reNewPinStr)) {
                            int newPin = Integer.parseInt(newPinStr);
                            am.changeAccountPIN(accountID, newPin);

                            JOptionPane.showMessageDialog(null,
                                    "Success! Your PIN has been changed.", "Status", JOptionPane.INFORMATION_MESSAGE);

                            // Adds a new history log for change of PIN
                            Date now = new Date();
                            String date = DATE_FORMAT.format(now);
                            String time = TIME_FORMAT.format(now);
                            hlm.addNewHistoryLog(date, time, "PIN CHANGED", accountID);

                            frame.dispose();
                            new Menu(am, tm, hlm, account);
                        }
                        else {
                            JOptionPane.showMessageDialog(null,
                                    "Your new PIN does not match with the re-entered new PIN. Try again",
                                    "Invalid New PIN", JOptionPane.INFORMATION_MESSAGE);
                            newPinText.setText("");
                            reNewPinText.setText("");
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(null,
                                "Your current PIN is not correct.", "Invalid Current PIN", JOptionPane.ERROR_MESSAGE);
                        currPinText.setText("");
                    }
                }
                catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null,
                            "Make sure all PINs .", "Invalid input for PIN(s)", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if(e.getSource() == resetButton) {
            currPinText.setText("");
            newPinText.setText("");
            reNewPinText.setText("");
        }
        if(e.getSource() == cancelButton) {
            frame.dispose();
            new Menu(am, tm, hlm, account);
        }
    }
}
