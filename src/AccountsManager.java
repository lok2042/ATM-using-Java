package _new;

import java.io.*;
import java.util.ArrayList;

/**
 * @author lok2042
 * AccountsManager stores and manages Account(s) using an ArrayList.
 */
public class AccountsManager {

    private ArrayList<Account> accounts;        // Arraylist to store existing and new accounts
    private int newAssignID;                    // Auto ID assignment for Account ID

    /**
     * Constructs an instance of AccountsManager
     */
    public AccountsManager() {
        accounts = new ArrayList<Account>();
        newAssignID = 0;

        try {
            populateAccounts();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Populates array list with Account objects obtained from accounts.txt
     * @throws IOException      An error occurs when reading accounts.txt
     */
    public void populateAccounts() throws IOException {
        FileReader fr = new FileReader("accounts.txt");
        BufferedReader br = new BufferedReader(fr);

        String line;
        while((line = br.readLine()) != null) {
            String [] tokens = line.split(",");

            int id = Integer.parseInt(tokens[0]);
            newAssignID = (id >= newAssignID) ? (id + 1) : newAssignID;

            accounts.add(
                    new Account(
                            id,
                            tokens[1],
                            Integer.parseInt(tokens[2]),
                            tokens[3],
                            Double.parseDouble(tokens[4])
                    )
            );
        }

        br.close();
        fr.close();
    }

    /**
     * Adds a new Account object to array list
     * @param username          Username (to represent Account number)
     * @param PIN               6-digit PIN number
     * @param name              Name of the owner
     * @param initialDeposit    Initial deposit amount
     */
    public void addNewAccount(String username, int PIN, String name, double initialDeposit) {
        accounts.add(new Account(newAssignID, username, PIN, name, initialDeposit));
        newAssignID++;
    }

    /**
     * Finds out if an account exists
     * @param username          Username (to represent Account number)
     * @return                  an Account object, otherwise null
     */
    public Account doesAccountExist(String username) {
        for(Account a : accounts) {
            if(a.getAccountNumber().equals(username)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Verifies a match for username and PIN
     * @param username          Username (to represent Account number)
     * @param pin               6-digit PIN
     * @return                  true if there is a match, otherwise false
     */
    public boolean signInAccount(String username, int pin) {
        for(Account a : accounts) {
            if(a.getAccountNumber().equals(username) && a.verifyPIN(pin)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks current balance for a given account
     * @param accountID         Account ID
     * @param amount            transaction amount
     * @return                  true if the account is found and transaction amount is less than or
     *                          equal to its current balance, otherwise false
     */
    public boolean checkAccountBalance(int accountID, double amount) {
        for(Account a : accounts) {
            if (a.getAccountID() == accountID) {
                if(a.checkBalance(amount)) return true;
            }
        }
        return false;
    }

    /**
     * Increases current balance for a given account (deposit or received transfer)
     * @param amount    transaction amount
     */
    public void increaseAccountBalance(int accountID, double amount) {
        for(Account a : accounts) {
            if(a.getAccountID() == accountID) {
                a.increaseBalance(amount);
            }
        }
    }

    /**
     * Decreases current balance for a given account (withdrawal or transfer)
     * @param amount    transaction amount
     */
    public void decreaseAccountBalance(int accountID, double amount) {
        for(Account a : accounts) {
            if(a.getAccountID() == accountID) {
                a.decreaseBalance(amount);
            }
        }
    }

    /**
     * Verifies a match for accountID and PIN
     * @param accountID
     * @param currentPin
     * @return
     */
    public boolean verifyCurrentAccountPIN(int accountID, int currentPin) {
        for(Account a : accounts) {
            if(a.getAccountID() == accountID && a.verifyPIN(currentPin)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates new PIN for the account with the given accountID
     * @param accountID
     * @param newPin
     */
    public void changeAccountPIN(int accountID, int newPin) {
        for(Account a : accounts) {
            if(a.getAccountID() == accountID) {
                a.changePIN(newPin);
            }
        }
    }

    /**
     * Rewrites accounts.txt with the latest data inside array list
     * @throws IOException      An error occurs when writing accounts.txt
     */
    public void updateAccounts() throws IOException {
        FileWriter fw = new FileWriter("accounts.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        StringBuilder str = new StringBuilder();
        for(Account account : accounts) {
            str.append(account);
        }

        bw.write(String.valueOf(str));

        bw.close();
        fw.close();
    }

}
