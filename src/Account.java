

import java.math.BigDecimal;
import java.math.MathContext;
import java.io.Serializable;

public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	private int accountNumber;
	private String accountHolderName;
    private String pinCode;
    private BigDecimal balance;
    
    public Account() {}
    
    public Account(int accountNumber, String accountHolderName, String pinCode, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pinCode = pinCode;
        this.balance = balance;
    }
    //MathContext.DECIMAL32 shows a precision up to 7 decimals e.g 0.0000001
    // it will Add the amount passed to this function to total balance
    public void makeDeposit(BigDecimal amount) {
        balance = balance.add(amount, MathContext.UNLIMITED);
    }

    // it will deduct the amount passed to this function from total balance
    public void withdrawFunds(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
    //getter and setters
    public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getPin() {
		return pinCode;
	}
	
	//encrypted pincode is first changed to its original form and then return it
	public String getPinCode() {
		String pincode = getPin();
		Decode decode = new Decode();
		String pin = decode.decrypt(pincode);

		return pin;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
