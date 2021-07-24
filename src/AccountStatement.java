package _new;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lok2042
 * Displays account statement
 */
public class AccountStatement implements ActionListener {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("H:mm:ss");
    private String date;
    private String time;

    // Copies of AccountsManager, TransactionsManager, HistoryLogsManager, and Account
    private AccountsManager am;
    private TransactionsManager tm;
    private HistoryLogsManager hlm;
    private Account account;

    // Frame
    private Frame frame;

    // Header
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JLabel dateAndTimeLabel;

    // Account Statement
    private JPanel statementPanel;
    private AccountStatementLabel usernameLabel;
    private AccountStatementText usernameText;
    private AccountStatementLabel nameLabel;
    private AccountStatementText nameText;
    private AccountStatementLabel currentBalanceLabel;
    private AccountStatementText currentBalanceText;

    // Inner class for account statement labels
    private static class AccountStatementLabel extends JLabel {
        public AccountStatementLabel(String labelName) {
            this.setText(labelName);
            this.setFont(new Font("Verdana", Font.PLAIN, 18));
            this.setForeground(Color.WHITE);
        }
    } 

    // Inner class for account statement texts
    private  static class AccountStatementText extends JTextField {
        public AccountStatementText(String textInfo) {
            this.setText(textInfo);
            this.setBackground(Color.GRAY);
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Verdana", Font.PLAIN, 16));
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.setHorizontalAlignment(JTextField.CENTER);
            this.setEditable(false);
        }
    }

    // Buttons
    private JPanel buttonPanel;
    private JButton saveButton;
    private JButton returnButton;

    public AccountStatement(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account) {
        this.am = am;
        this.tm = tm;
        this.hlm = hlm;
        this.account = account;

        frame = new Frame("ATM | Account Statement");
        frame.setLayout(new BorderLayout());

        // --------- HEADER -----------
        headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());

        titleLabel = new JLabel("   Bank of Java", JLabel.CENTER);
        titleLabel.setIcon(new ImageIcon("img/java.png"));
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 28));
        titleLabel.setPreferredSize(new Dimension(740, 100));
        titleLabel.setForeground(Color.WHITE);

        Date now = new Date();
        date = DATE_FORMAT.format(now);
        time = TIME_FORMAT.format(now);

        dateAndTimeLabel = new JLabel("DATE : " + date + ", TIME : " + time, JLabel.CENTER);
        dateAndTimeLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        dateAndTimeLabel.setPreferredSize(new Dimension(740, 30));
        dateAndTimeLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(dateAndTimeLabel, BorderLayout.SOUTH);
        // --------------------------

        // ---- STATEMENT SECTION ----
        statementPanel = new JPanel();
        statementPanel.setLayout(new GridLayout(3, 2, 10, 10));
        statementPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        statementPanel.setOpaque(false);

        usernameLabel = new AccountStatementLabel("Account Number / Username");
        usernameText = new AccountStatementText(account.getAccountNumber());

        nameLabel = new AccountStatementLabel("Account Holder");
        nameText = new AccountStatementText(account.getAccountOwner());

        currentBalanceLabel = new AccountStatementLabel("Current Balance ($)");
        currentBalanceText = new AccountStatementText(String.format("%.2f", account.getAccountBalance()));

        statementPanel.add(usernameLabel);
        statementPanel.add(usernameText);
        statementPanel.add(nameLabel);
        statementPanel.add(nameText);
        statementPanel.add(currentBalanceLabel);
        statementPanel.add(currentBalanceText);
        // ---------------------------

        // ----- BUTTON SECTION -----
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setPreferredSize(new Dimension(750, 75));
        buttonPanel.setOpaque(false);

        saveButton = new JButton("Save");
        saveButton.setFocusable(false);
        saveButton.setFont(new Font("Verdana", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(120, 35));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(100,149,237));
        saveButton.addActionListener(this);

        returnButton = new JButton("Return");
        returnButton.setIcon(new ImageIcon("img/return.png"));
        returnButton.setFocusable(false);
        returnButton.setFont(new Font("Verdana", Font.BOLD, 14));
        returnButton.setPreferredSize(new Dimension(120, 35));
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(Color.GRAY);
        returnButton.addActionListener(this);

        buttonPanel.add(saveButton);
        buttonPanel.add(returnButton);
        // --------------------------

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(statementPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == returnButton) {

            // Adds a new history log for change of PIN
            hlm.addNewHistoryLog(date, time, "VIEWED ACCOUNT STATEMENT", account.getAccountID());

            frame.dispose();
            new Menu(am, tm, hlm, account);
        }
        if(e.getSource() == saveButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);
            if(response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                PrintWriter fileOut = null;

                try {
                    fileOut = new PrintWriter(file);

                    // Stores account statement details for saving file
                    StringBuilder str = new StringBuilder();
                    str.append("Bank of Java | Account Statement\n");
                    str.append("\nDate\t: " + date + "\n");
                    str.append("Time\t: " + time + "\n\n");
                    str.append("Account Number / Username : " + account.getAccountNumber() + "\n");
                    str.append("Account Holder\t\t: " + account.getAccountOwner()  + "\n");
                    str.append("Current Balance\t\t: " + String.format("%.2f", account.getAccountBalance())  + "\n\n");
                    str.append("© Copyright 2021. Mini Java Project by lok2042.");

                    fileOut.print(str);
                    JOptionPane.showMessageDialog(null,
                            "Your account statement has been downloaded", "Download Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(null,
                            "Account Statement cannot be downloaded. Try again.", "Cannot Download", JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    fileOut.close();
                }
            }
        }
    }
}
