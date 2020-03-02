package com.revature.BankApp;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

public class User extends Person implements Serializable {

	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static String query = " ";
	static User myUser = new User();

	public String Password;
	private String userName;
	private String password;
	private String userType;
	// private Account setAcctType;
	// private Account balance;

	public User() {
	}

	public User(String firstName, String lastName, long ssn, int age, String email, String userName, String password,
			String userType) {
		super(firstName, lastName, ssn, age, email);
		this.userName = userName;
		this.password = password;
		this.userType = userType;

		// this.setAcctType = accountType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	static public User login(String userName, String password) throws SQLException {
		// System.out.println(userName + " "+ password);
		// User myUser = new User();
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();

		query = "select * from users where username =? and passwordhash=?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, userName);
		pstmt.setString(2, password);
		rs = pstmt.executeQuery();

		while (rs.next()) {

			if (userName.contains(rs.getString("username"))) {

				if (rs.getString("passwordhash").equals(password)) {

					myUser.setFirstName(rs.getString("first_name"));
					myUser.setLastName(rs.getString("last_name"));
					myUser.setAge(rs.getInt("age"));
					myUser.setEmail(rs.getString("email"));
					myUser.setUserType(rs.getString("usertype"));
					myUser.setUserName(userName);

					System.out.println("Login Successful");
					myUser.getFirstName();

					return myUser;
				}
			}

		}
		return myUser;

	}

	static public User logout() {

		myUser = null;

		return myUser;

	}

	public void createUser(String userName, User user) throws SQLException {
		DatabaseConnection conn = DatabaseConnection.getInstance();
		Connection connection = conn.getConnection();
		query = "insert into users  (first_name, last_name, ssn, email, username, passwordhash, usertype, age) values (?,?,?,?,?,?,?,?)";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, user.getFirstName());
		pstmt.setString(2, user.getLastName());
		pstmt.setLong(3, user.getSsn());
		pstmt.setString(4, user.getEmail());
		pstmt.setString(5, user.getUserName());
		pstmt.setString(6, user.getPassword());
		pstmt.setString(7, user.getUserType());
		pstmt.setInt(8, user.getAge());
		pstmt.executeUpdate();
		System.out.println("User Created");
	}

	@Override
	public String toString() {
		return "User [" + (userName != null ? "userName=" + userName + ", " : "")
				+ (userType != null ? "userType=" + userType + ", " : "")
				+ (getFirstName() != null ? "getFirstName()=" + getFirstName() + ", " : "")
				+ (getLastName() != null ? "getLastName()=" + getLastName() + ", " : "") + "getAge()=" + getAge() + ", "
				+ (getEmail() != null ? "getEmail()=" + getEmail() : "") + "]";
	}

	// public String updateUser() {}

	// public String deleteUser() {}

}
