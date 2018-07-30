package com.cg.WalletApp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.WalletApp.beans.Customer;
import com.cg.WalletApp.exception.WalletException;
import com.cg.WalletApp.exception.IWalletException;
import com.cg.WalletApp.repo.IWalletRepo;
import com.cg.WalletApp.repo.WalletRepoImpl;

public class WalletServiceImpl implements IWalletService {
	static IWalletRepo iWalletDao = new WalletRepoImpl();
	public static TreeMap<String, Customer> customerDetails = null;
	static {
		customerDetails = iWalletDao.getDetails();

	}

	public void checkName(String name) throws WalletException {
		Pattern pattern = Pattern.compile("[a-zA-Z]{1,}");
		Matcher matcher = pattern.matcher(name);
		if (!(matcher.matches())) {
			throw new WalletException(IWalletException.nameException);
		}
	}

	public void checkMobileNumber(String mobileNumber) throws WalletException {

		Pattern pattern = Pattern.compile("[7-9]{1}[0-9]{9}");
		Matcher matcher = pattern.matcher(mobileNumber);
		if (!(matcher.matches())) {
			throw new WalletException(IWalletException.mobileNumberException);
		}
	}

	public void checkPassword(String password) throws WalletException {
		Pattern pattern = Pattern.compile(".*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*" + "");
		Matcher matcher = pattern.matcher(password);
		if (!(matcher.matches())) {
			throw new WalletException(IWalletException.passwordException);
		}

	}

	public void checkEmail(String emailId) throws WalletException {
		Pattern pattern = Pattern.compile("[a-z]{1}[a-z0-9._]{1,}@[a-zA-Z0-9]{1,}.com");
		Matcher matcher = pattern.matcher(emailId);
		if (!(matcher.matches())) {
			throw new WalletException(IWalletException.emailIdException);
		}

	}

	public String addCustomer(Customer customer) throws WalletException {
		String result = null;
		if (!customerDetails.containsKey(customer.getMobileNumber())) {
			result= iWalletDao.addCustomer(customer);
		}
		else
			throw new WalletException(IWalletException.ACCOUNTEXISTS);
     return result;
	}

	public Customer showBalance(String mobileNum, String password) throws WalletException {
		Customer result = null;

		if (customerDetails.containsKey(mobileNum)) {

			if (customerDetails.get(mobileNum).getPassword().equals(password)) {
				result = customerDetails.get(mobileNum);
			}

		}
		else
			throw new WalletException(IWalletException.invalidDetails);
		
		return result;
	}

	public Customer check(String mobileNum, String password) throws WalletException {
		Customer result = null;
		if (customerDetails.containsKey(mobileNum)) {
			if (customerDetails.get(mobileNum).getPassword().equals(password)) {
				result = customerDetails.get(mobileNum);
			}
		}
		else
			throw new WalletException(IWalletException.invalidDetails);
		return result;
	}

	public void deposit(Customer customer, BigDecimal amount) {

		customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount));
		customer.getTransactions().put(LocalDateTime.now(), "Credited " + amount);

	}

	public boolean withDraw(Customer customer, BigDecimal amount) throws WalletException {
		boolean result = false;
		if (customer.getWallet().getBalance().subtract(amount).compareTo(BigDecimal.valueOf(0.0)) >= 0) {
			customer.getWallet().setBalance(customer.getWallet().getBalance().subtract(amount));
			result = true;
			customer.getTransactions().put(LocalDateTime.now(), "Debited " + amount);
		}
		else
			throw new WalletException(IWalletException.insufficientFunds);
		return result;
	}

	public boolean isFound(String receiverMobile) throws WalletException {
		boolean result = false;
		if (customerDetails.containsKey(receiverMobile)) {
			result = true;

		}
		else
			throw new WalletException(IWalletException.mobileNumberException);
		return result;
	}

	public boolean transfer(String senderMobile, String receiverMobile, BigDecimal amount) throws  WalletException, InterruptedException {
		boolean result = false;
		if (customerDetails.get(senderMobile).getWallet().getBalance().subtract(amount)
				.compareTo(BigDecimal.valueOf(0.0)) >= 0) {
			Customer sender = customerDetails.get(senderMobile);
			sender.getWallet().setBalance(sender.getWallet().getBalance().subtract(amount));
			sender.getTransactions().put(LocalDateTime.now(), "Debited " + amount + " to " + receiverMobile);
			Customer receiver = customerDetails.get(receiverMobile);
			Thread.sleep(100);
			receiver.getWallet().setBalance(receiver.getWallet().getBalance().add(amount));
			receiver.getTransactions().put(LocalDateTime.now(), "Credited " + amount + " from " + senderMobile);
			result = true;
		}
		else
			throw new WalletException(IWalletException.insufficientFunds);
		return result;
	}
	public boolean checkEnteredAmount(BigDecimal amount) throws WalletException {
		boolean result = false;
		Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]+");
		Matcher matcher = pattern.matcher(amount.toString());
		if(matcher.matches())
			result = true;
		else
			throw new WalletException(IWalletException.invalidAmount);
		return result;
	}
}
