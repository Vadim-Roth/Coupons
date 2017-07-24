package com.coupons.beans;
import java.sql.Date;
import java.util.Random;

public class Coupon {

	//declartaion of variables
	private final long couponId;
	private String creator;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	//this boolean variable's purpose is to check wheter the coupon is active
	private boolean active = true;
	
	//simple "id only required" constructor
	public Coupon(long couponId){
		this.couponId = couponId;
	}
	
	//simple constructor for creating a new coupon
	public Coupon(String creator, String title, Date startDate, Date endDate, int amount, 
			CouponType type, String message, double price, String image){
		Random rand = new Random();
		long randomNum = rand.nextInt(Integer.MAX_VALUE);
		couponId = randomNum;
		setCreator(creator);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);
	}
	//full constructor
	public Coupon(long couponId,String creator, String title, Date startDate, Date endDate, 
			int amount, CouponType type, String message, double price, 
			String image){
		this.couponId = couponId;
		setCreator(creator);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);
	}
	
	//coupon creator get/set methods
		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

	//title get/set methods
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	//startDate get/set methods
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	//endDate get/set methods
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	//amount get/set methods
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	//type get/set methods
	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	//message get/set methods
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	//price get/set methods
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	//image get/set methods
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	//id get only method
	public long getCouponId() {
		return couponId;
	}
	
	//boolean method to determine whether the coupon is active
	public boolean isActive(boolean active){
		return this.active;
	}
	
	//deactivates the coupon. Reactivation is unnecessary as expiration dates can cause a problem
	//in the future and making new coupons to replace the deactivated ones make more sense
	public void deactivate(){
		active = false;
	}
	

	//the toString method might look like a mess at first, but it's actually pretty elegant
	@Override
	public String toString() {
		return "ID = " + couponId + "\nCreator = " + creator  + "\nTitle = " + title + "\nStart Date = "
				+ startDate + "\nEnd Date = " + endDate + "\nAmount = " + amount + "\nType = " + type +
					"\nMessage = " + message + "\nPrice = " + price + "\nImage = "+ image + "\n";
	}
	
}
