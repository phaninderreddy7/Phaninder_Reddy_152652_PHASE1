package com.cg.WalletApp.service;

import java.math.BigDecimal;

import com.cg.WalletApp.beans.Customer;
import com.cg.WalletApp.exception.WalletException;

public interface IWalletService {


	void checkName(String name) throws WalletException;

	void checkMobileNumber(String mobileNumber) throws WalletException;

	void checkPassword(String password) throws WalletException;

	void checkEmail(String emailId) throws WalletException;

	String addCustomer(Customer customer) throws WalletException;

	Customer showBalance(String mobileNum, String password) throws WalletException;

	Customer check(String mobileNum, String password) throws WalletException;

	void deposit(Customer customer, BigDecimal amount);

	boolean withDraw(Customer customer, BigDecimal amount) throws WalletException;

	boolean isFound(String receiverMobile) throws WalletException;

	boolean transfer(String senderMobile, String receiverMobile, BigDecimal amount) throws InterruptedException, WalletException;

	boolean checkEnteredAmount(BigDecimal amount) throws WalletException;

}
