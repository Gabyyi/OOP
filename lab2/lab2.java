import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

interface Storable {
    void store(String file);
}

class Client implements Storable {
    private String name;
    private String bday;
    private String cnp;
    private ArrayList<Account> accounts;

    public Client(String name, String bday, String cnp) {
        this.name = name;
        this.bday = bday;
        this.cnp = cnp;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account A) {
        accounts.add(A);
        System.out.println("Account added for " + name);
    }

    public boolean remAccount(Account A) {
        if (accounts.contains(A)) {
            accounts.remove(A);
            System.out.println("Account removed for " + name);
            return true;
        } else {
            System.out.println("Account not found for " + name);
            return false;
        }
    }

    public void showAccounts() {
        System.out.println("Accounts for " + name + ":");
        for (Account account : accounts) {
            System.out.println("- IBAN: " + account.getIban() + ", Balance: " + account.getAmount());
        }
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Client Name: ").append(name).append("\n");
        sb.append("Birthday: ").append(bday).append("\n");
        sb.append("CNP: ").append(cnp).append("\n");
        sb.append("Accounts: \n");
        for (Account account : accounts) {
            sb.append(account.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void store(String file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(this.toString());
            System.out.println("Client data has been written to file: " + file);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}

abstract class Account implements Comparable<Account>, Storable {
    private String iban;
    protected int amount;

    public Account(String iban, int amount) {
        this.iban = iban;
        this.amount = amount;
    }

    public abstract boolean withdraw(int amount);

    public abstract void deposit(int amount);

    public String getIban() {
        return iban;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Account other) {
        return this.iban.compareTo(other.iban);
    }

    @Override
    public void store(String file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(this.toString());
            System.out.println("Account data has been written to file: " + file);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "IBAN: " + iban + ", Balance: " + amount;
    }
}

class DebitAccount extends Account {
    public DebitAccount(String iban, int amount) {
        super(iban, amount);
    }

    public void deposit(int amount) {
        this.amount += amount;
        System.out.println("Deposited " + amount + " into debit account. New balance: " + this.amount);
    }

    public boolean withdraw(int amount) {
        if (amount <= this.amount) {
            this.amount -= amount;
            System.out.println("Withdrew " + amount + " from debit account. New balance: " + this.amount);
            return true;
        } else {
            System.out.println("Insufficient funds in debit account.");
            return false;
        }
    }
}

class CreditAccount extends Account {
    private int maxCredit;

    public CreditAccount(String iban, int amount, int maxCredit) {
        super(iban, amount);
        this.maxCredit = maxCredit;
    }

    public void deposit(int amount) {
        this.amount += amount;
        System.out.println("Deposited " + amount + " into credit account. New balance: " + this.amount);
    }

    public boolean withdraw(int amount) {
        if (amount <= this.amount + maxCredit) {
            this.amount -= amount;
            System.out.println("Withdrew " + amount + " from credit account. New balance: " + this.amount);
            return true;
        } else {
            System.out.println("Exceeds credit limit.");
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Max Credit: " + maxCredit;
    }
}

public class lab2 {
    public static void main(String[] args) {
        Client client = new Client("John", "23/08/2004", "12345");

        DebitAccount debitAccount = new DebitAccount("DEBIT", 0);
        CreditAccount creditAccount = new CreditAccount("CREDIT", 0, 10000);

        int comp=debitAccount.compareTo(creditAccount);
        if(comp>0){
            System.out.println("debit is grater than credit");
        }
        if(comp<0){
            System.out.println("debit is less than credit");
        }
        if(comp==0){
            System.out.println("debit equal to credit");
        }

        client.addAccount(debitAccount);
        client.addAccount(creditAccount);

        // client.showAccounts();

        //debitAccount.deposit(1500);
        //debitAccount.withdraw(200);
        //debitAccount.withdraw(2000);

        //creditAccount.deposit(2000);
        //creditAccount.withdraw(300);
        //creditAccount.withdraw(2000);

        // client.showAccounts();

        // client.remAccount(debitAccount);
        // client.showAccounts();

        client.store("client_data.txt");
    }
}
