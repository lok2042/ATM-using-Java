package _new;

import javax.swing.*;
import java.util.Date;

/**
 * @author lok2042
 * Handles a withdrawal transactions
 */
public class Withdrawal extends TransactionOption {

    // Maximum RM 1,500.00 per transaction, 3 times per day allowed
    private static final double TRANSACTION_AMOUNT_LIMIT = 1500.00;
    private static final int TRANSACTION_NUMBER_LIMIT = 3;

    private double amount;

    public Withdrawal(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account, String amountStr) {
        super(am, tm, hlm, account, account.getAccountID());
        this.amount = Double.parseDouble(amountStr);

        processTransaction();
    }

    @Override
    public boolean allowTransaction() {
        return (tm.getNumberOfTransaction(accountID, TransactionType.WITHDRAWAL) < TRANSACTION_NUMBER_LIMIT);
    }

    @Override
    public void processTransaction() {
        // #1 Check - Amount shall not exceed TRANSACTION_AMOUNT_LIMIT per transaction
        if(amount <= TRANSACTION_AMOUNT_LIMIT) {

            // #2 Check - Amount shall not exceed current balance
            if(am.checkAccountBalance(accountID, amount)) {

                // #3 Check - Number of transactions in a day shall not exceed TRANSACTION_NUMBER_LIMIT
                if(allowTransaction()) {
                    Date now = new Date();
                    date = DATE_FORMAT.format(now);
                    time = TIME_FORMAT.format(now);

                    // Decreases account balance
                    am.decreaseAccountBalance(accountID, amount);

                    // Adds a new transaction record
                    tm.addNewTransaction(date, time, TransactionType.WITHDRAWAL, amount, accountID);

                    // Adds a new history log for withdrawal
                    hlm.addNewHistoryLog(date, time,  String.format("WITHDRAWAL: -$%.2f", amount), accountID);

                    // Message for user
                    JOptionPane.showMessageDialog(null,
                            String.format("$%.2f has been withdrawn from your account.", amount),
                            "Transaction Status", JOptionPane.INFORMATION_MESSAGE);

                    new Menu(am, tm, hlm, account);
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            String.format("You can only perform withdrawal %d times a day. Transaction is denied.", TRANSACTION_NUMBER_LIMIT),
                            "Transaction Status", JOptionPane.ERROR_MESSAGE);

                    new Menu(am, tm, hlm, account);
                }
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Amount exceeds balance! Transaction is denied.",
                        "Transaction Status", JOptionPane.ERROR_MESSAGE);

                new Menu(am, tm, hlm, account);
            }
        }
        else {
            JOptionPane.showMessageDialog(null,
                    String.format("Maximum amount allowed to withdraw is $%.2f. Transaction is denied.", TRANSACTION_AMOUNT_LIMIT),
                    "Transaction Status", JOptionPane.ERROR_MESSAGE);

            new Menu(am, tm, hlm, account);
        }
    }
}
