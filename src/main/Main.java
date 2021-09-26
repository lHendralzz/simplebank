package main;

import java.io.File;
import java.util.ArrayList;

import ATM.ATM;

public class Main { 

	public static void Init() {
		ATM Atm = new ATM();
		Atm.Start();
	}
	
	public static void main(String[] args) {
		Init();
		System.out.println("testing");
	}
}