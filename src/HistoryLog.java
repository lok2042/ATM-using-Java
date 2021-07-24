package _new;

/**
 * @author lok2042
 * A HistoryLog stores the details for a log made by a specific account
 */
public class HistoryLog {

    private int historyID;
    private String date;
    private String time;
    private String description;
    private int accountID;

    /**
     * Constructs a new HistoryLog
     * @param historyID     History Log ID
     * @param date          Date in dd/MM/yyyy
     * @param time          Time in H:mm:ss
     * @param description   Description of log
     * @param accountID     Account ID to link the account that creates the log
     */
    public HistoryLog(int historyID, String date, String time, String description, int accountID) {
        this.historyID = historyID;
        this.date = date;
        this.time = time;
        this.description = description;
        this.accountID = accountID;
    }

    /**
     * Returns account ID
     * @return accountID
     */
    public int getAccountID() {
        return accountID;
    }

    /**
     * Returns date, time and description (for displaying history logs)
     * @return  a string
     */
    public String toFormattedString() {
        return String.format("(%s, %s)  -  %s", date, time, description);
    }

    /**
     * Returns a string representation of current instance
     * @return  a string
     */
    public String toString() {
        return historyID + "," + date + "," + time + "," + description + "," + accountID + "\n";
    }
}
