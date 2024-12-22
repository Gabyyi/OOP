import java.util.ArrayList;

class Client{
    private String name;
    @SuppressWarnings("unused")
    private String bday;
    @SuppressWarnings("unused")
    private String cnp;
    private ArrayList<Account> accounts;

    public Client(String name, String bday, String cnp){
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
}

abstract class Account{
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
}

class Address {
    @SuppressWarnings("unused")
    private String city;
    @SuppressWarnings("unused")
    private int number;
    @SuppressWarnings("unused")
    private String street;

    public Address(String city, int number, String street) {
        this.city = city;
        this.number = number;
        this.street = street;
    }
}

public class Lab1 {
    public static void main(String[] args) {
        Client client1 = new Client("John", "23/08/2004", "12345");

        DebitAccount debitAccount = new DebitAccount("DEBIT", 0);
        CreditAccount creditAccount = new CreditAccount("CREDIT", 0, 1000);

        client1.addAccount(debitAccount);
        client1.addAccount(creditAccount);

        client1.showAccounts();

        debitAccount.deposit(1500);
        debitAccount.withdraw(200);
        debitAccount.withdraw(2000);
        
        creditAccount.deposit(2000);
        creditAccount.withdraw(300);
        creditAccount.withdraw(2000);
        
        client1.showAccounts();
        client1.remAccount(debitAccount);
        client1.showAccounts();
    }
}
