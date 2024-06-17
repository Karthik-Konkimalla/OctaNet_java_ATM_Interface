import java.util.ArrayList;
import java.util.*;

class UserAccount {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public UserAccount(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(UserAccount targetAccount, double amount) {
        if (amount <= balance) {
            balance -= amount;
            targetAccount.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to "+targetAccount.getUserId());
            System.out.println("After transfer the balance in "+targetAccount.getUserId()+"is: "+targetAccount.getBalance());
            
        } else {
            System.out.println("Insufficient balance.");
        }
    }
    
    public void display(){
        System.out.println("BALANCE:"+balance);
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

class ATM {
    private UserAccount currentAccount;
    private Scanner scanner;
    private HashMap<String, UserAccount> accounts;

    public ATM(HashMap<String, UserAccount> accounts) {
        this.scanner = new Scanner(System.in);
        this.accounts = accounts;
    }

    public void start() {
        System.out.println("Welcome to K.K'S bank A.T.M!!");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (accounts.containsKey(userId) && accounts.get(userId).getPin().equals(pin)) {
            this.currentAccount = accounts.get(userId);
            showMenu();
        } else {
            System.out.println("Invalid user ID or PIN.");
        }
    }

    private void showMenu() {
        int choice;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Balance enquiery");
            System.out.println("6. Quit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1:
                    currentAccount.printTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    currentAccount.display();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Have a nice day bye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 
        currentAccount.withdraw(amount);
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 
        currentAccount.deposit(amount);
    }

    private void transfer() {
         System.out.print("Enter target user ID: ");
        String targetUserId = scanner.nextLine();
        if (accounts.containsKey(targetUserId)) {
            UserAccount targetAccount = accounts.get(targetUserId);
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            currentAccount.transfer(targetAccount, amount);
        } else {
            System.out.println("Target user ID not found.");
        }
    }
}

public class AtmInterface {
    public static void main(String[] args) {
        HashMap<String, UserAccount> accounts = new HashMap<>();
        accounts.put("user1", new UserAccount("user1", "1234", 5000.0));
        accounts.put("user2", new UserAccount("user2", "5678", 3000.0));
        accounts.put("user3", new UserAccount("user3", "9101", 1500.0));

        ATM atm = new ATM(accounts);
        atm.start();
    }
}