package com.revature.BankApp;

import java.util.Scanner;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.Logger;


import java.sql.*;

public class Dashboard {
	static final Logger logger = (Logger) LogManager.getLogger(Dashboard.class);
	  
	public static void main(String[] args) throws Exception {	
		
			
		Scanner scanner = new Scanner(System.in);
		User user = new User();
		
		//int action = 0;

		while (User.logout() == null) {
			int action = 0;
			System.out.println("            ________  __                                                                                               \r\n" + 
					"           |        \\|  \\                                                                                              \r\n" + 
					"            \\$$$$$$$$| $$____    ______                                                                                \r\n" + 
					"              | $$   | $$    \\  /      \\                                                                               \r\n" + 
					"              | $$   | $$$$$$$\\|  $$$$$$\\                                                                              \r\n" + 
					"              | $$   | $$  | $$| $$    $$                                                                              \r\n" + 
					"              | $$   | $$  | $$| $$$$$$$$                                                                              \r\n" + 
					" __       __  | $$   | $$  | $$ \\$$     \\             ________                     __                                  \r\n" + 
					"|  \\     /  \\  \\$$    \\$$   \\$$  \\$$$$$$$            |        \\                   |  \\                                 \r\n" + 
					"| $$\\   /  $$  ______   _______    ______   __    __ | $$$$$$$$______    _______ _| $$_    ______    ______   __    __ \r\n" + 
					"| $$$\\ /  $$$ /      \\ |       \\  /      \\ |  \\  |  \\| $$__   |      \\  /       \\   $$ \\  /      \\  /      \\ |  \\  |  \\\r\n" + 
					"| $$$$\\  $$$$|  $$$$$$\\| $$$$$$$\\|  $$$$$$\\| $$  | $$| $$  \\   \\$$$$$$\\|  $$$$$$$\\$$$$$$ |  $$$$$$\\|  $$$$$$\\| $$  | $$\r\n" + 
					"| $$\\$$ $$ $$| $$  | $$| $$  | $$| $$    $$| $$  | $$| $$$$$  /      $$| $$       | $$ __| $$  | $$| $$   \\$$| $$  | $$\r\n" + 
					"| $$ \\$$$| $$| $$__/ $$| $$  | $$| $$$$$$$$| $$__/ $$| $$    |  $$$$$$$| $$_____  | $$|  \\ $$__/ $$| $$      | $$__/ $$\r\n" + 
					"| $$  \\$ | $$ \\$$    $$| $$  | $$ \\$$     \\ \\$$    $$| $$     \\$$    $$ \\$$     \\  \\$$  $$\\$$    $$| $$       \\$$    $$\r\n" + 
					" \\$$      \\$$  \\$$$$$$  \\$$   \\$$  \\$$$$$$$ _\\$$$$$$$ \\$$      \\$$$$$$$  \\$$$$$$$   \\$$$$  \\$$$$$$  \\$$       _\\$$$$$$$\r\n" + 
					"                                           |  \\__| $$                                                        |  \\__| $$\r\n" + 
					"                                            \\$$    $$                                                         \\$$    $$\r\n" + 
					"                                             \\$$$$$$                                                           \\$$$$$$ "
					+ "\nWelcome to my bank! What type of user are you ?\n1.Customer\n2.Employees");
			
			int type = 0;
			
			while(type==0) {
				try{
					type = Integer.parseInt(scanner.next());
					}catch(NumberFormatException e) {
						System.err.println("Invalid format. Enter number.");
						logger.info("Number Format incorrect");
						
					}
				}

			if (type == 1) {
				
				dashScreen(user, scanner);
				
				System.out.println(
						"1.Apply for account\n2.View Account\n3.Make Deposit\n4.Withdrawal\n5.Transfer\n6.View Balance\n7.Logout");
				while(action==0) {
					try{
						action = Integer.parseInt(scanner.next());
						}catch(NumberFormatException e) {
							System.err.println("Invalid format. Enter number.");
							logger.info("Number Format incorrect");
						}
					}	
				customerMainMenu(action, scanner, user);
			} else if (type == 2) {
				dashScreen(user, scanner);
				System.out.println(
						"1.Check Pending Account Apps\n2.View Customer Account\n3.View customers transactions\n4.Logout");
				while(action==0) {
					try{
						action = Integer.parseInt(scanner.next());
						}catch(NumberFormatException e) {
							System.err.println("Invalid format. Enter number.");
							logger.info("Number Format incorrect");
						}
					}

				try {
					adminMainMenu(action, scanner, user);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info(e);
				}

			}
			
		}

	}

