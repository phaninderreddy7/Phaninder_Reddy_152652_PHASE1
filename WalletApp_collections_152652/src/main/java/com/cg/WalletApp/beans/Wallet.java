package com.cg.WalletApp.beans;

import java.math.BigDecimal;

public class Wallet{

	private BigDecimal balance;
	
	public Wallet() {
		this.balance=BigDecimal.valueOf(0.0);
	}

	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
