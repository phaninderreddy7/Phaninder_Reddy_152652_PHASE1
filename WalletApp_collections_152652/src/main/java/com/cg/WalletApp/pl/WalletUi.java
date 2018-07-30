package com.cg.WalletApp.pl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.cg.WalletApp.beans.Customer;
import com.cg.WalletApp.exception.WalletException;
import com.cg.WalletApp.exception.IWalletException;
import com.cg.WalletApp.service.IWalletService;
import com.cg.WalletApp.service.WalletServiceImpl;

public class WalletUi {
	static IWalletService iWalletService = new WalletServiceImpl();
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		int choice;
		try {
			do {
				chooseOperation();
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					addCustomer();

					break;
				case 2:

					showBalance();
					break;
				case 3:
					depositAmount();
					break;
				case 4:
					withdrawAmount();
					break;
				case 5:
					fundTransfer();
					break;
				case 6:
					printTransactions();

					break;
				case 7:
					scanner.close();
					System.exit(0);
					break;
				default:
					System.out.println("Please Enter your choice correctly");
					break;

				}
			} while (choice != 7);
		} catch (InputMismatchException exception) {
			try {
				throw new WalletException(IWalletException.inputMismatch);
			} catch (WalletException e) {
				System.out.println(e.getMessage());
				scanner.next();
			}
		}
	}

	private static void chooseOperation() {
		System.out.println("<----------Welcome------->");
		System.out.println("1. Create an Account");
		System.out.println("2. Show the balance");
		System.out.println("3. Deposit the amount");
		System.out.println("4. Withdraw amount");
		System.out.println("5. Fund transfer");
		System.out.println("6. Print your transactions");
		System.out.println("7. Exit the application");
		System.out.println("<--------------------------->");
		System.out.println("Enter your choice");

	}

	private static void addCustomer() {
		try {
			Customer customer = new Customer();
			System.out.println("Enter the Customer Name");
			String name = scanner.nextLine();
			name += scanner.nextLine();
			iWalletService.checkName(name);
			customer.setName(name);
			System.out.println("Enter Customer's Mobile Number");
			String mobileNumber = scanner.next();
			iWalletService.checkMobileNumber(mobileNumber);
			customer.setMobileNumber(mobileNumber);
			System.out.println("Set your Password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			customer.setPassword(password);
			System.out.println("Enter Email Id");
			String emailId = scanner.next();
			iWalletService.checkEmail(emailId);
			customer.setEmailId(emailId);
			String result = iWalletService.addCustomer(customer);
			if (result != null)
				System.out.println("Account created with Account Number " + result);
		} catch (WalletException bankException) {
			System.out.println(bankException.getMessage());
		}
	}

	private static void showBalance() {
		try {
			System.out.println("Enter your mobile number");
			String mobileNum = scanner.next();
			iWalletService.checkMobileNumber(mobileNum);
			System.out.println("Enter the password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			Customer customer = iWalletService.showBalance(mobileNum, password);
			if (customer != null) {
				System.out.println("Available balance is " + customer.getWallet().getBalance());
			}
		} catch (WalletException bankException) {

			System.out.println(bankException.getMessage());
		}
	}

	private static void depositAmount() {
		try {
			System.out.println("Enter your mobile number");
			String mobileNum = scanner.next();
			iWalletService.checkMobileNumber(mobileNum);
			System.out.println("Enter the password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			Customer customer = iWalletService.check(mobileNum, password);
			if (customer != null) {
				System.out.println("Enter amount to deposit ");
				BigDecimal amount = scanner.nextBigDecimal();
				if (iWalletService.checkEnteredAmount(amount)) {
					iWalletService.deposit(customer, amount);
					System.out.println("Amount Deposited");
				}
			}
		} catch (WalletException bankException) {

			System.out.println(bankException.getMessage());
		}

	}

	private static void withdrawAmount() {
		try {
			System.out.println("Enter your mobile number");
			String mobileNum = scanner.next();
			iWalletService.checkMobileNumber(mobileNum);
			System.out.println("Enter the password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			Customer customer = iWalletService.check(mobileNum, password);
			if (customer != null) {
				System.out.println("Enter the amount to withdraw");
				BigDecimal amount = scanner.nextBigDecimal();
				if (iWalletService.checkEnteredAmount(amount)) {
					boolean result = iWalletService.withDraw(customer, amount);
					if (result == true) {
						System.out.println("Amount is succesfully withdrawn and your current balance is "
								+ customer.getWallet().getBalance());

					}
				}
			}
		} catch (WalletException bankException) {
			System.out.println(bankException.getMessage());
		}

	}

	private static void printTransactions() {
		try {
			System.out.println("Enter your mobile number");
			String mobileNum = scanner.next();
			iWalletService.checkMobileNumber(mobileNum);
			System.out.println("Enter the password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			Customer customer = iWalletService.check(mobileNum, password);
			if (customer != null) {
				TreeMap<LocalDateTime, String> transactions = customer.getTransactions();
				for (Map.Entry<LocalDateTime, String> trans : transactions.entrySet()) {
					System.out.println(trans.getKey() + " @ " + trans.getValue());
				}
			}

		} catch (WalletException bankException) {
			System.out.println(bankException.getMessage());
		}

	}

	private static void fundTransfer() {
		try {
			System.out.println("Enter Receivers mobile number ");
			String receiverMobile = scanner.next();
			iWalletService.checkMobileNumber(receiverMobile);
			boolean result = iWalletService.isFound(receiverMobile);
			if (result) {
				System.out.println("Enter sender mobile number");
				String senderMobile = scanner.next();
				iWalletService.checkMobileNumber(senderMobile);
				System.out.println("Enter senders password");
				String password = scanner.next();
				iWalletService.checkPassword(password);
				Customer customer = iWalletService.check(senderMobile, password);
				if (customer != null) {
					System.out.println("Enter the amount to transfer");
					BigDecimal amount = scanner.nextBigDecimal();
					if (iWalletService.checkEnteredAmount(amount)) {
						boolean output = iWalletService.transfer(senderMobile, receiverMobile, amount);
						if (output == true) {
							System.out.println("Amount is succesfully transferred to " + receiverMobile
									+ " and current balance is " + customer.getWallet().getBalance());
						}

					}
				}
			}

		} catch (WalletException bankException) {
			System.out.println(bankException.getMessage());
		} catch (InterruptedException interruptedException) {
			System.out.println(interruptedException.getMessage());
		}
	}

}
