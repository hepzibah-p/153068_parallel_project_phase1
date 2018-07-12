package com.capgemini.core.pw.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.capgemini.core.pw.beans.Customer;
import com.capgemini.core.pw.beans.Wallet;
import com.capgemini.core.pw.exception.InsufficientBalanceException;
import com.capgemini.core.pw.exception.InvalidInputException;
import com.capgemini.core.pw.model.WalletRepo;
import com.capgemini.core.pw.model.WalletRepoImpl;




public class WalletServiceImpl implements WalletService{
private WalletRepo repo;
Customer customer = new Customer();
	
	public WalletServiceImpl(Map<String, Customer> data)
	{
		repo= new WalletRepoImpl(data);
	}
	
	public WalletServiceImpl(WalletRepo repo) 
	{
		super();
		this.repo = repo;
	}

	public WalletServiceImpl()
	{
		repo= new WalletRepoImpl();
	}

//SHOW BALANCE

	public Customer showBalance(String mobileNo)
	{
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile number");
	}
	
	
//CREATE ACCOUNT 
	
	@Override
	public Customer createAccount(String name, String mobileno, BigDecimal amount) 
	{		
		Customer customer = new Customer(name, mobileno, new Wallet(amount));
		
		if(isBalanceInvalid(amount)==true)
		{
			throw new InvalidInputException("Balance is less.");
			
		}
		
		if( isPhoneNumberInvalid( mobileno ) == true)
		{	
			throw new InvalidInputException("Invalid Mobile number");
		
		}
		
		if (isNameInvalid( name )==true)
		{	
			throw new InvalidInputException("Invalid Name");
		
		}
			
		else
		{
			if(repo.save(customer)==false) 
			throw new InvalidInputException("");	
	
		}
			
		
		
		return customer;
	}
	

	//FUND TRANSFER

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InsufficientBalanceException
	{
	
		Customer customer = null;
		Customer customer1 = null;
		
		customer = repo.findOne(sourceMobileNo);
		customer1 = repo.findOne(targetMobileNo);
		
		if( isPhoneNumberInvalid( sourceMobileNo ) || isPhoneNumberInvalid( targetMobileNo )== true)
		{	
			
			throw new InvalidInputException("Invalid Mobile number");
		
		}
		
		if(isBalanceInvalid(amount)==true)
		{
			throw new InvalidInputException("Balance is less.");
			
		}
		
		if(amount.compareTo(customer.getWallet().getBalance())<=0)
		{
		
			customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount.negate()));
			
			
		
		if(repo.save(customer)==false)
			{
				throw new InvalidInputException("");
			}
		customer1.getWallet().setBalance(customer1.getWallet().getBalance().add(amount));
		
		if(repo.save(customer1)==false)
			{
				throw new InvalidInputException("Invalid Mobile Number");
			}
		}
		else
		{
			throw new InsufficientBalanceException("Insufficient Balance");
		}
		
		return customer;
	}
	
	
	//DEPOSIT
	
	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount)
	
	{
		Customer customer= repo.findOne(mobileNo);
		
		if( isPhoneNumberInvalid( mobileNo ) == true)
		{	
			throw new InvalidInputException("Invalid Mobile number");
		
		}
		
		if(isBalanceInvalid(amount)==true)
		{
			throw new InvalidInputException("Balance is less.");
			
		}
		
		if(customer!=null)
		customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount));

		if(repo.save(customer)==false)
		{
			throw new InvalidInputException("Invalid Mobile Number");
		}
		return customer;
	}
	
	
	//WITHDRAW
	
	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) 
	{
		if( isPhoneNumberInvalid( mobileNo ) == true)
		{	
			throw new InvalidInputException("Invalid Mobile number");
		
		}
		
		if(isBalanceInvalid(amount)==true)
		{
			throw new InvalidInputException("Balance is less.");
			
		}
		
		
		Customer customer= repo.findOne(mobileNo);
		
		if(customer!=null)
		{
			if(amount.compareTo(customer.getWallet().getBalance())<0)
			{
		customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount.negate()));
			}
			
			else
			{
				System.out.println("The amount entered is greater than the available balance.");
				
			}
		}
		
		if(repo.save(customer)==false)
			throw new InvalidInputException("");
		
		return customer;
		
	}
	
	//VALIDATION
	
	public static boolean isPhoneNumberInvalid( String phoneNumber )
	{
		
		if(phoneNumber.matches("[1-9][0-9]{9}")) 
		{
			return false;
		}		
		else 
			return true;
	}
	
	public static boolean isNameInvalid( String name )
	{
		
		if(name.matches("[a-zA-Z][a-zA-Z]*")) 
		{
			return false;
		}		
		else 
			return true;
	}
	
	public static boolean isBalanceInvalid( BigDecimal balance )
	{
		
		if(balance.compareTo(new BigDecimal(0))>0) 
		{
			return false;
		}		
		else 
			return true;
	}

}
