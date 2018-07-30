package com.cg.WalletApp.exception;

public interface IWalletException {

	String nameException ="Please Enter a Valid Name!";
	String mobileNumberException = "Please Enter Valid Mobile Number!";
	String passwordException ="Please Enter a Valid Password";
	String emailIdException = "Please Enter valid email id";
	String invalidDetails = "Given details are invalid.";
	String insufficientFunds = "Insufficicnet Account Balance";
	String ACCOUNTEXISTS = "The Account is already existing with the given mobile number!";
	String inputMismatch = "Please Enter Valid Details";
	String invalidAmount = "Please Enter valid amount";
}
