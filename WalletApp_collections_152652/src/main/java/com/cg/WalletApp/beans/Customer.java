package com.cg.WalletApp.beans;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class Customer {
	private String mobileNumber;
	private String name;
	private String password;
	private String EmailId;
	private Wallet wallet ;
	private  TreeMap<LocalDateTime, String> transactions ;
	public Customer() {
		wallet = new Wallet();
		transactions = new TreeMap<LocalDateTime, String>();
	}
	



	public Customer(String mobileNumber, String name, String password, String emailId, Wallet wallet,
			TreeMap<LocalDateTime, String> transactions) {
		super();
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.password = password;
		EmailId = emailId;
		this.wallet = wallet;
		this.transactions = transactions;
	}




	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getEmailId() {
		return EmailId;
	}

	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	public TreeMap<LocalDateTime, String> getTransactions() {
		return transactions;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	
	



}
