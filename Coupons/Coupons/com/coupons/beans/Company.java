package com.coupons.beans;
import java.util.Collection;
import java.util.Random;

public class Company {
	
	//declartaion of variables
	private final long compId;
	private String compName;
	private String compPassword;
	private String email;
	private Collection<Coupon> coupons;
	
	//this boolean variable's purpose is to check wheter the company is active
	private boolean active = true;	
	
	//tester for other companies later, if found unnecessary will be removed
	public Company(){
		compId = Integer.MAX_VALUE + 1;
	}
	
	//simple "id only required" constructor
	public Company(long compId){
		this.compId = compId;
	}
	
	//simple constructor for creating a new company
	public Company(String compName, String compPassword, String email) {
		Random rand = new Random();
		long randomNum = rand.nextInt(Integer.MAX_VALUE);
		compId = randomNum;
		setCompName(compName);
		setPassword(compPassword);
		setEmail(email);
	}
	
	//full constructor 
	public Company(long compId, String compName, String compPassword, String email, 
			Collection<Coupon> coupons){
		this.compId = compId;
		setCompName(compName);
		setPassword(compPassword);
		setEmail(email);
		setCoupons(coupons);
	}

	//compName get/set methods
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	
	//compPassword get/set methods
	public String getCompPassword() {
		return compPassword;
	}

	public void setPassword(String compPassword) {
		this.compPassword = compPassword;
	}

	
	//email get/set methods
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	//id get only method
	public long getCompId() {
		return compId;
	}

	
	//coupons collection get/set methods
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	
	//boolean method to determine whether the company is active
	public boolean isActive(boolean active){
		return this.active;
	}
	
	
	//deactivates the company
	public void deactivate(){
		active = false;
	}
	
	//reactivates the company
	public void reactivate(){
		active = true;
	}

	//the toString method might look like a mess at first, but it's actually pretty elegant
	@Override
	public String toString() {
		return "ID = " + compId + ",\nName = " + compName + ",\nPassword = "
	+ compPassword + ",\nE-mail = " + email+ "\nCoupons:\n" + coupons;
	}
	
	

}
