package _new;

/**
 * @author lok2042
 * A Transaction stores the details for a transaction, i.e. Withdrawal, Deposit, Transfer
 * Each Transaction has an account ID to associate itself with the person who performs the transaction
 */
public class Transaction {

    private int transactionID;
    private String date;
    private String time;
    private TransactionType type;
    private double amount;
    private int accountID;

    /**
     * Constructs a new Transaction
     * @param transactionID     Transaction ID
     * @param date              Date in dd/MM/yyyy
     * @param time              Time in H:mm:ss
     * @param transactionType   Type of transaction (Withdrawal, Deposit, or Transfer)
     * @param amount            Amount of transaction
     * @param accountID         Account ID to link the account that performs the transaction
     */
    public Transaction(int transactionID, String date, String time,
                       TransactionType transactionType, double amount, int accountID) {
        this.transactionID = transactionID;
        this.date = date;
        this.time = time;
        this.type = transactionType;
        this.amount = amount;
        this.accountID = accountID;
    }

    /**
     * Returns transaction ID
     * @return  transactionID
     */
    public int getTransactionID() {
        return transactionID;
    }

    /**
     * Returns date of transaction
     * @return  date
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns time of transaction
     * @return  time
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns transaction type
     * @return  type
     */
    public TransactionType getTransactionType() {
        return type;
    }

    /**
     * Returns transaction amount
     * @return  amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns account ID
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }

    /**
     * Returns a string representation of current instance
     * @return  a string
     */
    public String toString() {
        return transactionID + "," + date + "," + time + "," + type + ","
                + String.format("%.2f", amount) + "," + accountID + "\n";
    }
}
