package _new;

import javax.swing.*;
import java.util.Date;

/**
 * @author lok2042
 * Handles a deposit transactions
 */
public class Deposit extends TransactionOption {

    // Maximum $3,000.00 per transaction, 3 times per day allowed
    private static final double TRANSACTION_AMOUNT_LIMIT = 3000.00;
    private static final int TRANSACTION_NUMBER_LIMIT = 3;

    private double amount;

    public Deposit(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account, String amountStr) {
        super(am, tm, hlm, account, account.getAccountID());
        this.amount = Double.parseDouble(amountStr);

        processTransaction();
    }

    @Override
    public boolean allowTransaction() {
        return (tm.getNumberOfTransaction(accountID, TransactionType.DEPOSIT) < TRANSACTION_NUMBER_LIMIT);
    }

    @Override
    public void processTransaction() {
        // #1 Check - Amount shall not exceed TRANSACTION_AMOUNT_LIMIT per transaction
        if(amount <= TRANSACTION_AMOUNT_LIMIT) {

            // #2 Check - Number of transactions in a day shall not exceed TRANSACTION_NUMBER_LIMIT
            if(allowTransaction()) {
                Date now = new Date();
                date = DATE_FORMAT.format(now);
                time = TIME_FORMAT.format(now);

                // Increases account balance
                am.increaseAccountBalance(accountID, amount);

                // Adds a new transaction record
                tm.addNewTransaction(date, time, TransactionType.DEPOSIT, amount, accountID);

                // Adds a new history log for deposit
                hlm.addNewHistoryLog(date, time,  String.format("DEPOSIT: +$%.2f", amount), accountID);

                // Message for user
                JOptionPane.showMessageDialog(null,
                        String.format("$%.2f has been deposited into your account.", amount),
                        "Transaction Status", JOptionPane.INFORMATION_MESSAGE);

                new Menu(am, tm, hlm, account);
            }
            else {
                JOptionPane.showMessageDialog(null,
                        String.format("You can only perform deposit %d times a day. Transaction is denied.", TRANSACTION_NUMBER_LIMIT),
                        "Transaction Status", JOptionPane.ERROR_MESSAGE);

                new Menu(am, tm, hlm, account);
            }
        }
        else {
            JOptionPane.showMessageDialog(null,
                    String.format("Maximum amount allowed to deposit is $%.2f. Transaction is denied.", TRANSACTION_AMOUNT_LIMIT),
                    "Transaction Status", JOptionPane.ERROR_MESSAGE);

            new Menu(am, tm, hlm, account);
        }
    }
}
