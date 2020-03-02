package com.revature.BankApp;

import java.sql.*;
import java.util.Scanner;

public class Account extends User {

	private double balance;
	private static long accountNumber;
	private String accountType;
	private String accountName;
	private double withdraw;
	private double deposit;

	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static String query = " ";

	// optional . default "Checking" or "Saving" if left empty
	// private String arrayOfTransactions;
	// map of accounts.

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(String firstName, String lastName, long ssn, int age, String email, String userName, String password,
			String userType) {
		super(firstName, lastName, ssn, age, email, userName, password, userType);
		// TODO Auto-generated constructor stub
	}

	public double getdeposit() {
		return deposit;
	}

	public void setdeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(double withdraw) {
		this.withdraw = withdraw;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public static long getAccountNumber() {
		return accountNumber;
	}

	public static void setAccountNumber(long accountNumber) {
		Account.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public static void createAccount(String firstName, String lastName, String accountType, double balance,
			String username) throws SQLException {

		long accountNumber = (long) (Math.random() * Math.pow(10, 10));

		// take in info for account DB and save it in the database.
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();
		query = "insert into accounts (first_name, last_name, account_number, balance, account_type, username) values (?,?,?,?,?,?)";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, firstName);
		pstmt.setString(2, lastName);
		pstmt.setLong(3, accountNumber);
		pstmt.setDouble(4, balance);
		pstmt.setString(5, "checkings");
		pstmt.setString(6, username);

		pstmt.execute();

		System.out.println("Account created");

	}

	public static void viewAccount() throws SQLException {

		Scanner scanner = new Scanner(System.in);
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();

		query = "select distinct username from accounts";
		pstmt = connection.prepareStatement(query);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println(rs.getString("username"));
		}
		System.out.println("Who's account would you like to view?");
		String username = scanner.next();

		query = "select * from accounts where username = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, username);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println("Name: " + rs.getString("first_name") + rs.getString("last_name") + "\nAccount Type: "
					+ rs.getString("account_type") + "\nAccount Number: " + rs.getLong("account_number")
					+ "Account Balance: " + rs.getInt("balance"));

		}

	}

	public static void viewTranscations() throws Exception {

		Scanner scanner = new Scanner(System.in);
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();

		System.out.println("Who's account would you like to view?");
		query = "select distinct username from accounts";
		pstmt = connection.prepareStatement(query);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("username"));
		}
		String username = scanner.next();
		query = "select * from accounts where username = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, username);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println("View transactions from this account? " + rs.getLong("account_number") + " "
					+ rs.getString("account_type") + "\n1.Yes\n2.No");
			long accountNum = rs.getLong("account_number");
			int choice = Integer.parseInt(scanner.next());
			if (choice == 1) {
				query = "select * from transactions where account_number = ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setLong(1, accountNum);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					System.out.println("Transaction ID: " +  rs.getInt("transaction_id") + " Name: " + rs.getString("transaction_name")+" Account Number: "+ rs.getLong("account_number") + " Amount: "+rs.getInt("balance") +  " Date : "+rs.getDate("transaction_date"));

				}
			}
			break;
		}

	}
}
