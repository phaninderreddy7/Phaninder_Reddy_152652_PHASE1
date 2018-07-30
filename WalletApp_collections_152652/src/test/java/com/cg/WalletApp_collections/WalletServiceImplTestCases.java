package com.cg.WalletApp_collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import com.cg.WalletApp.exception.WalletException;
import com.cg.WalletApp.beans.Customer;
import com.cg.WalletApp.beans.Wallet;
import com.cg.WalletApp.service.IWalletService;
import com.cg.WalletApp.service.WalletServiceImpl;

public class WalletServiceImplTestCases {
	public static IWalletService iWalletService=null;
	Customer customer1,customer2,customer3,customer4,customer5,customer6,customer7;
	
	@Before
	public void initData() {
		iWalletService=new WalletServiceImpl();
		customer1= new Customer("9394466988","Phani","phani@321","balusanivineeth8@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer2 = new Customer("9491633891","rohith","rohith@123","pulivarun125@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer3 = new Customer("9897654321","Virat","virat@123","vinay_gr@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer4= new Customer("8899447722","kohli","kohli@123","johndoe12@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer5= new Customer("8885475621","james","jam#james@123","doe_doe.doe@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer6= new Customer("9876542130","Sherlock","Sherlock@123","sherlock8@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer7= new Customer("9012345678","Dhoni","Dhoni@123","watson12@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
	}
	
	@Test(expected = WalletException.class)
	public void checkMobileNumberError() throws WalletException
	{
		iWalletService.checkMobileNumber("878754");
	}
	@Test
	public void checkMobileNumberTrue() throws WalletException
	{
		iWalletService.checkMobileNumber("9394466988");
	}
	
	@Test(expected = WalletException.class)
	public void checkNameTestError() throws WalletException
	{
		iWalletService.checkName("");
	}
	
	@Test
	public void checkNameTest() throws WalletException
	{
		iWalletService.checkName("Phani");
	}
	
	@Test(expected = WalletException.class)
	public void passwordLengthError() throws WalletException
	{
		iWalletService.checkPassword("rohith");
	}
	
	@Test(expected = WalletException.class)
	public void passwordSpecialCharacterError() throws WalletException
	{
		iWalletService.checkPassword("varun1234");
	}
	
	@Test(expected = WalletException.class)
	public void passwordCapitalLetterError() throws WalletException
	{
		iWalletService.checkPassword("phani@321");
	}
	@Test(expected = WalletException.class)
	public void emailTestFailDueToSymbol() throws WalletException
	{
		iWalletService.checkEmail("johndoe123gmail.com");
	}
	
	@Test(expected = WalletException.class)
	public void emailTestFailDueTodotcom() throws WalletException
	{
		iWalletService.checkEmail("johndoe123@gmail");
	}
	@Test(expected = WalletException.class)
	public void emailTestFailDueToMissingDomain() throws WalletException
	{
		iWalletService.checkEmail("johndoe123@.com");
	}
	
	@Test(expected = WalletException.class)
	public void emailTestFailDueToMissingId() throws WalletException {
		iWalletService.checkEmail("@gmail.com");
	}
	
	
	@Test
	public void emailTestForValidEmailId() throws WalletException {
		iWalletService.checkEmail("john.doe_12@xyzmail123.com");
	}
	
	
	@Test
	public void addCustomerTestTrue() throws WalletException
	{
		assertEquals("9394466988",iWalletService.addCustomer(customer1));
			
	}
	
	@Test
	public void checkForExistingCustomer() throws WalletException
	{
		assertNotNull(iWalletService.check("9394466988", "phani@321"));
	}
    
	@Test(expected = WalletException.class)
	public void checkForNonExistingCustomer() throws WalletException
	{
		assertNull(iWalletService.check("9586234523", "John@1John"));
	}
	
    
    @Test
    public void depositTestTrue()
    {
    	iWalletService.deposit(customer1, BigDecimal.valueOf(5000));
    }
    
    
    @Test(expected = WalletException.class)
    public void withdrawTestException() throws WalletException
    {
    	iWalletService.addCustomer(customer3);
    	iWalletService.withDraw(customer3, BigDecimal.valueOf(5000));
  
    }
	@Test
	public void checkForReceiverExists() throws WalletException
	{
		assertTrue(iWalletService.isFound("9394466988"));
	}
	
	@Test(expected = WalletException.class)
	public void checkForReceiverNotExists() throws WalletException
	{
		assertTrue(iWalletService.isFound("9494232097"));
	}
   
    
    @Test
    public void amountValidationTestForInteger() throws WalletException {
    	assertTrue(iWalletService.checkEnteredAmount(BigDecimal.valueOf(5000)));
    }
    
    
    
	
}
