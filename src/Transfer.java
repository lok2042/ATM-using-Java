package _new;

import javax.swing.*;
import java.util.Date;

/**
 * @author lok2042
 * Handles a transfer transactions
 */
public class Transfer extends TransactionOption {

    // Maximum $2,000.00 per transaction, 4 times per day allowed
    private static final double TRANSACTION_AMOUNT_LIMIT = 2000.00;
    private static final int TRANSACTION_NUMBER_LIMIT = 4;

    private Account recipientAccount;
    private double amount;

    public Transfer(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account, Account recipientAccount, String amountStr) {
        super(am, tm, hlm, account, account.getAccountID());
        this.recipientAccount = recipientAccount;
        this.amount = Double.parseDouble(amountStr);

        processTransaction();
    }

    @Override
    public boolean allowTransaction() {
        return (tm.getNumberOfTransaction(accountID, TransactionType.TRANSFER) < TRANSACTION_NUMBER_LIMIT);
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

                    // Decreases sender's account balance and increases recipient's account balance
                    am.decreaseAccountBalance(accountID, amount);
                    am.increaseAccountBalance(recipientAccount.getAccountID(), amount);

                    // Adds a new transaction record
                    tm.addNewTransaction(date, time, TransactionType.TRANSFER, amount, accountID);

                    // Adds a new history log
                    hlm.addNewHistoryLog(date, time,
                            String.format("SENT TRANSFER TO %s: -$%.2f", account.getAccountNumber(), amount),
                            accountID);
                    hlm.addNewHistoryLog(date, time,
                            String.format("RECEIVED TRANSFER FROM %s: +$%.2f", account.getAccountNumber(), amount),
                            recipientAccount.getAccountID());

                    // Message for user
                    JOptionPane.showMessageDialog(null,
                            String.format("$%.2f has been transferred from your account", amount),
                            "Transaction Status", JOptionPane.INFORMATION_MESSAGE);

                    new Menu(am, tm, hlm, account);
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            String.format("You can only perform transfer %d times a day. Transaction is denied.", TRANSACTION_NUMBER_LIMIT),
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
                    String.format("Maximum amount allowed to transfer is $%.2f. Transaction is denied.", TRANSACTION_AMOUNT_LIMIT),
                    "Transaction Status", JOptionPane.ERROR_MESSAGE);

            new Menu(am, tm, hlm, account);
        }
    }
}
