package com.capgemini.core.pw.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.core.pw.beans.Customer;
import com.capgemini.core.pw.beans.Wallet;
import com.capgemini.core.pw.exception.InsufficientBalanceException;
import com.capgemini.core.pw.exception.InvalidInputException;
import com.capgemini.core.pw.service.WalletService;
import com.capgemini.core.pw.service.WalletServiceImpl;

 public class TestClass
  {
	 static WalletService service;
	
	@Before
	public void initData()
	{
		 Map<String,Customer> data= new HashMap<String, Customer>();
		 Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
		 Customer cust2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
		 Customer cust3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
				
		 data.put("9900112212", cust1);
		 data.put("9963242422", cust2);	
		 data.put("9922950519", cust3);	
			service= new WalletServiceImpl(data);
			
	}
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		service=new WalletServiceImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		service=null;
	}

	@Before
	public void setUp() throws Exception 
	{
		System.out.println("Before Testing.");
	}

	@After
	public void tearDown() throws Exception
	{
		System.out.println("After Testing.");
	}
	
	
	
	@Test(expected=NullPointerException.class)
	public void testCreateAccount1() 
	{
		
		service.createAccount(null,null,null);
		
	}

	
	@Test(expected=NullPointerException.class)
	public void testCreateAccount2() 
	{
		
	service.createAccount(null,"9900112212",new BigDecimal(9000));
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount3() 
	{
		
	service.createAccount("","9900112212",new BigDecimal(9000));
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount4() 
	{
		
	service.createAccount("","",new BigDecimal(9000));
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount5() 
	{
		
	service.createAccount("Eric","9898989898",new BigDecimal(0));
	}

	@Test	
	public void testCreateAccount6() 
	{			
			Customer cust=new Customer();
			cust=service.createAccount("Eric","9898989898",new BigDecimal(6000));
			assertEquals("Eric", cust.getName());
		
	}
	
	
	@Test
	public void testCreateAccount7()
	{
		Customer cust=new Customer();
		cust=service.createAccount("Eric","9898989898",new BigDecimal(7000));
		assertEquals("9898989898", cust.getMobileNo());

	}
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance8() 
	{
		Customer cust=new Customer();
	cust=service.showBalance("9898989898");

	}
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance9() 
	{
		service.showBalance(null);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance10() 
	{
		service.showBalance("9898989898");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance11()
	{
		service.showBalance("");
	}
	
	@Test
	public void testShowBalance12()
	{
	Customer cust=new Customer();
	cust=service.showBalance("9900112212");
	BigDecimal actual=cust.getWallet().getBalance();
	BigDecimal expected=new BigDecimal(9000);
	assertEquals(expected, actual);
    }
	
	@Test(expected=NullPointerException.class)
	public void testFundTransfer13() 
	{
		service.fundTransfer(null, null,new BigDecimal(6000));
	}

	@Test
	public void testFundTransfer14() 
	{
		Customer cust1 = service.fundTransfer("9900112212","9963242422",new BigDecimal(2000));
		BigDecimal actual=cust1.getWallet().getBalance();
		BigDecimal expected=new BigDecimal(7000);
		assertEquals(expected, actual);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer15() 
	{
		service.fundTransfer("", "", new BigDecimal(6000));
	}

	@Test(expected=InvalidInputException.class)
	public void testFundTransfer16() 
	{
		service.fundTransfer("", "", new BigDecimal(0));
	}
	
	
@Test(expected=InvalidInputException.class)
public void testDeposit17()
{
	service.depositAmount("9898989898", new BigDecimal(2000));
}
	
@Test
public void testDeposit18()
{
	Customer cust1 = service.depositAmount("9900112212", new BigDecimal(2000));
	BigDecimal actual=cust1.getWallet().getBalance();
	BigDecimal expected=new BigDecimal(11000);
	assertEquals(expected, actual);
}

@Test(expected=InvalidInputException.class)
public void testWithdraw19()
{
	service.withdrawAmount("9600045352", new BigDecimal(2000));
}
	
@Test
public void testWithdraw20()
{
	Customer cust1 = service.withdrawAmount("9900112212", new BigDecimal(2000));
	BigDecimal actual=cust1.getWallet().getBalance();
	BigDecimal expected=new BigDecimal(7000);
	assertEquals(expected, actual);
}	

@Test(expected=NullPointerException.class)
public void testCreateAccount21() 
{
	
service.createAccount("Amit",null,new BigDecimal(9000));
}


@Test(expected=InvalidInputException.class)
public void testCreateAccount22() 
{
	
service.createAccount("Amit","9900112212",new BigDecimal(-9000));
}

@Test(expected=InvalidInputException.class)
public void testFundTransfer23() 
{
	service.fundTransfer("Yogini", "9922950519",new BigDecimal(-600));
}

@Test(expected=InvalidInputException.class)
public void testDeposit24()
{
	service.depositAmount("9922950519", new BigDecimal(-200));
}

@Test(expected=InvalidInputException.class)
public void testWithdraw25()
{
	Customer cust1 = service.withdrawAmount("9900112212", new BigDecimal(-2000));
	BigDecimal actual=cust1.getWallet().getBalance();
	BigDecimal expected=new BigDecimal(-7000);
	assertEquals(expected, actual);
}

}