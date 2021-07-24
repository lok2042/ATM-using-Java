package _new;

/**
 * @author lok2042
 * An Account stores a customer's security info, bank details, and current amount
 *
 * Constraints:
 * 1. Since there is no ATM card involved, hence account number / username is used.
 * 2. PIN number cannot begin with 0.
 */
public class Account {

    private int accountID;
    private String accountNumber;  // For simplicity sake, 'username' is used to represent an account number throughout the program
    private int accountPIN;
    private String accountOwner;
    private double accountBalance;

    /**
     * Constructs a new Account
     * @param accountID         Account ID            (e.g., 1)
     * @param accountNumber     Account Number        (e.g., XXXX-XXXX-XXXX-XXXX or John123)
     * @param accountPIN        6-digit PIN number    (e.g., 123456)
     * @param accountOwner      Name of the owner     (e.g., John Smith)
     * @param accountBalance    Current balance       (e.g., 1100.59)
     */
    public Account(int accountID, String accountNumber, int accountPIN, String accountOwner, double accountBalance) {
        this.accountID = accountID;
        this.accountNumber = accountNumber;
        this.accountPIN = accountPIN;
        this.accountOwner = accountOwner;
        this.accountBalance = accountBalance;
    }

    /**
     * Returns account ID
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }

    /**
     * Returns account number (a.k.a. username)
     * @return accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Verifies if entered pin matches account's PIN
     * @param pin   6-digit user entered PIN
     * @return      true if PIN matches, otherwise false
     */
    public boolean verifyPIN(int pin) {
        return (pin == accountPIN);
    }

    /**
     * Changes the account current PIN with a new PIN
     * @param newPin    New PIN
     */
    public void changePIN(int newPin) {
        this.accountPIN = newPin;
    }

    /**
     * Checks current balance to allow a transaction (withdrawal or transfer)
     * @param amount transaction amount
     * @return       true if transaction amount is less than or equal to current balance, otherwise false
     */
    public boolean checkBalance(double amount) {
        return (amount <= accountBalance);
    }

    /**
     * Returns current balance
     * @return  accountBalance
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Increases current balance (deposit or received transfer)
     * @param amount    transaction amount
     */
    public void increaseBalance(double amount) {
        accountBalance += amount;
    }

    /**
     * Decreases current balance (withdrawal or transfer)
     * @param amount    transaction amount
     */
    public void decreaseBalance(double amount) {
        accountBalance -= amount;
    }

    /**
     * Returns owner's name
     * @return  accountOwner
     */
    public String getAccountOwner() {
        return accountOwner;
    }

    /**
     * Returns a string representation of current instance
     * @return  a string
     */
    public String toString() {
        return accountID + "," + accountNumber + "," + accountPIN + "," +
               accountOwner + "," + String.format("%.2f", accountBalance) + "\n";
    }
}