	public static void dashScreen(User user, Scanner scanner) {
		System.out.println("Are you already a user? \n1.Yes \n2.No");
		int input = 0;
		while(input==0) {
			try{
				input = Integer.parseInt(scanner.next());
				}catch(NumberFormatException e) {
					System.err.println("Invalid format. Enter number.");
					logger.info("Number Format incorrect");
				}
			}
				

		if (input == 1) {
			System.out.println("Please enter your username: ");
			user.setUserName(scanner.next());
			System.out.println("Please enter your password: ");
			user.setPassword(scanner.next());

			try {

				User.login(user.getUserName(), user.getPassword());		

			} catch (Exception e) {
				logger.info("Caught exception Dashboard:104");
			}
	

		} else {

			System.out.println("Please create a user. First name: ");
			user.setFirstName(scanner.next());
			System.out.println("Please create a user. Last name: ");
			user.setLastName(scanner.next());
			System.out.println("Enter your SSN: ");
			long ssn = Long.parseLong(scanner.next());
			System.out.println("Please enter your age: ");
			user.setAge(Integer.parseInt(scanner.next()));
			System.out.println("Please enter your email: ");
			user.setEmail(scanner.next());
			System.out.println("Please create a username");
			user.setUserName(scanner.next());
			System.out.println("Set your password:");
			user.setPassword(scanner.next());
			System.out.println("Are you a customer, or Employee?");
			user.setUserType(scanner.next());

			try {
				user.createUser(user.getUserName(), user);
			} catch (SQLException e) {
				logger.info("Caught Exception");
				e.printStackTrace();
				
			}

		}
	}

	public static void customerMainMenu(int action,Scanner scanner, User user) throws Exception {
		
		switch (action) {
		case 1:
			System.out.println(
					"Please enter additional information for new account.\n What type of account would you like to create?\n'Checking'\nSavings?");
			String accountType = scanner.next();
			System.out.println("Do you have your minimum deposit of $5?\n1.Yes\n2.No");
			int deposit =0;
			while(deposit==0) {
				try{
					deposit = Integer.parseInt(scanner.next());
					}catch(NumberFormatException e) {
						System.err.println("Invalid format. Enter number.");
					}
				}
					
			if (deposit == 1) {

				System.out.println("User has deposit");

				Customer.applyForAccount(user, accountType, 5);

			}

			else if (deposit != 1) {
				System.out.println("Deposit needs to be atleast $5 to create an account.");

				User.logout();
			}
			break;
		case 2:
			System.out.println("Would you like to view 1.All accounts or 2.One Specific Account");
			int choice = 0;
			while(choice==0) {
			try{
				choice = Integer.parseInt(scanner.next());
				}catch(NumberFormatException e) {
					System.err.println("Invalid format. Enter number.");
				}
			}
					
			if (choice == 1) {
				Customer.viewAllAcct(user);
			} else {
				Customer.viewOneAcct(user);
			}
			break;

		case 3:
			Customer.makeDeposit(user);
			break;
		case 4:
			Customer.withdrawal(user);
			break;
		case 5:
			Customer.Transfer(user);
			break;
		case 6:
			Customer.viewBalance(user);
			break;
		case 7:
			User.logout();

		}
	}

	public static void adminMainMenu(int action, Scanner scanner, User user) throws Exception {
		switch (action) {
		case 1:
			Admin.getPendingApps();
			break;
		case 2:
			Account.viewAccount();
			break;
		case 3:
			Account.viewTranscations();
			break;
		case 4:
			User.logout();
		}
	}
}
