package com.coupons.beans;
import java.util.List;
import java.util.Random;

public class Customer {
	
	//declartaion of variables
	private final long custId;
	private String custName;
	private String custPassword;
	private List<Coupon> coupons;
	
	//this boolean variable's purpose is to check wheter the customer is active
	private boolean active = true;
	
	
	//simple "id only required" constructor
	public Customer(long custId){
		this.custId = custId;
	}
	
	//simple constructor for creating a new customer
	public Customer(String custName, String custPassword) {
		Random rand = new Random();
		long randomNum = rand.nextInt(Integer.MAX_VALUE);
		custId = randomNum;
		setCustName(custName);
		setCustPassword(custPassword);
	}
	
	//full constructor
	public Customer(long custId, String custName, String custPassword, 
			List<Coupon> coupons){
		this.custId = custId;
		setCustName(custName);
		setCustPassword(custPassword);
		setCoupons(coupons);
	}

	//custName get/set methods
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	
	//custPassword get/set methods
	public String getCustPassword() {
		return custPassword;
	}

	public void setCustPassword(String password) {
		this.custPassword = password;
	}

	//coupons collection get/set methods
	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	//id get only method
	public long getCustId() {
		return custId;
	}
	
	//boolean method to determine whether the customer is active
	public boolean isActive(boolean active){
		return this.active;
	}
	
	//deactivates the customer
	public void deactivate(){
		active = false;
	}
	
	//reactivates the customer
	public void reactivate(){
		active = true;
	}

	//the toString method might look like a mess at first, but it's actually pretty elegant
	@Override
	public String toString() {
		return "ID = " + custId + "\nName = " + custName + "\nPassword = " 
		+ custPassword + "\nCoupons = " + coupons + "\n";
	}

}
