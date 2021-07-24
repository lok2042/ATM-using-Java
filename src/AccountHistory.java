package _new;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * @author lok2042
 * Displays all history logs in an account
 */
public class AccountHistory implements ActionListener {

    // Copies of AccountsManager, TransactionsManager, HistoryLogsManager, and Account
    private AccountsManager am;
    private TransactionsManager tm;
    private HistoryLogsManager hlm;
    private Account account;

    // Frame
    private Frame frame;

    // Title
    private JLabel titleLabel;

    // History Logs Section
    private JList historyLogsList;

    // Buttons
    private JPanel buttonPanel;
    private JButton returnButton;

    public AccountHistory(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account) {
        this.am = am;
        this.tm = tm;
        this.hlm = hlm;
        this.account = account;

        frame = new Frame("ATM | Account History");
        frame.setLayout(new BorderLayout());

        // --------- TITLE -----------
        titleLabel = new JLabel("Account History", JLabel.CENTER);
        titleLabel.setIcon(new ImageIcon("img/history-log.png"));
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 28));
        titleLabel.setPreferredSize(new Dimension(740, 100));
        titleLabel.setForeground(Color.WHITE);
        // --------------------------

        // --- HISTORY LOGS SECTION ---
        LinkedList<String> logs = hlm.getAccountHistoryLogs(account.getAccountID());

        historyLogsList = new JList(logs.toArray());
        historyLogsList.setFont(new Font("Verdana", Font.PLAIN, 16));
        historyLogsList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(historyLogsList);
        listScroller.setBorder(new EmptyBorder(5, 30, 5, 30));
        listScroller.setOpaque(false);
        // ----------------------------

        // ----- BUTTON SECTION -----
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 25));
        buttonPanel.setPreferredSize(new Dimension(750, 100));
        buttonPanel.setOpaque(false);

        returnButton = new JButton("Return");
        returnButton.setIcon(new ImageIcon("img/return.png"));
        returnButton.setFocusable(false);
        returnButton.setFont(new Font("Verdana", Font.BOLD, 14));
        returnButton.setPreferredSize(new Dimension(120, 40));
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(Color.GRAY);
        returnButton.addActionListener(this);
        
        buttonPanel.add(returnButton);
        // --------------------------

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(listScroller, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == returnButton) {
            frame.dispose();
            new Menu(am, tm, hlm, account);
        }
    }
}
