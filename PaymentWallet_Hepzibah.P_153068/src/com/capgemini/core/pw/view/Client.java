package com.capgemini.core.pw.view;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capgemini.core.pw.beans.Customer;
import com.capgemini.core.pw.beans.Wallet;
import com.capgemini.core.pw.service.WalletService;
import com.capgemini.core.pw.service.WalletServiceImpl;

public class Client 
{ private static WalletService walletService;
	public Client()
	{ 
		walletService=new WalletServiceImpl();
		
	}
	public void menu()
	{
		
		Scanner console = new Scanner(System.in);
		System.out.println("Options");
		System.out.println("1. Create Account");
		System.out.println("2. Show Balance");
		System.out.println("3. Fund Transfer");
		System.out.println("4. Deposit Amount");
		System.out.println("5. Withdraw Amount");
		System.out.println("0. Exit");
		System.out.println("Enter the Choice:");
		int choice = console.nextInt();
		
		switch (choice) 
		{
		case 1: 
			Customer customer = new Customer();
			System.out.println("Enter the Name: ");
			String name = console.next();
			
			System.out.println("Enter the Mobile Number: ");
			String number = console.next();

			System.out.println("Enter the Initial Balance Amount: ");
		    BigDecimal amount = console.nextBigDecimal();
		    
		  
		    
		    customer= walletService.createAccount(name, number, amount);
		    
		    System.out.println("Congrats Your Account has been created.");
		    
		   break;
		   
		case 2:
			
			System.out.println("Enter the Mobile Number: ");
			String number1 = console.next();
			
		 customer =	walletService.showBalance(number1);
		 
		 System.out.println("Balance = "+customer.getWallet().getBalance());
			
		break;
		
		case 3:
			
			System.out.println("Enter the Source Mobile Number: ");
			String sNumber = console.next();
			
			System.out.println("Enter the Target Mobile Number: ");
			String dNumber = console.next();
			
			System.out.println("Enter the Amount to transfer: ");
			BigDecimal amount1 = console.nextBigDecimal();
			
			customer = walletService.fundTransfer(sNumber, dNumber, amount1);
			
			 System.out.println("Balance = "+customer.getWallet().getBalance());
			
			 break;
			 
		case 4:
			System.out.println("Enter the Amount to Deposit: ");
			BigDecimal amount2 = console.nextBigDecimal();
			
			System.out.println("Enter the Mobile number: ");
			String mNumber = console.next();
			
			customer = walletService.depositAmount(mNumber, amount2);
		
		//	System.out.println("Balance = "+customer.getWallet().getBalance());
			
			break;
			
		case 5:
			System.out.println("Enter the Amount to Withdraw: ");
			BigDecimal amount3 = console.nextBigDecimal();
			
			System.out.println("Enter the Mobile number: ");
			String mNumber1 = console.next();
			
			customer = walletService.withdrawAmount(mNumber1, amount3);
		
			//System.out.println("Balance = "+customer.getWallet().getBalance());
			
			break;
			
		case 0:
			System.out.println("Exiting....");
			System.exit(0);
		default:
			System.out.println("Invalid Option");
			break;
			
		}
	}
	
	public static void main(String[] args) 
	{
		Client client = new Client();
	while(true)
		client.menu();
}
}
