
import java.util.Scanner;

import java.math.BigDecimal;
import java.math.MathContext;

public class BankCore {
	private AccountHandler AccountHandler;
	boolean close = false;
	Integer invalidTries = 0;
	
    public BankCore() {}

    public BankCore(String accountListFilename) {
    	AccountHandler = new AccountHandler(accountListFilename);
    }
    
    
    public void startUp() {
    	boolean endTask = false;
        String accountName;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Bank -- We care for your Future");
        System.out.println("Please provide your username or your nickname to proceed");
        accountName = sc.nextLine();
        System.out.println("Welcome, " + accountName);

        do {
        	if(close) {
        		System.exit(0);
        	}
            String actionString;
            char type;
            displayMenu();
            actionString = sc.nextLine();
            type = actionString.charAt(0);

            switch(type) {
                case '1':
                	perfromTransaction(accountName, "CREATE_ACCOUNT");
                	AccountHandler.updateAccountsRecords();
                    break;
                case '2':
                	perfromTransaction(accountName, "CHECK_BALANCE");
                    break;
                case '3':
                	perfromTransaction(accountName, "WITHDRAW_FUNDS");
                    break;
                case '4':
                	perfromTransaction(accountName, "DEPOSIT_FUNDS");
                    break;
                case '5':
                	perfromTransaction(accountName, "DELETE_ACCOUNT");
                    break;
                case '6':
                	endTask = true;
                    AccountHandler.updateAccountsRecords();
                    System.out.println("Thank you for using our services!");
                    System.exit(1);
                    
            }

        } while(!endTask);
    }
    
    private void displayMenu() {
        System.out.println("You can perform Following Actions:");
        System.out.println("  1 - Create Account");
        System.out.println("  2 - Check Balance");
        System.out.println("  3 - Withdraw cash");
        System.out.println("  4 - Deposit cash");
        System.out.println("  5 - Delete Account");
        System.out.println("  6 - End Transactions");
        System.out.println("Select a choice:");
    }
    
    //saveAccount
    //This is our main method which handles all the transactions of a user
    public void perfromTransaction(String accountName, String transactionType) {
        Scanner sc = new Scanner(System.in);
        Account account = AccountHandler.getAccount(accountName);
        String action = transactionType;
        //makes sure the account is created only if it does not exist
        if(account == null && action == "CREATE_ACCOUNT")  {
            String pin;
            System.out.println("Enter your four-digit PIN: ");
            pin = sc.nextLine();
            while(!validatePin(pin)) {
            	System.out.println("Enter your four-digit PIN: ");
                pin = sc.nextLine();
                
                if (validatePin(pin)) break;
            }
            Encode encode = new Encode();
            String pincode = encode.encrypt(pin);
            account = new Account(0, accountName, pincode, new BigDecimal(500));
            account.setPinCode(pincode);
            System.out.println("Your account is created wift a gift of 500!");
            AccountHandler.saveAccount(account);

        } else if(account == null) {
            System.out.println("You do not have an account with us yet!"); 
        }else {
            //this block of code runs only when an account exists
            String userInput;

            switch(action) {
                case "CREATE_ACCOUNT":
                    System.out.println("Your Account already exists!");
                    break;
                case "CHECK_BALANCE":
                    userInput = userinput();
                    if(userInput.equals(account.getPinCode()))
                        System.out.println("Account Balance: " + account.getBalance().toPlainString());
                    else
                    	protectSystem();
                    break;
                case "DEPOSIT_FUNDS":
                	userInput = userinput();
                	//System.out.println(account.getPin());
                    if(userInput.equals(account.getPinCode())) {
                    	System.out.print("Enter amount to deposit: ");
                        userInput = sc.nextLine();
                        while(!validateAmount(userInput)) {
                        	System.out.print("Enter amount to deposit: ");
                            userInput = sc.nextLine();
                            if (validateAmount(userInput)) break;
                        };
                        account.makeDeposit(new BigDecimal(userInput, MathContext.UNLIMITED));
                      //updating balance value
                        AccountHandler.updateBalance(account);
                        AccountHandler.updateAccount(account);
                    } else protectSystem();
                    break;
                case "WITHDRAW_FUNDS":
                	//System.out.println(account.getPin());
                	userInput = userinput();
                    if(userInput.equals(account.getPinCode())) {
                    	System.out.println("Enter amount to withdraw: ");
                        userInput = sc.nextLine();
                        while(!validateAmount(userInput)) {
                        	System.out.println("Enter amount to withdraw: ");
                            userInput = sc.nextLine();
                            if (validateAmount(userInput)) break;
                        };
                        account.withdrawFunds(new BigDecimal(userInput, MathContext.UNLIMITED));
                      //updating balance value
                        AccountHandler.updateBalance(account);
                        AccountHandler.updateAccount(account);
                    } else {
                    	protectSystem();
                    	}
                    break;
                case "CLOSE_ACCOUNT":
                	AccountHandler.deleteAccount(accountName);
                    break;
            }
            
        }
    }
    
    
    public void  protectSystem() {
    	invalidTries++;
    	if(invalidTries>=3) {
    		close=true;
    		System.out.println("No More Tries Take Care");
    		System.out.println("Thank You!");
    		return;
    	}
    	System.out.println("Wrong PIN.");
    }
    
    public String userinput() {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Enter your PIN: ");
        String inputString = sc.nextLine();
        return inputString;
    }
    
    public boolean isNumber(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
 
    }

    //makes sure the amount is a positive integer
    public boolean validateAmount(String amountString) {
    	if (isNumber(amountString)) {
    		//if its a negative integer return false
    		if(Integer.parseInt(amountString)<0) {
    			System.out.println("Invalid value for amount.");
    			return false;
    			}
    		//otherwise return true
    		else {
    			return true;
    		}
    	}
    	//return False if not a number
    	else { 
    		System.out.println("Invalid value for amount."); 
    		return false;
    	}       
}

//makes sure pin is 4 character long and does not contain letters
    public boolean validatePin(String pin) {
    	if (isNumber(pin)) {
    		String pincode = pin;
    		if(pincode.length()==4) {
    			return true;
    			}
    		else { 
    			System.out.println("Invalid pin."); 
    			return false;
    		}
    	}
    	//return False if not a number
    	else { 
    		System.out.println("Invalid pin."); 
    		return false;
    	}
    }

    
   
}
