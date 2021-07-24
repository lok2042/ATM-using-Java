package _new;

import java.text.SimpleDateFormat;

/**
 * @author lok2042
 * TransactionOption is an abstract class
 * It serves as parent class for Deposit, Withdrawal, and Transfer
 */
public abstract class TransactionOption {

    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    protected static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("H:mm:ss");

    protected String date;
    protected String time;

    // Copies of AccountsManager, TransactionsManager, HistoryLogsManager, Account, and account ID
    protected AccountsManager am;
    protected TransactionsManager tm;
    protected HistoryLogsManager hlm;
    protected Account account;
    protected int accountID;

    public TransactionOption(AccountsManager am, TransactionsManager tm, HistoryLogsManager hlm, Account account, int accountID) {
        this.am = am;
        this.tm = tm;
        this.hlm = hlm;
        this.account = account;
        this.accountID = accountID;
    }

    public abstract boolean allowTransaction();
    public abstract void processTransaction();
}
