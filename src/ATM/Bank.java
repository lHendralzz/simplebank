package ATM;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bank {
	

	public static final String ERROR_TRANSFER_TARGET_NULL = "Credit Customer is Null";
	
	private  HashMap<String, Customer> CustomerDatabase = new HashMap<String,Customer>();
	
	public Customer Login(String customerName) {
		
		Customer existCustomer = null;
		existCustomer = CustomerDatabase.get(customerName);
		if (!Objects.isNull(existCustomer)){
			return existCustomer;
		}
		
		Customer newCustomer = new Customer(customerName);
		CustomerDatabase.put(customerName, newCustomer);
		return newCustomer;
	}

	public void Deposit(Customer customer, int amount) {

		for ( Map.Entry<String, Integer> entry : customer.getDebt().entrySet() ) {
		    String key = entry.getKey();
		    Integer value = entry.getValue();
		    if (amount >= value ){
		    	customer.getDebt().remove(key);
		    	amount -= value;
		    	
		    	Customer settledCustomer = CustomerDatabase.get(key);
		    	settledCustomer.getDebtor().remove(customer.GetName());
		    	Deposit(settledCustomer, value);
		    	
		    }else {
		    	int remainDeb = value - amount;
		    	customer.getDebt().put(key, remainDeb);
		    	Customer settledCustomer = CustomerDatabase.get(key);
		    	settledCustomer.getDebtor().put(customer.GetName(), remainDeb);
		    	Deposit(settledCustomer, amount);
		    	amount = 0;
		    	return;
		    }
		}
		customer.Deposit(amount);
		
		return;
	}
	
	public String Withdraw(Customer customer, int amount) {
		if (amount > customer.GetBalance()) {
			return "Withdraw More Than Balance";
		}
		customer.Withdraw(amount);
		return "";
	}
	
	public Customer GetCustomer(String customerName) {
		Customer existCustomer = null;
		existCustomer = CustomerDatabase.get(customerName);
		if (!Objects.isNull(existCustomer)){
			return existCustomer;
		}
		return null;
	}
	
	public String Transfer(Customer debit, Customer credit, int amount) {
		if (Objects.isNull(credit)){
			return ERROR_TRANSFER_TARGET_NULL;
		}
		if (debit.GetBalance() < amount ){
			int transferedAmount = debit.GetBalance();
			int debt = amount - transferedAmount;
			debit.Transfer(credit, debit.GetBalance());
			System.out.println("Transferred $" + transferedAmount + " to " + credit.GetName());
			debit.debt(credit, debt);
			credit.owed(debit, debt);
		}else {
			debit.Transfer(credit, amount);
			System.out.println("Transferred $" + amount + " to " + credit.GetName());
		}
		return "";
	}
	
	public String PrintBalance(Customer customer) {
		String printString = "";
		int balance = customer.GetBalanceWithOwed();
		printString += "Your Balance is $" + balance;
		String debtString = customer.GetDebt();
		String debtorString = customer.GetDebtor();
		if (!debtString.equals("")) {
			printString += "\n" + debtString;
		}
		if (!debtorString.equals("")) {
			printString += "\n" + debtorString;
		}
		return printString;
	}
}
