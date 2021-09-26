package ATM;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Customer {
	
	private String name;
	private int balance;
	private  HashMap<String, Integer> Debt;
	private  HashMap<String, Integer> Debtor;

	public String GetName() {
		return name;
	}
	
	public int GetBalance() {
		return balance;
	}

	public Customer(String name) {
		super();
		this.name = name;
		this.balance = 0;
		this.Debt = new HashMap<String,Integer>();
		this.Debtor = new HashMap<String,Integer>();
	}
	

	public HashMap<String, Integer> getDebt() {
		return Debt;
	}

	public HashMap<String, Integer> getDebtor() {
		return Debtor;
	}

	public void Deposit(int depositValue) {
		balance += depositValue;
	}
	
	public void Withdraw(int withdrawValue) {
		balance -= withdrawValue;
	}
	
	public void Transfer(Customer target, int transferAmount) {
		if (transferAmount > balance) {
			return;
		}
		Withdraw(transferAmount);
		target.Transfered(transferAmount);
	}
	
	public void debt(Customer target, int debt) {
		String customerName = target.GetName();
		Integer debtValue = Debt.get(customerName);
		if (Objects.isNull(debtValue)){
			Debt.put(customerName, new Integer(debt));
		}else {
			Debt.put(customerName, new Integer(debt + debtValue));
		}
		return;
		/*
		Integer debtValue = Debt.get(target.GetName());
		debtValue += debt;
		Debt.put(target.GetName(), debtValue);
		target.owed(this, debt);
		return;
		*/
	}
	
	public void owed(Customer debtorCustomer,int owed) {
		String customerName = debtorCustomer.GetName();
		Integer owedValue = Debtor.get(customerName);
		if (Objects.isNull(owedValue)){
			Debtor.put(customerName, new Integer(owed));
		}else {
			Debtor.put(customerName, new Integer(owed + owedValue));
		}
		return;
	}
	
	public void Transfered(int transferAmount) {
		Deposit(transferAmount);
		return;
	}
	
	public String GetDebt() {
		String result = "";
		for ( Map.Entry<String, Integer> entry : Debt.entrySet() ) {
		    String key = entry.getKey();
		    Integer value = entry.getValue();
		    if (!result.equals("")) {
		    	result += "\n";
		    }
		    result += "Owed $" + value +" to "+ key;
		}
		return result;
	}
	
	public String GetDebtor() {
		String result = "";
		for ( Map.Entry<String, Integer> entry : Debtor.entrySet() ) {
		    String key = entry.getKey();
		    Integer value = entry.getValue();
		    if (!result.equals("")) {
		    	result += "\n";
		    }
		    result += "Owed $" + value +" from "+ key;
		}
		return result;
	}
	
	public int GetBalanceWithOwed() {
		int total = 0;
		total += balance;

		for (Integer value : Debtor.values()) {
		    total += value;
		}
		return total;
	}
}
