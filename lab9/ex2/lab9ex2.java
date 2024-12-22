class BankAccount{
    private int balance=0;

    public synchronized void deposit(int amount){
        balance+=amount;
        System.out.println("Deposited "+amount+", new balance: "+balance);
    }

    public synchronized void withdraw(int amount){
        if(balance>=amount){
            balance-=amount;
            System.out.println("Withdrew "+amount+", new balance: "+balance);
        }else{
            System.out.println("Insufficient funds to withdraw "+amount+", current balance: "+balance);
        }
    }

    public int getBalance(){
        return balance;
    }
}

class DepositThread extends Thread{
    private BankAccount account;
    private int amount;

    public DepositThread(BankAccount account, int amount){
        this.account=account;
        this.amount=amount;
    }

    public void run(){
        account.deposit(amount);
    }
}

class WithdrawThread extends Thread{
    private BankAccount account;
    private int amount;

    public WithdrawThread(BankAccount account, int amount){
        this.account=account;
        this.amount=amount;
    }

    public void run(){
        account.withdraw(amount);
    }
}

public class lab9ex2{
    public static void main(String[] args){
        BankAccount account=new BankAccount();

        Thread t1=new DepositThread(account, 100);
        Thread t2=new WithdrawThread(account, 50);
        Thread t3=new DepositThread(account, 200);
        Thread t4=new WithdrawThread(account, 150);
        Thread t5=new WithdrawThread(account, 1000);
        Thread t6=new WithdrawThread(account, 500);
        Thread t7=new DepositThread(account, 2000);

        try{
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
            t4.start();
            t4.join();
            t5.start();
            t5.join();
            t6.start();
            t6.join();
            t7.start();
            t7.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Final balance: "+account.getBalance());
    }
}