package ATM;

import java.util.Scanner;
import java.util.Objects;

import com.sun.org.apache.xpath.internal.functions.Function;

public class ATM {

	private Customer loginCustomer;
	private Bank bank;
	public ATM() {
		bank = new Bank();
	}
	
	public void Start(){

	    Scanner myObj = new Scanner(System.in);

		String cmd = "";
		do {
			commandList();
			String firstWord, rest;
			cmd = myObj.nextLine();

			firstWord = getFirstWord(cmd);
			rest = getRestWord(cmd);
			switch (firstWord) {
			case "login":
				Login(rest);
				break;
			case "deposit":
				Deposit(rest);
				break;
			case "withdraw":
				Withdraw(rest);
				break;
			case "transfer":
				Transfer(rest);
				break;
			case "logout":
				Logout(rest);
				break;
			case "exit":
				break;
			default:
				System.out.println("Command Not Found");
				break;
			}
			myObj.nextLine();
		}while(!cmd.equals("exit"));
		
		return;
	}

	private void Login(String Command) {
		if (Command.equals("")){
			System.out.println("Please Input [name] When using Login Command" );
			return;
		}
		if (!Objects.isNull(loginCustomer)) {
			System.out.println("Already Login As " + loginCustomer.GetName());
			return;
		}
		if (Command.contains(" ")) {
			System.out.println("Please Input [name] without Space (\" \") When using Login Command");
			return;
		}
		loginCustomer = bank.Login(Command);
		System.out.println("Hello, " + loginCustomer.GetName() + "!");
		printBalance();
		
		return;
	}
	private void Deposit(String Command) {
		if (Objects.isNull(loginCustomer)) {
			System.out.println("Please Login First");
			return;
		}
		int amount = 0;
		try{
			amount = Integer.parseInt(Command);
		}
		catch (NumberFormatException ex){
			amount = 0;
			System.out.println("Please Input [amount] using Integer" );
			return;
		}
		bank.Deposit(loginCustomer, amount);
		printBalance();
		return;
	}
	
	private void Withdraw(String Command) {
		if (Objects.isNull(loginCustomer)) {
			System.out.println("Please Login First");
			return;
		}
		int amount = 0;
		try{
			amount = Integer.parseInt(Command);
		}
		catch (NumberFormatException ex){
			amount = 0;
			System.out.println("Please Input [amount] using Integer" );
			return;
		}
		String error = bank.Withdraw(loginCustomer, amount);
		if (!error.isEmpty()){
			System.out.println(error);
		}
		printBalance();	
		return;
	}
	
	private void Transfer(String Command) {
		if (Objects.isNull(loginCustomer)) {
			System.out.println("Please Login First");
			return;
		}
		String target = getFirstWord(Command);
		
		Customer targetCustomer = bank.GetCustomer(target);
		if (Objects.isNull(targetCustomer)) {
			System.out.println("Please Target Must Login First");
			return;
		}
		
		String amountString = getRestWord(Command);
		int amount = 0;
		try{
			amount = Integer.parseInt(amountString);
		}
		catch (NumberFormatException ex){
			amount = 0;
			System.out.println("Please Input [amount] using Integer" );
			return;
		}
		
		String error = bank.Transfer(loginCustomer, targetCustomer, amount);
		if (!error.isEmpty()){
			System.out.println(error);
		}
		printBalance();
		
		return;
	}
	
	private void Logout(String Command) {
		if (Objects.isNull(loginCustomer)) {
			System.out.println("Please Login First");
			return;
		}
		System.out.println("Goodbye, " + loginCustomer.GetName() + "!");
		loginCustomer = null;
		return;
	}
	
	private String getFirstWord(String Word){
		String[] result = Word.split(" ", 2);
		String first = result[0];
		return first;
	}
	
	private String getRestWord(String Word){
		int lastIndexOf = Word.lastIndexOf(" ");
		if( lastIndexOf == -1) {
			return "";
		}
		String result = Word.substring( Word.indexOf(" ") + 1);
		return result;
	}
	
	private void commandList() {
		for (int i = 0; i < 25; i++) {
			System.out.println();
		}
		System.out.println("Command List");
		System.out.println("* `login [name]` - Logs in as this customer and creates the customer if not exist");
		System.out.println("* `deposit [amount]` - Deposits this amount to the logged in customer");
		System.out.println("* `withdraw [amount]` - Withdraws this amount from the logged in customer");
		System.out.println("* `transfer [target] [amount]` - Transfers this amount from the logged in customer to the target customer");
		System.out.println("* `logout` - Logs out of the current customer");
	}
	
	private void printBalance() {
		String stringBalance = bank.PrintBalance(loginCustomer);
		System.out.println(stringBalance);
		return;
	}
}
