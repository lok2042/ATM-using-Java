package _new;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author lok2042
 * TransactionsManager manages existing and new Transaction(s) using LinkedLists
 */
public class TransactionsManager {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private LinkedList<Transaction> todayTransactions;  // Existing transactions for today
    private LinkedList<Transaction> newTransactions;    // New transactions to be added
    private int newAssignID;                            // Auto ID assignment for Transaction ID

    /**
     * Constructs an instance of TransactionsManager
     */
    public TransactionsManager() {
        todayTransactions = new LinkedList<Transaction>();
        newTransactions = new LinkedList<Transaction>();
        newAssignID = 0;

        try {
            populateTransactions();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Populates todayTransactions with Transaction objects obtained from transactions.txt
     * Only transactions with today's date are added to todayTransactions
     * @throws IOException      An error occurs when reading transactions.txt
     */
    public void populateTransactions() throws IOException {
        FileReader fr = new FileReader("transactions.txt");
        BufferedReader br = new BufferedReader(fr);

        // Today's date
        String today = DATE_FORMAT.format(new Date());

        String line;
        while((line = br.readLine()) != null) {
            String [] tokens = line.split(",");

            int id = Integer.parseInt(tokens[0]);
            newAssignID = (id >= newAssignID) ? (id + 1) : newAssignID;

            String date = tokens[1];
            if(date.equals(today)) {

                TransactionType type = switch (tokens[3]) {
                    case "DEPOSIT" -> TransactionType.DEPOSIT;
                    case "WITHDRAWAL" -> TransactionType.WITHDRAWAL;
                    case "TRANSFER" -> TransactionType.TRANSFER;
                    default -> TransactionType.NULL;
                };

                todayTransactions.add(new Transaction(
                        id,
                        date,
                        tokens[2],
                        type,
                        Double.parseDouble(tokens[4]),
                        Integer.parseInt(tokens[5]))
                );
            }
        }

        br.close();
        fr.close();
    }

    /**
     * Adds a new Transaction object to newTransactions
     * @param date              Date of transaction
     * @param time              Time of transaction
     * @param transactionType   Type of transaction
     * @param amount            Amount of transaction
     * @param accountID         Account ID
     */
    public void addNewTransaction(String date, String time,
                                  TransactionType transactionType, double amount, int accountID) {
        newTransactions.add(new Transaction(newAssignID, date, time, transactionType, amount, accountID));
        newAssignID++;
    }

    /**
     * Counts the number of the specified transactions already performed by an account today
     * @param accountID         Account ID
     * @param transactionType   Type of transaction
     * @return
     */
    public int getNumberOfTransaction(int accountID, TransactionType transactionType) {
        int count = 0;
        for(Transaction t : todayTransactions) {
            if(t.getAccountID() == accountID && t.getTransactionType() == transactionType)
                count++;
        }
        for(Transaction t : newTransactions) {
            if(t.getAccountID() == accountID && t.getTransactionType() == transactionType)
                count++;
        }
        return count;
    }

    /**
     * Appends latest data from newTransactions to update transactions.txt
     * @throws IOException      An error occurs when writing transactions.txt
     */
    public void updateTransactions() throws IOException {
        FileWriter fw = new FileWriter("transactions.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        StringBuilder str = new StringBuilder();
        for(Transaction newTransaction : newTransactions) {
            str.append(newTransaction);
        }

        bw.write(String.valueOf(str));

        bw.close();
        fw.close();
    }
}
