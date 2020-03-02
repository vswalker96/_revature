package com.revature.BankApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin extends Person {

	private int adminId;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static String query = " ";

	public Admin() {
	}

	public Admin(String firstName, String lastName, long ssn, int age, String email, int adminId) {
		super(firstName, lastName, ssn, age, email);
		this.adminId = adminId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public static void getPendingApps() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		User user = new User();

		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();

		query = "SELECT * from users where pending = 'true'";
		pstmt = connection.prepareStatement(query);
		// pstmt.setString(1, user.getUserName());
		rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("first_name") + rs.getString("last_name") + "pending account: "
					+ rs.getString("account_type") + rs.getDouble("balance"));

			System.out.println("Would you like to Approve " + rs.getString("first_name") + "?\n1. Yes\n2. No");
			int whichUser = 0;
			while (whichUser == 0) {
				try {
					whichUser = Integer.parseInt(scanner.next());
				} catch (NumberFormatException e) {
					System.err.println("Invalid format. Enter number.");
				}
			}
			if (whichUser == 1) {
				query = "update users SET pending = 'false', account_type = null, balance = null where username=?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, rs.getString("username"));
				pstmt.executeUpdate();

				Account.createAccount(rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("account_type"), rs.getDouble("balance"), rs.getString("username"));
				System.out.println("Account Approved!");
			}

		}

	}

}
