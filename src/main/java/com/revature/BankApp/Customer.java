package com.revature.BankApp;

import java.sql.*;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer extends User {

	static final Logger logger = (Logger) LogManager.getLogger(Customer.class);

	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static String query = " ";

	private String accountType;
	private double balance;

	public Customer(String firstName, String lastName, long ssn, int age, String email, String userName,
			String password, String userType, String accountType, double balance) {
		super(firstName, lastName, ssn, age, email, userName, password, userType);
		this.accountType = accountType;
		this.balance = balance;
		// TODO Auto-generated constructor stub
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public static void applyForAccount(User user, String accountType, double balance) throws SQLException {

		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();
		query = "UPDATE users SET pending= ?, account_type = ?, balance = ? WHERE username= ?";
		pstmt = connection.prepareStatement(query);

		pstmt.setString(1, "true");
		pstmt.setString(2, accountType);
		pstmt.setDouble(3, balance);
		pstmt.setString(4, user.getUserName());
		pstmt.execute();

		System.out.println("You have successfully applied for an account");

		User.logout();

	}

	public static void viewAllAcct(User user) throws Exception {
		Scanner scanner = new Scanner(System.in);
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();
		query = "select * from accounts where username = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, user.getUserName());
		rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name")
					+ "\nAccount Type: " + rs.getString("account_type") + "\nAccount Number: "
					+ rs.getLong("account_number") + "Balance: $" + rs.getInt("balance"));
			continue;
		}

	}

	public static void viewOneAcct(User user) throws Exception {
		Scanner scanner = new Scanner(System.in);
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();
		query = "select * from accounts where username = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, user.getUserName());
		rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println("Account Type: " + rs.getString("account_type") + "Account Number: "
					+ rs.getLong("account_number"));

			System.out.println("Would you like to view this account? " + "\n1. Yes\n2. No");
			int whichAcct = 0;
			while (whichAcct == 0) {
				try {
					whichAcct = Integer.parseInt(scanner.next());
				} catch (NumberFormatException e) {
					System.err.println("Invalid format. Enter number.");
					logger.info("Nunber Format Exception");
				}
			}
			if (whichAcct == 1) {
				System.out.println("Account Type: " + rs.getString("account_type") + "\nBalance: $"
						+ rs.getDouble("balance") + "\nAccount Number: " + rs.getString("account_number"));
			} else {
				rs.next();
			}
			break;
		}
	}

	public static void makeDeposit(User user) throws Exception {
		Scanner scanner = new Scanner(System.in);
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();
		query = "select * from accounts where username = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, user.getUserName());
		rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println("Account Type: " + rs.getString("account_type") + "Account Number: "
					+ rs.getLong("account_number") + "Balance: " + rs.getDouble("balance"));

			System.out.println("Do you want to deposit into this account? " + "\n1. Yes\n2. No");
			int whichAcct = 0;
			while (whichAcct == 0) {
				try {
					whichAcct = Integer.parseInt(scanner.next());
				} catch (NumberFormatException e) {
					System.err.println("Invalid format. Enter number.");
					logger.info("Invalid user input. Number Format Exception");
				}
			}
			if (whichAcct == 1) {
				System.out.println("How much are you depositing?");
				double depositAmt = Double.parseDouble(scanner.next());
				while (depositAmt < 0) {
					System.err.println("Invalid Deposit. Try Again");
					depositAmt = Double.parseDouble(scanner.next());
					break;
				}
				double newBalance = rs.getDouble("balance") + depositAmt;
				query = "UPDATE accounts SET balance= ? WHERE username= ? and account_number =?";
				pstmt = connection.prepareStatement(query);
				pstmt.setDouble(1, newBalance);
				pstmt.setString(2, user.getUserName());
				pstmt.setLong(3, rs.getLong("account_number"));
				pstmt.executeUpdate();

				System.out.println("Deposit accepted. Your new balance is " + newBalance);

			} else {
				rs.next();
			}
			break;

		}

	}

	public static void withdrawal(User user) throws Exception {
		Scanner scanner = new Scanner(System.in);
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();
		query = "select * from accounts where username = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, user.getUserName());
		rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println("Account Type: " + rs.getString("account_type") + "Account Number: "
					+ rs.getLong("account_number") + " Account balance: " + rs.getDouble("balance"));

			System.out.println("Do you want to withdrawl from this account? " + "\n1. Yes\n2. No");
			int whichAcct = 0;
			while (whichAcct == 0) {
				try {
					whichAcct = Integer.parseInt(scanner.next());
				} catch (NumberFormatException e) {
					System.err.println("Invalid format. Enter number.");
					logger.info("Invalid user input. Number Format Exception");
				}
			}
			if (whichAcct == 1) {
				System.out.println("How much do you want to withdrawal?");
				double withdrawalAmt = Double.parseDouble(scanner.next());
				while (withdrawalAmt < 0 || withdrawalAmt > rs.getDouble("balance")) {
					System.err.println("Insufficient Funds. Try Again");
					withdrawalAmt = Double.parseDouble(scanner.next());
					break;
				}
				double newBalance = rs.getDouble("balance") - withdrawalAmt;
				query = "UPDATE accounts SET balance= ? WHERE username= ? and account_number =?";
				pstmt = connection.prepareStatement(query);
				pstmt.setDouble(1, newBalance);
				pstmt.setString(2, user.getUserName());
				pstmt.setLong(3, rs.getLong("account_number"));
				pstmt.executeUpdate();

				System.out.println("Your new balance is " + newBalance);

			} else {
				rs.next();
			}
			break;

		}
	}

	public static void viewBalance(User user) throws Exception {

		Scanner scanner = new Scanner(System.in);
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();
		query = "select * from accounts where username = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, user.getUserName());
		rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println("Account Type: " + rs.getString("account_type") + "Account Number: "
					+ rs.getLong("account_number"));

			System.out.println("Do you want to view balance of this account? " + "\n1. Yes\n2. No");
			int whichAcct = 0;
			while (whichAcct == 0) {
				try {
					whichAcct = Integer.parseInt(scanner.next());
				} catch (NumberFormatException e) {
					System.err.println("Invalid format. Enter number.");
					logger.info("Invalid user input. Number Format Exception");
				}
			}
			if (whichAcct == 1) {

				System.out.println("Your balance is " + rs.getDouble("balance"));

			}
			
		}

	}

	public static void Transfer(User user) throws Exception {
		Scanner scanner = new Scanner(System.in);
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();

		System.out.println("Which account would you like to send money from?");
		query = "select * from accounts where username = ?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, user.getUserName());
		rs = pstmt.executeQuery();

		while (rs.next()) {

			System.out.println("Account: " + rs.getLong("account_number") + "\nBalance: $" + rs.getDouble("balance")
					+ "\nWould you like to send it from this account?\n1.Yes\n2.No");
			int choice = 0;
			while (choice == 0) {
				try {
					choice = Integer.parseInt(scanner.next());
				} catch (NumberFormatException e) {
					System.err.println("Invalid format. Enter number.");
					logger.info("Invalid user input. Number Format Exception");
				}
			}

			if (choice == 1) {
				long currentUserAcctNum = rs.getLong("account_number");
				double currentUserAcctBal = rs.getDouble("balance");
				System.out.println("How much would you like to send?");
				
				double amount = Double.parseDouble(scanner.next());
				
				System.out.println("Who would you like to send money to? Please enter username:");
				String receivingUser = scanner.next();

				query = "select * from accounts where username = ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, receivingUser);
				rs = pstmt.executeQuery();
				while (rs.next()) {

					if (rs.getString("username").equals(receivingUser)) {
						System.out.println("Send to this account?\n" + rs.getLong("account_number") + "\n1.Yes\n2.No");
						int thisChoice = 0;
						while (thisChoice == 0) {
							try {
								thisChoice = Integer.parseInt(scanner.next());
							} catch (NumberFormatException e) {
								System.err.println("Invalid format. Enter number.");
								logger.info("Invalid user input. Number Format Exception");
							}
						}
						if (thisChoice == 1) {
							long receiverAccountNum = rs.getLong("account_number");
							query = "select balance from accounts where account_number = ?";
							pstmt = connection.prepareStatement(query);
							pstmt.setLong(1, rs.getLong("account_number"));
							rs = pstmt.executeQuery();
							while (rs.next()) {
								double receiverCurrentBal = rs.getDouble("balance");
								double receiverNewBal = receiverCurrentBal + amount;

								query = "update accounts set balance =? where account_number =?";
								pstmt = connection.prepareStatement(query);
								pstmt.setDouble(1, receiverNewBal);
								pstmt.setLong(2, receiverAccountNum);
								pstmt.executeUpdate();

								System.out.println("Money Sent Successfully");

								double senderNewBal = currentUserAcctBal - amount;
								query = "update accounts set balance =? where account_number =?";
								pstmt = connection.prepareStatement(query);
								pstmt.setDouble(1, senderNewBal);
								pstmt.setLong(2, currentUserAcctNum);
								pstmt.executeUpdate();

							}
						}

					} else {
						System.out.println("User does not exist");
						logger.info("User doesnt Exist in the DB");

					}

				}

			}
		}
	}
}
