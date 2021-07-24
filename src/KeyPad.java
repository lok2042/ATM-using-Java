package _new;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lok2042
 * KeyPad allows users to enter in the amount to perform a transaction
 */
public class KeyPad extends JFrame implements ActionListener {

    // KeyPad variables
    private StringBuilder screenInput;
    private TransactionType transactionType;
    private double amount;

    // Copies of AccountsManager, TransactionsManager, HistoryLogsManager, and Account
    private AccountsManager am;
    private TransactionsManager tm;
    private HistoryLogsManager hlm;
    private Account account;

    // GUI Components
    private JTextField displayScreen;
    private JPanel panel;
    private JButton[] numberButtons;
    private JButton doubleZeroButton;
    private JButton decimalButton;
    private JButton cancelButton;
    private JButton clearButton;
    private JButton enterButton;
    private JButton helpButton;

    public KeyPad(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account, TransactionType transactionType) {
        screenInput = new StringBuilder();
        amount = 0;

        this.am = am;
        this.tm = tm;
        this.hlm = hlm;
        this.account = account;
        this.transactionType = transactionType;

        this.setTitle("Key Pad | " + transactionType.toString());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(50, 50, 50));
        this.setIconImage(new ImageIcon("img/key-pad.png").getImage());

        // Display Screen
        displayScreen = new JTextField();
        displayScreen.setBackground(Color.BLACK);
        displayScreen.setForeground(Color.WHITE);
        displayScreen.setPreferredSize(new Dimension(480, 100));
        displayScreen.setHorizontalAlignment(JTextField.CENTER);
        displayScreen.setFont(new Font("Consolas", Font.PLAIN, 30));
        displayScreen.setEditable(false);

        // Panel for buttons
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(480, 345));
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.setBackground(new Color(50, 50, 50));

        // Number Buttons
        numberButtons = new JButton[10];
        for(int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFocusable(false);
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new Font("Consolas", Font.BOLD, 30));
        }

        // Double Zero button
        doubleZeroButton = new JButton("00");
        doubleZeroButton.setFocusable(false);
        doubleZeroButton.addActionListener(this);
        doubleZeroButton.setFont(new Font("Consolas", Font.BOLD, 30));

        // Decimal Button
        decimalButton = new JButton(".");
        decimalButton.setFocusable(false);
        decimalButton.addActionListener(this);
        decimalButton.setFont(new Font("Consolas", Font.BOLD, 30));

        // Cancel Button
        cancelButton = new JButton("CANCEL");
        cancelButton.setBackground(Color.RED);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);
        cancelButton.setFont(new Font("Consolas", Font.BOLD, 24));

        // Clear Button
        clearButton = new JButton("CLEAR");
        clearButton.setBackground(Color.YELLOW);
        clearButton.setFocusable(false);
        clearButton.addActionListener(this);
        clearButton.setFont(new Font("Consolas", Font.BOLD, 24));

        // Enter Button
        enterButton = new JButton("ENTER");
        enterButton.setBackground(new Color(0,128,0));
        enterButton.setFocusable(false);
        enterButton.addActionListener(this);
        enterButton.setFont(new Font("Consolas", Font.BOLD, 24));

        // Help Button
        helpButton = new JButton("HELP");
        helpButton.setFocusable(false);
        helpButton.addActionListener(this);
        helpButton.setFont(new Font("Consolas", Font.BOLD, 24));

        // Adds buttons to panel
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(cancelButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(clearButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(enterButton);
        panel.add(decimalButton);
        panel.add(numberButtons[0]);
        panel.add(doubleZeroButton);
        panel.add(helpButton);

        this.add(displayScreen);
        this.add(panel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 10; i++) {
            if(e.getSource() == numberButtons[i]) {
                screenInput.append(i);
                displayScreen.setText(screenInput.toString());
            }
        }
        if(e.getSource() == decimalButton) {
            if(!screenInput.toString().contains(".")) {
                screenInput.append(".");
                displayScreen.setText(screenInput.toString());
            }
        }
        if(e.getSource() == doubleZeroButton) {
            if(screenInput.length() > 0) {
                screenInput.append("00");
                displayScreen.setText(screenInput.toString());
            }
        }
        if(e.getSource() == cancelButton) {
            if(JOptionPane.showConfirmDialog(null,
                    "Are you sure to exit key pad?",
                    "Cancel",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.dispose();
                new Menu(am, tm, hlm, account);
            }
        }
        if(e.getSource() == clearButton) {
            if(screenInput.length() > 0) {
                screenInput.delete(screenInput.length() - 1, screenInput.length());
                displayScreen.setText(screenInput.toString());
            }
        }
        if(e.getSource() == enterButton) {
            if(screenInput.length() > 0) {
                amount = Double.parseDouble(screenInput.toString());

                if(transactionType.equals(TransactionType.DEPOSIT)) {
                    this.dispose();
                    new Deposit(am, tm, hlm, account, String.format("%.2f", amount));
                }
                else if(transactionType.equals(TransactionType.WITHDRAWAL)) {
                    this.dispose();
                    new Withdrawal(am, tm, hlm, account, String.format("%.2f", amount));
                }
                else if(transactionType.equals(TransactionType.TRANSFER)) {
                    String recipientUsername = JOptionPane.showInputDialog("Enter a valid account number / username: ");

                    if(account.getAccountNumber().equals(recipientUsername)) {
                        JOptionPane.showMessageDialog(null,
                                "You cannot transfer money to yourself!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        Account recipientAccount = am.doesAccountExist(recipientUsername);

                        if(recipientAccount != null) {
                            this.dispose();
                            new Transfer(am, tm, hlm, account, recipientAccount, String.format("%.2f", amount));
                        }
                        else {
                            JOptionPane.showMessageDialog(null,
                                    "Recipient Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Empty Field!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource() == helpButton) {
            JOptionPane.showMessageDialog(null,
                    "Please wait for an officer to come and assist you.",
                    "Help", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
