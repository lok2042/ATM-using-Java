package _new;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author lok2042
 * HistoryLogsManager manages existing and new HistoryLog(s) using LinkedLists
 */
public class HistoryLogsManager {

    private LinkedList<HistoryLog> existingHistoryLogs; // List of existing history logs for a specific account
    private LinkedList<HistoryLog> newHistoryLogs;      // List of new history logs for a specific account
    private int accountID;                              // Account ID of the specific account
    private int newAssignID;                            // Auto ID assignment for History Log ID

    /**
     * Constructs an instance of HistoryLogsManager
     * @param accountID     Account ID of the specific account
     */
    public HistoryLogsManager(int accountID) {
        existingHistoryLogs = new LinkedList<HistoryLog>();
        newHistoryLogs = new LinkedList<HistoryLog>();
        this.accountID = accountID;

        try {
            populateExistingHistoryLogs();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Populates existingHistoryLogs with HistoryLog objects obtained from history-logs.txt
     * Only history logs with accountID same as the given accountID are added
     * @throws IOException      An error occurs when reading history-logs.txt
     */
    public void populateExistingHistoryLogs() throws IOException {
        FileReader fr = new FileReader("history-logs.txt");
        BufferedReader br = new BufferedReader(fr);

        String line;
        while((line = br.readLine()) != null) {
            String [] tokens = line.split(",");

            int id = Integer.parseInt(tokens[0]);
            newAssignID = (id >= newAssignID) ? (id + 1) : newAssignID;

            int accID = Integer.parseInt(tokens[4]);
            if(accID == accountID) {
                existingHistoryLogs.add(new HistoryLog(
                        id,
                        tokens[1],
                        tokens[2],
                        tokens[3],
                        accID)
                );
            }
        }

        br.close();
        fr.close();
    }

    /**
     * Adds a new HistoryLog object to newHistoryLogs
     * @param date          Date
     * @param time          Time
     * @param description   Description
     * @param accountID     Account ID
     */
    public void addNewHistoryLog(String date, String time, String description, int accountID) {
        newHistoryLogs.add(new HistoryLog(newAssignID, date, time, description, accountID));
        newAssignID++;
    }

    /**
     * Returns a list containing one or more formatted strings of history logs for a given account
     * @param accountID     Account ID
     * @return              logs
     */
    public LinkedList<String> getAccountHistoryLogs(int accountID) {
        LinkedList<String> logs = new LinkedList<String>();

        for(HistoryLog e : existingHistoryLogs) {
            logs.add(e.toFormattedString());
        }

        // Since newHistoryLogs may contain other account's logs (usually recipient account during transfer),
        // hence only the history logs with the given accountID are added.
        for(HistoryLog n : newHistoryLogs) {
            if(n.getAccountID() == accountID)
                logs.add(n.toFormattedString());
        }

        return logs;
    }

    /**
     * Appends latest data from newHistoryLogs to update history-logs.txt
     * @throws IOException      An error occurs when writing history-logs.txt
     */
    public void updateHistoryLogs() throws IOException {
        FileWriter fw = new FileWriter("history-logs.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        StringBuilder str = new StringBuilder();
        for(HistoryLog newHistoryLog : newHistoryLogs) {
            str.append(newHistoryLog);
        }

        bw.write(String.valueOf(str));

        bw.close();
        fw.close();
    }
}
