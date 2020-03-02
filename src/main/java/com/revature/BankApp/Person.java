package com.revature.BankApp;

public abstract class Person {
	private String firstName;
	private String lastName;
	private transient long ssn;
	private int age;
	private String email;
	
	public Person() {};
	
	public Person(String firstName,String lastName, long ssn, int age, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.age = age;
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getSsn() {
		return ssn;
	}
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Person [name=" + firstName + " "+ lastName +  ", age=" + age + ", email=" + email + "]";
	}
	
	
	
	
	
	
	
	
	

}
