package com.cg.WalletApp.repo;

import java.util.TreeMap;

import com.cg.WalletApp.beans.Customer;

public class WalletRepoImpl implements IWalletRepo{
public static TreeMap<String,Customer> customerDetails=null; 
static{
	customerDetails=new TreeMap<String, Customer>();
	
}
public TreeMap<String, Customer> getDetails() {
	
	return customerDetails;
}
public String addCustomer(Customer customer) {
	customerDetails.put(customer.getMobileNumber(), customer);
	return customer.getMobileNumber();
}
}
