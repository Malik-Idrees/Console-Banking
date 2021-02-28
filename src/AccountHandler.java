

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.io.FileWriter;

public class AccountHandler {
	private String accountListFilename;
    private Map<String, Integer[]> listAllUsers;
    private int totalAccounts;

    public AccountHandler(String accountListFile) {
        this.accountListFilename = accountListFile;
        totalAccounts = 0;
        //it will store all present accounts in a key:value pair
        listAllUsers = new HashMap<String, Integer[]>();
        loadAllAccountsFromFile();
        accountSerialize();
    }
    
    //it basically loads all existing accounts into our system on startup from acccountlistFile
    private void loadAllAccountsFromFile() {
    	File accountListFile = new File(accountListFilename);
        if(accountListFile.exists()) {
            try(BufferedReader in = new BufferedReader(new FileReader(accountListFile))) {
                String temp;
                temp = in.readLine();
                totalAccounts = Integer.parseInt(temp);
                Integer[] passworAndBalance = {0,0};
                while((temp = in.readLine()) != null) {
                	//splits on white space The line contains username pincode separated by space
                	//str[0] have username and str[1] have pincode
                    String[] strs = temp.split("\\s");
                    
                    if(strs.length >= 2) {
                    	passworAndBalance[0]= Integer.parseInt(strs[1]);
                    	passworAndBalance[1]= 0;
                    	if(strs.length == 3) {
                    		passworAndBalance[1]= Integer.parseInt(strs[2]);
                    		listAllUsers.put(strs[0], passworAndBalance);
                    	}
                    	else {
                    		listAllUsers.put(strs[0], passworAndBalance);
                    	}
                    }
                    else {System.out.println("Account list file format invalid.");}
                }
            } catch(IOException e) {
                System.out.println("Error reading file.");
            } catch(NumberFormatException e) {
                System.out.println("Account number format invalid.");
            }
        }
    }
    //this method converts all users loaded above into serializers
    //All it does is it makes our system faster so that we dont have to read and write to file for every user
    //.ser is created for All users
    public void accountSerialize() {
    	for ( Entry<String, Integer[]> entry : listAllUsers.entrySet() ) {
    		String name = entry.getKey();
    		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name + ".ser"))) {
    			//Integer[] pinAndPassword = {0,0};
    			Integer[] getValue = entry.getValue();
    			
    			Integer pin = getValue[0];
    			String pincode = String.valueOf(pin);
    			Integer bankBalance = getValue[1];
    			
    			BigDecimal value = new BigDecimal(bankBalance);
    			Account account = new Account(0, name, pincode,value);
    			out.writeObject(account);
    			}
    		catch(IOException e) {
    			System.out.println("Account file corropted.");
    }
    }}
    public int getNumberOfAccounts() {
        return listAllUsers.size();
    }

    public int getNumberOfAccountsOpened() {
        return totalAccounts;
    }
    //It returns the account of a user if it exists
    //It searches for user in [listAllUsers]
    public Account getAccount(String accountName) {
    	//an instance of account class
        Account account = null;

        if(listAllUsers.containsKey(accountName)) {
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(accountName.trim() + ".ser"))) {
                account = (Account) in.readObject();
            } catch(IOException e) {
                System.out.println("Account file not found.");
            } catch(ClassNotFoundException e) {
                System.out.println("Error reading object from file.");
            }
        }

        return account;
    }
  //It saves the account of a user in .ser class
    public void saveAccount(Account account) {
        if(!listAllUsers.containsKey(account.getAccountHolderName())) {
        	Integer pincode =Integer.parseInt(account.getPin());
        	BigDecimal value = account.getBalance();
        	Integer balance =  value.intValue();
        	Integer[] pinWithBalance = {pincode,balance};
            listAllUsers.put(account.getAccountHolderName(), pinWithBalance);
            totalAccounts++;
        }
        
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(account.getAccountHolderName() + ".ser"))) {
            out.writeObject(account);
        } catch(IOException e) {
            System.out.println("Error writing account information to file.");
        }
    }
  //It Updates the account of a user in .ser class
    public void updateAccount(Account account) {
        if(listAllUsers.containsKey(account.getAccountHolderName())) {
        	Integer pincode =Integer.parseInt(account.getPin());
        	BigDecimal value = account.getBalance();
        	Integer balance =  value.intValue();
        	Integer[] pinWithBalance = {pincode,balance};
            listAllUsers.put(account.getAccountHolderName(), pinWithBalance);
            totalAccounts++;
        }
        
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(account.getAccountHolderName() + ".ser"))) {
            out.writeObject(account);
        } catch(IOException e) {
            System.out.println("Error writing account information to file.");
        }
    }
  //In the end when we shutdown our system all the users stored in .ser class(not visible outside java)  
    //will be written to list.txt file
    public void updateAccountsRecords() {
        try(BufferedWriter out = new BufferedWriter(new FileWriter(accountListFilename))) {
            out.write(totalAccounts + "\n");
          for ( Entry<String, Integer[]> entry : listAllUsers.entrySet() ) {
            	
                String name = entry.getKey();
                Integer[] getValue = entry.getValue();
                Integer pin = getValue[0];
                
                //String pincode = String.valueOf(pin);
    			Integer bankBalance = getValue[1];
                
                out.write(name + " " + pin + " "+bankBalance+"\n");
            }
        } catch(IOException e) {
            System.out.println("Error writing account list to file.");
        }
    }

    //It deletes the account of a user
    public void deleteAccount(String userName) {
        if(!listAllUsers.containsKey(userName)) System.out.println("User do not exist");
        else {
            File accountFile = new File(userName + ".ser");
            if(accountFile.exists()) accountFile.delete();
            listAllUsers.remove(userName);
            
        }
    }
    
  //Update user balance in .ser class
    public void updateBalance(Account account) {
        if(listAllUsers.containsKey(account.getAccountHolderName())) {
        	Integer pincode =Integer.parseInt(account.getPinCode());
        	BigDecimal value = account.getBalance();
        	Integer balance =  value.intValue();
        	Integer[] pinWithBalance = {pincode,balance};
        	deleteAccount(account.getAccountHolderName());
            listAllUsers.put(account.getAccountHolderName(), pinWithBalance);
            totalAccounts++;
        }
        
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(account.getAccountHolderName() + ".ser"))) {
            out.writeObject(account);
        } catch(IOException e) {
            System.out.println("Error writing account information to file.");
        }
    }
    
}
