package _new;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lok2042
 * Serves as an intermediary after a sucessful sign-in before user is taken to the Menu frame
 * Creates new instances of TransactionsManager and HistoryLogsManager
 */
public class Intermediary {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("H:mm:ss");

    // New TransactionsManager
    private TransactionsManager tm;

    // New HistoryLogsManager
    private HistoryLogsManager hlm;

    public Intermediary(AccountsManager am, Account account) {
        tm = new TransactionsManager();
        hlm = new HistoryLogsManager(account.getAccountID());

        // Adds a new history log for successful sign-in
        Date now = new Date();
        String date = DATE_FORMAT.format(now);
        String time = TIME_FORMAT.format(now);
        hlm.addNewHistoryLog(date, time, "SIGNED IN", account.getAccountID());

        new Menu(am, tm, hlm, account);
    }
}
