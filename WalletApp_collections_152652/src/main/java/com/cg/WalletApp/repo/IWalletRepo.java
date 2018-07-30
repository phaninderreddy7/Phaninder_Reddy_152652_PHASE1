package com.cg.WalletApp.repo;

import java.util.TreeMap;

import com.cg.WalletApp.beans.Customer;

public interface IWalletRepo {

	TreeMap<String, Customer> getDetails();

	String addCustomer(Customer customer);

}
