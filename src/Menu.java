package _new;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lok2042
 * Menu provides users with several options:
 * - Deposit
 * - Withdrawal
 * - Transfer
 * - View account statement
 * - View account history logs
 * - Change PIN
 * - Log out
 */
public class Menu implements ActionListener {

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

    // Header
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JButton logOutButton;

    // Menu buttons
    private JPanel buttonPanel;
    private MenuButton viewAccountStatementButton;
    private MenuButton depositButton;
    private MenuButton withdrawalButton;
    private MenuButton transferButton;
    private MenuButton viewHistoryButton;
    private MenuButton changePINButton;

    // Footer
    private JLabel footerLabel;

    public Menu(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account) {
        this.am = am;
        this.tm = tm;
        this.hlm = hlm;
        this.account = account;
        this.accountID = account.getAccountID();

        frame = new Frame("ATM | Menu");
        frame.setLayout(new BorderLayout());

        // --------- HEADER ---------
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        headerPanel.setOpaque(false);

        titleLabel = new JLabel("Welcome, " + account.getAccountOwner(), JLabel.LEFT);
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        titleLabel.setPreferredSize(new Dimension(750, 36));
        titleLabel.setForeground(Color.WHITE);

        logOutButton = new JButton("Log Out");
        logOutButton.setPreferredSize(new Dimension(100, 15));
        logOutButton.setFont(new Font("Verdana", Font.BOLD, 12));
        logOutButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        logOutButton.setForeground(Color.WHITE);
        logOutButton.setBackground(Color.RED);
        logOutButton.setFocusable(false);
        logOutButton.addActionListener(this);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(logOutButton, BorderLayout.EAST);
        // --------------------------

        // ----- BUTTON SECTION -----
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 100, 25));
        buttonPanel.setBorder(new EmptyBorder(50, 20, 50, 20));
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        buttonPanel.setOpaque(false);

        depositButton = new MenuButton("Deposit");
        depositButton.addActionListener(this);

        withdrawalButton = new MenuButton("Withdrawal");
        withdrawalButton.addActionListener(this);

        transferButton = new MenuButton("Transfer");
        transferButton.addActionListener(this);

        viewAccountStatementButton = new MenuButton("Account Statement");
        viewAccountStatementButton.addActionListener(this);

        viewHistoryButton = new MenuButton("Account History");
        viewHistoryButton.addActionListener(this);

        changePINButton = new MenuButton("Change PIN");
        changePINButton.addActionListener(this);

        buttonPanel.add(depositButton);
        buttonPanel.add(viewAccountStatementButton);
        buttonPanel.add(withdrawalButton);
        buttonPanel.add(viewHistoryButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(changePINButton);
        // --------------------------

        // -------- FOOTER ----------
        footerLabel = new JLabel("© Copyright 2021. Mini Java Project by lok2042.", JLabel.CENTER);
        footerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        footerLabel.setPreferredSize(new Dimension(750, 75));
        footerLabel.setForeground(Color.WHITE);
        // --------------------------

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(footerLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logOutButton) {
            if(JOptionPane.showConfirmDialog(null,
                    "Are you sure to log out?",
                    "Log Out Confirmation",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                // Adds a new history log for logging out
                Date now = new Date();
                String date = DATE_FORMAT.format(now);
                String time = TIME_FORMAT.format(now);
                hlm.addNewHistoryLog(date, time, "LOGGED OUT", accountID);

                // Updates files
                try {
                    am.updateAccounts();
                    tm.updateTransactions();
                    hlm.updateHistoryLogs();
                } catch (IOException ioException) {
                    // Do nothing
                }

                frame.dispose();
                new ATM(am);
            }
        }
        else if(e.getSource() == depositButton) {
            frame.dispose();
            new KeyPad(am, tm, hlm, account, TransactionType.DEPOSIT);
        }
        else if(e.getSource() == withdrawalButton) {
            frame.dispose();
            new KeyPad(am, tm, hlm, account, TransactionType.WITHDRAWAL);
        }
        else if(e.getSource() == transferButton) {
            frame.dispose();
            new KeyPad(am, tm, hlm, account, TransactionType.TRANSFER);
        }
        else if(e.getSource() == viewAccountStatementButton) {
            frame.dispose();
            new AccountStatement(am, tm, hlm, account);
        }
        else if(e.getSource() == viewHistoryButton) {
            frame.dispose();
            new AccountHistory(am, tm, hlm, account);
        }
        else if(e.getSource() == changePINButton) {
            frame.dispose();
            new ChangePIN(am, tm, hlm, account);
        }
    }
}
